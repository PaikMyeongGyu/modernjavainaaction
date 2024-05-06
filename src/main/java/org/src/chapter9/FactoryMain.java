package org.src.chapter9;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryMain {

    // 책에서는 TryFunction이라는 함수형 인터페이스를 활용해 더 많은 인수를 받는 Product를
    // 처리하는 방법을 알려준다. 개인적으로 builder를 쓰는게 차라리 명확할 수도 있지 않을까..? 싶다.
    public static void main(String[] args) {
        Product p1 = ProductFactory.createProduct("loan");
        System.out.printf("p1: %s%n", p1.getClass().getSimpleName());

        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();
        System.out.printf("p2: %s%n", p2.getClass().getSimpleName());

        Product p3 = ProductFactory.createProductLambda("loan");
        System.out.printf("p3: %s%n", p3.getClass().getSimpleName());
    }

    static private class ProductFactory {

        public static Product createProduct(String name) {
            switch (name) {
                case "loan":
                    return new Loan();
                case "stock":
                    return new Stock();
                case "bond":
                    return new Bond();
                default:
                    throw new RuntimeException("No such product " + name);
            }
        }

        // 이런 코드가 가능한 이유는 Supplier 내부에는 지네릭으로 선언된 타입 하나만 저장이 되고
        // 해당 값을 전달해주는 것만 있음. 결과적으로 Product를 찾아서 있으면 주는 거임.
        public static Product createProductLambda(String name) {
            Supplier<Product> p = map.get(name);
            if (p != null) {
                return p.get();
            }
            throw new RuntimeException("No such product " + name);
        }
    }

    static private interface Product {}
    static private class Loan implements Product {}
    static private class Stock implements Product {}
    static private class Bond implements Product {}

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
}
