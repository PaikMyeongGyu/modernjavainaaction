package org.src.chapter16;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderMain {

    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        // execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"));
        // execute("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"));
        // 책에서는 1000밀리초쯤이라는데, 내 CPU가 문제가 있는건가...? 조건은 똑같은데
        execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"));
        bestPriceFinder.printPricesStream("myPhone27S");
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }

}
