package org.src.chapter20;

import java.util.function.Function;
import java.util.stream.Stream;

// 자바에서는 이렇게 커링을 하기 위해서는 전체적인 구조를 만들어야 함.
// 하지만 스칼라에서는 def multiplyCurry(x : Int)(y : Int) = x * y
// val multiplyByTwo = int => Int = multiplyCurry(2)
// val r = multiplyByTwo(10) 이런 식으로 가능함!
public class Currying {

    public static void main(String[] args) {
        int r = multiply(2, 10);
        System.out.println(r);

        Stream.of(1, 3, 5, 7)
                .map(multiplyCurry(2))
                .forEach(System.out::println);
    }

    static int multiply(int x, int y) {
        return x * y;
    }

    static Function<Integer, Integer> multiplyCurry(int x) {
        return (Integer y) -> x * y;
    }

}
