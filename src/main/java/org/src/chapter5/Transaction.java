package org.src.chapter5;

import java.util.Objects;

public class Transaction {

    private Trader trader;
    private int year;
    private int value;

    public Transaction(Trader trader, int year, int value){
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader(){
        return trader;
    }

    public int getYear(){
        return year;
    }

    public int getValue(){
        return value;
    }

    /**
     * 스트림의 관점에서 hashCode와 equals는 distinct 같은 것을 위해서 사용된다.
     */
    @Override
    public int hashCode(){
        int hash = 17;
        hash = hash * 31 + (trader == null ? 0 : trader.hashCode());
        hash = hash * 31 + year;
        hash = hash * 31 + value;
        return hash;
    }

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }
        if(!(other instanceof Transaction)){
            return false;
        }

        Transaction o = (Transaction) other;
        boolean eq = Objects.equals(trader, o.getTrader());
        eq = eq && year == o.getYear();
        eq = eq && value == o.getValue();
        return eq;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString(){
        return String.format("{%s year: %d, value: %d}", trader, year, value);
    }
}
