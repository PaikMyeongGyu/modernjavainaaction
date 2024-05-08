package org.src.chapter4;

import java.util.ArrayList;
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

        // 람다버전
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
        System.out.println("-----------------------");

        // 반복문 버전
        List<String> names2 = new ArrayList<>();
        int count = 0;

        for (Dish dish : Dish.menu) {
            if (dish.getCalories() > 300) {
                System.out.println("filtering: " + dish.getName());
                if (count < 3) {
                    System.out.println("mapping: " + dish.getName());
                    names2.add(dish.getName());
                    count++;
                }
                if (count >= 3) {
                    break;
                }
            }
        }
        System.out.println(names2);
    }
}