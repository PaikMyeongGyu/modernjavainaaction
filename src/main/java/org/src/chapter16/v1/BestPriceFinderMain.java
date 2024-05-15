package org.src.chapter16.v1;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderMain {

    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        // BestPriceFinder 내부에는 총 4개의 상점이 있다.
        // 각 메서드는 스트림으로 getPrice와 getName 같은 메서드롤 호출한다.
        // 그런데 해당 메서드 내부에는 1초 간의 지연이 있고 Stream은 상점을 돌면서
        // 총 4개의 상점을 들르니 총 4번의 지연이 발생해 아래의 코드는 4초 이상의 시간이 소요된다.
        // execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"));

        // 병렬 스트림을 사용하면 순차 계산을 병렬로 처리해서 성능 개선이 가능하다.
        // 아래의 메서드의 실행 시간은 1초이다.
        // execute("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"));

        execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"));
        //execute("combined USD CompletableFuture", () -> bestPriceFinder.findPricesInUSD("myPhone27S"));
        //execute("combined USD CompletableFuture v2", () -> bestPriceFinder.findPricesInUSD2("myPhone27S"));
        //execute("combined USD CompletableFuture v3", () -> bestPriceFinder.findPricesInUSD3("myPhone27S"));
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }

}
