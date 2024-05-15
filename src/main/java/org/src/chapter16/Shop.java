package org.src.chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.src.chapter16.Util.delay;
import static org.src.chapter16.Util.format;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product); // 해당 메서드 호출시 1초가 지연
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>(); // 계산 결과를 포함할 곳
        new Thread(() -> {
            double price = calculatePrice(product); // 다른 쓰레드에서 비동기적으로 해결
            futurePrice.complete(price); // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정
        }).start();
        return futurePrice; // 계산 결과를 기다리지 않고 즉시 반환
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public String getName() {
        return name;
    }

}