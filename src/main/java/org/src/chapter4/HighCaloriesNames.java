package org.src.chapter4;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class HighCaloriesNames {

    public static void main(String[] args){
        List<String> threeHighCaloricDishNames =
                Dish.menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(toList());

        System.out.println(threeHighCaloricDishNames);

        List<String> names = Dish.menu.stream()
                .filter(dish -> {
                    System.out.println("filtering:" + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(names);
    }
}