package org.src.chapter6;

import static java.util.stream.Collectors.reducing;
import static org.src.chapter6.Dish.menu;

public class Reducing {
    public static void main(String...args){
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    /**
     * 개인적으로, 최소한 두번째 방식 이상으로 코드를 작성하는 게 좋을 것 같다. 첫 번째 코드는 익숙한 사람이 아니면
     * 의도파악하기도 어렵고, 작성하는 사람 입장에서도 불편하다.
     */
    private static int calculateTotalCalories(){
        return menu.stream().collect(reducing(0, Dish::getCalories, (Integer i, Integer j) -> i + j));
    }

    private static int calculateTotalCaloriesWithMethodReference(){
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCaloriesWithoutCollectors(){
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum(){
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
