package org.src.chapter10;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PrintNumbers {

    public static void main(String[] args) {
        List<String> numbers = Arrays.asList("one", "two", "three");

        System.out.println("Anonymous class:");
        numbers.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        System.out.println("Lambda expression:");
        numbers.forEach(s -> System.out.println(s));

        // 내부 구현은 모르겠지만 명확한 이름을 사용한다면 의도 파악이 쉬움.
        System.out.println("Method reference:");
        numbers.forEach(System.out::println);
    }
}
