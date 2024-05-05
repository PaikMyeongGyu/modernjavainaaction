package org.src.chapter5;


import org.src.chapter4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;
import static org.src.chapter4.Dish.menu;

public class Mapping {

    public static void main(String... args) {
        // map
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);

        // flatMap, flatMap은 Stream 배열 덩어리를 풀어주는 역할을 한다.
        // 특히나 distinct를 예시로 한 이유는 words에 {"Goodbye", "World"}가 있을 때
        // 전체 문자에 대해서 구분을 하고 싶은 게 의도인 경우, flatMap을 사용하지 않고 그냥 map을 사용하면
        // split도 하고 distinct로 해도 결과는 {"Goodbye", "World"} 스트림의 참조 깂이다.
        words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);

        // 개별 요소를 비교해보면 더 쉽게 알 수 있지 않을까 싶어 변경해보았다.
        // Stream<Stream<String>>에서 외부에 씌워진 Stream을 하나 지우는 역할을 한다.
        Stream<String> flatMapStream = words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")));

        Stream<Stream<String>> mapStream = words.stream()
                .map((String line) -> Arrays.stream(line.split("")));

        // 결과로 참조값이 나옴.
        // java.util.stream.ReferencePipeline$Head@5fd0d5ae
        // java.util.stream.ReferencePipeline$Head@2d98a335
        words.stream()
                .map((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);

        // flatMap은 숫자 배열에 대해서도 동일하게 동작한다. 두 개의 배열을 가지고서 추출하는 방법을 담았는데
        // 각각 배열에서 한 값씩 꺼내 조합해서 나올 수 있는 모든 경우의 수에 관해 알고 싶다면 flatMap을 사용해야 한다.
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                        .map((Integer j) -> new int[]{i, j})
                )
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
    }
}
