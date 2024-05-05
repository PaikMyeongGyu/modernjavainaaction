package org.src.chapter6;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class GroupingTransaction {

    public static List<Transaction> transactions = Arrays.asList(
            new Transaction(Currency.EUR, 1500.0),
            new Transaction(Currency.USD, 2300.0),
            new Transaction(Currency.GBP, 9900.0),
            new Transaction(Currency.EUR, 1100.0),
            new Transaction(Currency.JPY, 7800.0),
            new Transaction(Currency.CHF, 6700.0),
            new Transaction(Currency.EUR, 5600.0),
            new Transaction(Currency.USD, 4500.0),
            new Transaction(Currency.CHF, 3400.0),
            new Transaction(Currency.GBP, 3200.0),
            new Transaction(Currency.USD, 4600.0),
            new Transaction(Currency.JPY, 5700.0),
            new Transaction(Currency.EUR, 6800.0)
    );

    public static void main(String... args) {
        groupImperatively();
        groupFunctionally();
    }

    /**
     * 자바 코드로 그룹핑을 하는 경우에는 의도나 목적을 한 번에 알아보기가 굉장히 불편하다.
     * 아래의 코드는 통화 별 그룹핑을 하는 코드이다.
     */
    private static void groupImperatively() {
        Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();
        for (Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = transactionByCurrencies.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionByCurrencies.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }

        System.out.println(transactionByCurrencies);
    }

    /**
     * 메서드 명칭을 통해 정확히 해당 함수가 무엇을 기준으로 Grouping 하는지 정확하게 알 수 있다.
     */
    public static void groupFunctionally() {
        Map<Currency, List<Transaction>> transactionByCurrencies = transactions.stream()
                .collect(groupingBy(Transaction::getCurrency));
        System.out.println(transactionByCurrencies);
    }

    public static class Transaction {

        private final Currency currency;
        private final double value;

        public Transaction(Currency currency, double value) {
            this.currency = currency;
            this.value = value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return currency + " " + value;
        }
    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }
}
