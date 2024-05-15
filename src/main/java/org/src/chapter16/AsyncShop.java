package org.src.chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.src.chapter16.Util.delay;
import static org.src.chapter16.Util.format;

public class AsyncShop {

    private final String name;
    private final Random random;

    public AsyncShop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public Future<Double> getPrice(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>(); // 계산 결과를 포함할 곳
        new Thread(() -> {
            try {
                double price = calculatePrice(product); // 다른 쓰레드에서 비동기적으로 해결
                futurePrice.complete(price); // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정
            } catch (Exception ex) {
                // 기존의 쓰레드는 결과값을 얻을 때까지 블록이 된다.
                // 그런데 결과를 내고 기다리는 쓰레드에게 알려줘야 할 쓰레드가 도중에 문제가 발생하면
                // 그 예외는 기다리는 쓰레드에게 미치지 않는다. 문제가 일어나서 타임 아웃이 발생하면
                // TimeoutException이 발생하는데, 이 때 그 예외를 잡아서 외부로 futurePrice에 예외를 던져줘
                // 기다리고 있는 쓰레드에게 오류가 일어났었음을 전달해주어야 한다.
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice; // 계산 결과를 기다리지 않고 즉시 반환
    }

    private double calculatePrice(String product) {
        delay();
        if (true) {
            throw new RuntimeException("product not available");
        }
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

}