package org.src.chapter5;


import org.src.chapter4.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Filtering {

    public static void main(String...args){
        // 프레디케이트로 거름, 프리디케이트가 filter 내부에 조건으로 사용됨.
        System.out.println("Filtering with a predicate");
        List<Dish> vegetarianMenu = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        vegetarianMenu.forEach(System.out::println);

        // 고유 요소로 거름, 중복을 판단하는 방법은 hashCode와 equals로 논리적인 값으로 결정
        System.out.println("Filtering unique elements:");
        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        numbers.stream()
                .filter(i->i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 스트림 슬라이스
        // 칼로리 값을 기준으로 리스트 오름차순 정렬
        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));
        System.out.println("Filtered sorted menu:");
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());
        filteredMenu.forEach(System.out::println);

        /**
         * takeWhile은 범위까지의 지정? 테이블 스캔으로 따지면 IndexRangeScan 같은 것이다.
         * 동작의 결과는 적용하지 않은 것과 동일하지만, 데이터 자료가 만약에 조건에 맞게 정렬이 되어있다면,
         * 일반 filter는 모든 요소를 다 순회하며 filter 내부의 Predicate를 확인하지만
         * takeWhile로 동작하면, 값이 넘어갔을 때 순회를 멈춘다! -> 약간의 최적화적 기능으로 리스트가 커도 빠른 응답용
         */
        System.out.println("Sorted menu sliced with takewhile():");
        List<Dish> sliceMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        sliceMenu1.forEach(System.out::println);

        /**
         * drhopWhile은 takeWhile과 정반대로 처음으로 거짓이 되는 지점까지만해서 아래의 경우는 지금 320미만인 값을
         * filter하는게 아니라, 320이상인 값을 찾기 위한 것이다. 이 또한 최적화적 기능이며, 조건을 정확하게 보자.
         */
        System.out.println("Sorted menu sliced with dropwhile():");
        List<Dish> sliceMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        sliceMenu2.forEach(System.out::println);

        // 스트림 연결
        List<Dish> dishesLimit3 = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());
        System.out.println("Truncating a stream:");
        dishesLimit3.forEach(System.out::println);

        // 요소 생략
        List<Dish> dishesSkip2 = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
        System.out.println("Skipping elements:");
        dishesSkip2.forEach(System.out::println);
    }
}
