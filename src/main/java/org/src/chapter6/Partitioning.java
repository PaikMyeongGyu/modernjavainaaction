package org.src.chapter6;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.src.chapter6.Dish.menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Partitioning {

    public static void main(String... args) {
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
        System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
    }

    // partitioning은 약간 grouping인데 참 거짓 같이 속하는 것과 속하지 않는 그룹으로 나누는 방식이다.
    private static Map<Boolean, List<Dish>> partitionByVegeterian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }

    // partition과 grouping 같이 사용 가능하다.
    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    }

    private static Object mostCaloricPartitionedByVegetarian() {
        return menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get)));
    }
}
