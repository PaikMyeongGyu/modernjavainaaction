package org.src.chapter10.dsl;

import org.src.chapter10.dsl.model.Order;
import org.src.chapter10.dsl.model.Stock;
import org.src.chapter10.dsl.model.Trade;

import java.util.stream.Stream;

public class NestedFunctionOrderBuilder {

    /**
     * 아래의 코드를 통해서 만들고자 하는 패턴!,
     * 결과적으로 완성된 DSL은 많은 괄호를 사용해야 함, 인수 목록을 정적 메서드에 넘겨줘야 함.
     * Order order = order("BigBank",
     *                      buy(80,
     *                          stock("IBM", on("NYSE")).at(125.00)),
     *                      sell(50,
     *                          stock("GOOGLE", on("NASDAQ")).at(375.00)
     *                      );
     */
    public static Order order(String customer, Trade... trades) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(trades).forEach(order::addTrade);
        return order;
    }

    public static Trade buy(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }

    public static Trade sell(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }

    private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type buy) {
        Trade trade = new Trade();
        trade.setQuantity(quantity);
        trade.setType(buy);
        trade.setStock(stock);
        trade.setPrice(price);
        return trade;
    }

    public static double at(double price) {
        return price;
    }

    public static Stock stock(String symbol, String market) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setMarket(market);
        return stock;
    }

    public static String on(String market) {
        return market;
    }

}
