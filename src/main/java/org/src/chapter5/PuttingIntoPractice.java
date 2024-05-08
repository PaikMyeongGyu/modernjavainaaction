package org.src.chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class PuttingIntoPractice {
    public static void main(String...args){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        Trader john = new Trader("john", "New York");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950),
                new Transaction(john, 2011, 950)
        );

        // 질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값)
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() >= 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);

        // 질의 2: 거래자가 근무하는 모든 고유 도시는?
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println("hey cities = " + cities);

        // 질의 3: Cambridge의 모든 거래자를 찾아 이름으로 정렬
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println("traders = " + traders);


        // 질의 4: 알파벳 순으로 정렬된 모든 거래자의 이름 문자열을 반환
        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println("traderStr = " + traderStr);

        // 질의 5: Milan에 거주하는 거래자가 있는가?
        boolean milanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(milanBased);

        // 질의 6: 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 질의 7: 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> hightestValue =
                transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(Integer::max);
        System.out.println("hightestValue = " + hightestValue);

        // 질의 8: 전체 트랜잭션 중 최솟값은 얼마인가?, 최대 최소를 max나 min으로 해도 되고 아래의 방법으로 해도 됨.
        Optional<Transaction> smallestTransaction =
                transactions.stream()
                        .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println("smallestTransaction = " + smallestTransaction.get().getValue());

    }
}
