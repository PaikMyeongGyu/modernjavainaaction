package org.src.chapter10.dsl;

import org.src.chapter10.dsl.model.Order;
import org.src.chapter10.dsl.model.Stock;
import org.src.chapter10.dsl.model.Trade;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {

    /**
     *  이 파트에 대해서 다시 생각해봤는데... 개인적으로 어떻게 구현했는지 달달 외우기보다는
     *  이런 구성이 어떤 메서드 호출 패턴 결과로 나오는지를 생각해보는 시간을 가지는 게 더 좋을 것 같음.
     *
     *     Order order = forCustomer("BigBank",
     *             buy( t -> t.quantity(80)
     *                        .stock("IBM")
     *                        .on("NYSE")
     *                        .at(125.00)),
     *             sell( t -> t.quantity(50)
     *                         .stock("GOOGLE")
     *                         .on("NASDAQ")
     *                         .at(125.00)) );
     */

    public static Order forCustomer(String customer, TradeBuilder... builders) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(builders).forEach(b -> order.addTrade(b.trade));
        return order;
    }

    public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.BUY);
    }

    public static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.SELL);
    }

    private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type buy) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(buy);
        consumer.accept(builder);
        return builder;
    }

    public static class TradeBuilder {

        private Trade trade = new Trade();

        public TradeBuilder quantity(int quantity) {
            trade.setQuantity(quantity);
            return this;
        }

        public TradeBuilder at(double price) {
            trade.setPrice(price);
            return this;
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(this, trade, symbol);
        }

    }

    public static class StockBuilder {

        private final TradeBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        public TradeBuilder on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return builder;
        }

    }

}
