package org.src.chapter11;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Chapter11은 개인적으로 세가지만 기억하면 될 것 같다.
 * 1. Optional에 Stream을 지원한 게 자바 9부터라는 점
 * 2. flatMap을 사용해서 Stream<Optional> 혹은 Optional<Optional<?>> 형태를 해결
 * 3. orElseGet과 get의 차이
 */
public class OptionalMain {

    /**
     * 지금 구조가 사람이 차를 가지고 차에 보험을 가입했는지 여부를 고려해야 함.
     * 그런데, 없는 경우 해당 객체는 null임. 중첩마다 계속 null인지 여부를 확인해야 하니
     * 다음처럼 인덴트가 엄청 깊은 코드가 나와버림.
     */
    public String getCarInsuranceNameNullSafeV1(PersonV1 person) {
        if (person != null) {
            CarV1 car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    /**
     * 인덴트 깊이를 줄이려고 출구를 엄청나게 만들면 그것도 좀 곤란한 것 같음...
     * 이게 상황이 쉬워서 그렇지 복잡하다면?
     */
    public String getCarInsuranceNameNullSafeV2(PersonV1 person) {
        if (person == null) {
            return "Unknown";
        }
        CarV1 car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }

    // 컴파일되지 않음:
    // (1)에서 Optional<Person>에 map(Person::getCar) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
    // 그리고 (2)에서 Optional<Car>에 map(Car::getInsurance) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
    // Insurance::getName은 평범한 문자열을 반환하므로 추가 "flatMap"은 필요가 없음.
  /*public String getCarInsuranceName(Person person) {
    Optional<Person> optPerson = Optional.of(person);
    // 책에서 이 코드가 안된다고 하는 이유는 Person::getCar는 Optional<Car>
    // map(Person::getCar)는 Optional<Optional<Car>>
    // 여기에 스트림 연산 추가적으로 map이 안됨. 여기서 중첩된 걸 없애려면 flatMap 사용시 가능
    Optional<String> name = optPerson.map(Person::getCar) // (1)
        .map(Car::getInsurance) // (2)
        .map(Insurance::getName);
    return name.orElse("Unknown");
  }*/

    // flatMap이 예전엔 어려웠는데 단순히 Optional 혹은 Stream 같은 게 중첩됐을 때 한 칸 내려주는 것?
    // 정도로 생각하면 되는 것 같다.
    // 결과적으로 아래 코드는 맨위랑 형식은 비슷하지만 가독성이 높아지고 결과를 위해 출구가 여러 개 생기지 않는다.
    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown"); // null일 때, 사용할 기본 값.
    }


    // 결과적으로 Stream<Optional<String>> 객체가 만들어지는데 이는 스트림으로 바꿔
    // Set으로 최종 변경 가능!, 주의사항은 Optional에서의 스트림 조작은 자바 9부터 가능하다!
    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optInsurance -> optInsurance.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(toSet());
    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }
    }

    public Insurance findCheapestInsurance(Person person, Car car) {
        // 다른 보험사에서 제공한 질의 서비스
        // 모든 데이터 비교
        Insurance cheapestCompany = new Insurance();
        return cheapestCompany;
    }

    public Optional<Insurance> nullSafeFindCheapestInsuranceQuiz(Optional<Person> person, Optional<Car> car) {
        // 여기서 뒤의 car.map의 결과가 Optional<Insurance>라 flatMap 사용함.
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}
