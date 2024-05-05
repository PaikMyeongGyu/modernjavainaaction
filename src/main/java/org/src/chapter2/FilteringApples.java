package org.src.chapter2;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.src.chapter2.FilteringApples.Color.GREEN;
import static org.src.chapter2.FilteringApples.Color.RED;


public class FilteringApples {

    public static void main(String[] args){
        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(120, RED)
        );

        List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
        System.out.println(greenApples);

        List<Apple> redApples = filterApplesByColor(inventory, RED);
        System.out.println(redApples);

        List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
        System.out.println(greenApples2);

        List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
        System.out.println(heavyApples);

        List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples);

        Predicate<Apple> greenApplePredicate = (apple) -> apple.getColor().equals(GREEN);
        Predicate<Apple> heavyApplePredicate = (apple) -> apple.getWeight() >= 150;

        List<Apple> heavyGreenApples = inventory.stream().filter(greenApplePredicate.and(heavyApplePredicate)).collect(toList());
        System.out.println("heavyGreenApples = " + heavyGreenApples);

        List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple a) {
                return a.getColor() == RED;
            }
        });
        System.out.println(redApples2);

        List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        System.out.println(result);

        Collections.sort(inventory, new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return Integer.compare(a1.getWeight(), a2.getWeight());
            }
        });

        Collections.sort(inventory,
                (Apple a1, Apple a2) -> Integer.compare(a1.getWeight(), a2.getWeight()));
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight)
    {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    enum Color{
        RED,
        GREEN
    }

    public static class Apple {
        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("Apple{color=%s, weight=%d}", color, weight);
        }

    }

    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    static class AppleWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) { return apple.getWeight() > 150; }
    }

    static class AppleColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == GREEN;
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == RED && apple.getWeight() > 150;
        }
    }
}
