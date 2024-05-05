package org.src.chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamBasic {

    public static void main(String...args){
        getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

        System.out.println("---");

        getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);
    }

    /**
     * 절차적으로 계속 생각을 해야 한다.
     * 어렵지 않은 코드이지만 400 칼로리면 추가하고
     * 새로 이름 배열을 만들어 할 당한 뒤 정렬한다.
     * 400 칼로리 이하인 배열이 담긴 lowCaloricDishes에서
     * 이름만 가져와 이름을 담는 배열인 lowCaloricDishesName에 담는다.
     */
    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: dishes){
            if(d.getCalories() < 400){
                lowCaloricDishes.add(d);
            }
        }

        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishesName);

        for(Dish d : lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }

    /**
     * 선언적으로 작성이 가능하다. 400 칼로리 이하 음식을
     * 칼로리 순으로 정렬해서 이름 필드를 가지고 List화 시켜라.
     */
    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
        return dishes.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

    /**
     * 책에서 parallelStream을 이용해서 속도 개선을 위한 병렬처리를 하는 방법을 알려준다. 그런데, parallelStream을 스프링에서 쓰는 건,
     * 고민을 좀 해봐야 한다. 알기로는 이게 쓰레드 로컬로 구현된 것들과 함께 동작시 오히려 장애의 원인이 될 가능성이 있다는 것으로 안다.
     */
    public static List<String> getLowCaloricDishesNamesWithParallelStream(List<Dish> dishes) {
        return dishes.parallelStream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

}
