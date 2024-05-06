package org.src.chapter9;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Peek {

    // Peek은 파이프라인 연산을 디버깅할 때, 개별 요소를 사용하는 게 아니라 본인 연산에서 처리만 하고
    // 값을 그대로 뒷 파이프라인으로 넘긴다.
    public static void main(String[] args) {
        List<Integer> result = Stream.of(2, 3, 4, 5)
                .peek(x -> System.out.println("taking from stream: " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit: " + x))
                .collect(toList());
    }

}
