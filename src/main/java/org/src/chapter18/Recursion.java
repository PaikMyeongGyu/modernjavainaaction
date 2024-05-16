package org.src.chapter18;

import java.util.stream.LongStream;

public class Recursion {

    public static void main(String[] args) {
        System.out.println(factorialIterative(5));
        System.out.println(factorialRecursive(5));
        System.out.println(factorialStreams(5));
        System.out.println(factorialTailRecursive(5));
    }

    // 반복문 형태에서는 r과 i가 계속해서 바뀌고 외부로 노출돼 있다.
    public static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    // 외부로 노출이 되어있지 않지만, 재귀 코드는 호출 시마다 새로운 스택 프레임이 만들어진다.
    // 동일한 일을 했을 때 메모리 사용량이 증가하는데, 입력값이 큰 경우, stackOverflowError가 발생한다.
    public static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    public static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (long a, long b) -> a * b);
    }

    // 꼬리 호출 재귀 형태로 놔두면, 이를 지원하는 언어에서는 컴파일러가 스택 프레임을 재활용한다.
    // 하지만 JVM은 최적화를 제공하지 않는다.
    public static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    public static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

}
