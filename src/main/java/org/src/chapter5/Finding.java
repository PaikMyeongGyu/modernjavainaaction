package org.src.chapter5;

import org.src.chapter4.Dish;

import java.util.Optional;

import static org.src.chapter4.Dish.menu;


public class Finding {

    public static void main(String...args){
        if(isVegetarianFriendlyMenu()){
            System.out.println("Vegetarian friendly");
        }

        System.out.println(isHealthyMenu());
        System.out.println(isHealthyMenu2());

        // 람다의 병렬성으로 인해 첫번째 요소 같은 방식으로 찾지 않고 이렇게 반환함.
        Optional<Dish> dish = findVegetarianDish();
        // Optional에서 ifPresent로 동작하면 장점은 if(dish == null)로 생기는 들여쓰기가 사라진다!
        dish.ifPresent(d -> System.out.println(d.getName()));
    }

    /**
     * any, allMatch, nonMatch는 쇼트서킷 평가 특성이 있다. 만약에 anyMatch라면 단 한 개의 값이라도 일치하면
     * 이후는 검사하나 마나이다. 그러면, stream 개별 값에 대한 평가를 멈춰서 불필요한 연산을 줄일 수 있다.
     */
    private static boolean isVegetarianFriendlyMenu(){
        return menu.stream().anyMatch(Dish::isVegetarian);
    }

    private static boolean isHealthyMenu(){
        return menu.stream().allMatch(d -> d.getCalories() < 1000);
    }

    private static boolean isHealthyMenu2(){
        return menu.stream().noneMatch(d -> d.getCalories() >= 1000);
    }

    private static Optional<Dish> findVegetarianDish(){
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }
}
