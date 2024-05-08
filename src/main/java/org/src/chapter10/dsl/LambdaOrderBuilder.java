package org.src.chapter10.dsl;

import org.src.chapter10.dsl.model.Order;
import org.src.chapter10.dsl.model.Stock;
import org.src.chapter10.dsl.model.Trade;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

    /**
     * 이 구현으로 만들 수 있는 주문 패턴
     * Order order = order(o -> {
     *      o.forCustomer("BigBank");
     *      o.buy(t -> {
     *          t.quantity(80);
     *          t.price(125.00);
     *          t.stock(s -> {
     *              s.symbol("IBM");
     *              s.market("NYSE");
     *          });
     *      });
     * });
     * o.sell(t -> {
     *     t.quantity(50);
     *     t.price(375.00);
     *     t.stock(s -> {
     *         s.symbol("GOOGLE");
     *         s.market("NASDAQ");
     *     });
     *    });
     * });
     */
    private Order order = new Order();

    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder);
        return builder.order;
    }

    public void forCustomer(String customer) {
        order.setCustomer(customer);
    }

    public void buy(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.BUY);
    }

    public void sell(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.SELL);
    }

    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(type);
        consumer.accept(builder);
        order.addTrade(builder.trade);
    }

    public static class TradeBuilder {

        private Trade trade = new Trade();

        public void quantity(int quantity) {
            trade.setQuantity(quantity);
        }

        public void price(double price) {
            trade.setPrice(price);
        }

        public void stock(Consumer<StockBuilder> consumer) {
            StockBuilder builder = new StockBuilder();
            consumer.accept(builder);
            trade.setStock(builder.stock);
        }

    }

    public static class StockBuilder {

        private Stock stock = new Stock();

        public void symbol(String symbol) {
            stock.setSymbol(symbol);
        }

        public void market(String market) {
            stock.setMarket(market);
        }

    }

}
