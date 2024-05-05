package org.src.chapter5;


import org.src.chapter4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.src.chapter4.Dish.menu;


public class NumericStream {

    public static void main(String...args){
        List<Integer> numbers = Arrays.asList(3,4,5,1,2);

        Arrays.stream(numbers.toArray())
                .forEach(System.out::println);
        // 이렇게 작성하면 박싱 비용이 추가된 코드를 작성할 수 있다. 비효율적이다.
        // getCalories에서 int 타입으로 가져온 걸 Stream<Integer>로 변경하는 박싱 비용 발생!
        int caloriesBoxing = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("caloriesBoxing = " + caloriesBoxing);


        // mapToInt 같이 특화된 스트림으로 작성하면 박싱 비용을 없앤 코드를 작성할 수 있다.
        // 추가적으로 기존 map에는 타입 정보가 불분명해, sum 같은 인터페이스르 제공하지 않는데,
        // IntStream, DoubleStream 같은 경우에는 max, min, average, sum 같은 인터페이스 제공 가능!
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("calories = " + calories);

        // max와 OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        // 아래 if/else 문을 다음과 같이 한번에 줄여버릴 수 있다.
        int max = maxCalories.orElse(1);
//        if(maxCalories.isPresent()){
//            max = maxCalories.getAsInt();
//        }
//        else{
//            // 기본 값 선택 가능
//            max = 1;
//        }
        System.out.println("max = " + max);

        // 숫자 범위
        // IntStream oneToHundred = IntStream.range(1, 100);
        // 위와 같이 작성하면 1과 100이 포함되지 않는다.
        // 아래와 같이 작성하면 1부터 100까지 모두 포함된다.
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println("evenNumbers = " + evenNumbers.count());

        // boxed의 역할은 IntStream을 Stream<Integer>로 변환해준다.
        // Math.sqrt(a * a + b * b) % 1 == 0인 이유는 소수점이 아래 있으면 false가 되기 때문
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                    .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[]{a,b, (int)Math.sqrt(a * a + b * b)}));
        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        // mapToObj는 IntStream 같은 걸 Stream<U>로 변경
        Stream<int[]> pythagoreanTriples2 = IntStream.rangeClosed(1,100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0))
                .map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());
        pythagoreanTriples2.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
;    }

    public static boolean isPerfectSquare(int n){
        return Math.sqrt(n) % 1 == 0;
    }
}
