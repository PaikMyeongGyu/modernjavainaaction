package org.src.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVsCollection {

    public static void main(String...args){
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.forEach(System.out::println);
        // 스트림은 한 번만 소비될 수 있으므로 윗 줄 반복시 IllegalStateException 발생
    }
}
