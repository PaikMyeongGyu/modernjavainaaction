package org.src.chapter16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShopMain {

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product"); // 상점에 제품 가격 정보 요청
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime
                + " msecs"); // 가격 정보를 얻어오기 전에 출력됨. getPriceAsync가 즉시 반환돼 아직 블록이 안됐기 때문임.
        // 제품 가격을 계산하는 동안
        doSomethingElse();
        // 다른 상점 질의 같은 다른 작업 수행
        try {
            double price = futurePrice.get(); // 가격 정보가 있으면 Future에서 가격 정보를 읽고, 없으면 받을 때까지 블록된다.
            // 그래서 1초 뒤 아래의 메시지가 뜸.
            System.out.printf("Price is %.2f%n", price);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void doSomethingElse() {
        System.out.println("Doing something else...");
    }

}