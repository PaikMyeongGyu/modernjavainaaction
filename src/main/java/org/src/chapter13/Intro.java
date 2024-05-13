package org.src.chapter13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Intro {

    public static void main(String... args) {
        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
        List<String> words = Arrays.asList("modern", "java", "in", "action");
        // sort는 디폴트 메서드인데, 디폴트 메서드는 기존 인터페이스에서 메서드의 추가가 필요할 때
        // 메서드 인터페이스가 아닌, 구체적인 것으로 넘겨, 소스 호환성을 있게 만든 방법임.
        // naturalOrder는 정적 메서드로 미리 Comparator 클래스 내에 정의를 해놓은 sort 내부의 Comparator 객체
        // 이미 구현이 돼서 자연 순서(표준 알파벳 순서)로 정렬이 가능하게 해줌. 숫자면 오름차순, 문자도 오름차순임.
        // 이 외에도 reverseOrder가 있어 이는 역순 정렬
        numbers.sort(Comparator.naturalOrder());
        words.sort(Comparator.naturalOrder());
        System.out.println(numbers);
        System.out.println(words);

        numbers.sort(Comparator.reverseOrder());
        words.sort(Comparator.reverseOrder());
        System.out.println(numbers);
        System.out.println(words);
    }
}
