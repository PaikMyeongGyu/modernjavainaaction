package org.src.chapter5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {

    public static void main(String...args) throws Exception{
        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambda", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // getProperty, null이 될 수 있는 개체를 스트림으로 만드는 메서드
        // getProperty는 원래는 운영체제이름, 사용자 계정, 디렉토리 같은 정보를 가져다 주는 것임.
        String homeValue = System.getProperty("home");
        Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

        // 아니면 Stream.ofNullable로 다음과 같이 합칠수도 있다.
        Stream<String> homeValueStream2 = Stream.ofNullable(System.getProperty("home"));

        // null이 될 수 있는 객체를 포함하는 스트림 값을 flatMap과 함꼐 사용하는 경우 더 유용하게 사용 가능
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));

        // Arrays.stream을 이용한 스트림을 만드는 방법
        int[] numbers = {2,3,5,7,11,13};
        int sum = Arrays.stream(numbers).sum();

        // 파일에서 스트림 사용하기
        long uniqueWords = 0;
        try(Stream<String> lines =
                    Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct() // 문장 별 고유 단어 수 계산
                    .count();
        } catch(IOException e){
        }

        // 함수로 무한 스트림 만들기
        Stream.iterate(0, n -> n+2)
                .limit(10)
                .forEach(System.out::println);

        // 스트림으로 피보나치수열 집합 작성하기
        // 이건 집합을 얻음.
        Stream.iterate(new int[]{0,1}, t-> new int[]{t[1], t[0]+t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1], t[0]+t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        // 0부터 시작해서 100보다 크면 숫자 생성 중단하는 코드
        IntStream.iterate(0, n-> n < 100, n -> n + 4)
                .forEach(System.out::println);

        // 위와 동일한 결과를 가져다 주는 코드
        IntStream.iterate(0, n -> n + 4)
                .takeWhile(n -> n < 100)
                .forEach(System.out::println);

        // 0부터 1까지 임의의 값을 랜덤하게 갖는 크기 10짜리 스트림
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        // Generate 뒤에는 supplier가 와서 값을 주고 무한하게 하되
        // limit으로 개수 조절 가능.
        IntStream.generate(()->1)
                .limit(10)
                .forEach(System.out::println);
    }
}
