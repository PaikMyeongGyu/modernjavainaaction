package org.src.chapter9;

public class StrategyMain {

    /**
     * 아래가 나은 코드라고 책에서는 설명하는 것 같은데, 개인적으로는 위가 더 의도가 명확하지 않나 싶다.
     * 오히려 아래가 코드에 대해 한 번 더 생각해야 하고 무엇을 하는 건지 정규식을 모른다면,
     * 더 헷갈리지 않나 싶다.
     */
    public static void main(String[] args) {
        // old school
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase());
        System.out.println(v2.validate("bbbb"));

        // with lambdas
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }

    interface ValidationStrategy {
        boolean execute(String s);
    }

    static private class IsAllLowerCase implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }

    }

    static private class IsNumeric implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }

    }

    // 전략을 객체 생성 시점에 외부로부터 주입을 받는다.
    // 전략 패턴은 런타임에 적절한 알고리즘을 선택하는 기법임을 까먹지 말자!
    static private class Validator {

        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }

    }

}
