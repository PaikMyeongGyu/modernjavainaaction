package org.src.chapter6;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.*;
import static org.src.chapter6.Dish.menu;

public class Grouping {

    enum CaloricLevel { DIET, NORMAL, FAT };

    public static void main(String...args){
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType(){
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    /**
     * Grouping을 위한 정확한 메서드가 없어도, 특정한 기준에 따라 분류하는 것을
     * 따로 작성할 수 있다!
     */
    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    }
                    else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    }
                    else {
                        return CaloricLevel.FAT;
                    }
                })
        );
    }

    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType(){
        // return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        // 위처럼 코드를 짜버리면 이미 필터에서 500이상인게 날라가버려서 FISH 카테고리의 코드가 모두 날아가버린다.
        // 그렇기 때문에 빈 리스트로 반환되는 컬렉션도 포함되게 코드를 작성하려면,
        // 순서를 바꿔서 grouping을 먼저하고 filter를 내부에서 해줘야 한다.
        return menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish->dish.getCalories() > 500, toList())));
    }

    /**
     *  Grouping을 이중으로 해서 각 음식 타입별, 음식 칼로리 레벨 별로 분류된 음식 리스트를 반환할 수 있다.
     */
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) {
                               return CaloricLevel.DIET;
                            }
                            else if (dish.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            }
                            else {
                                return CaloricLevel.FAT;
                            }
                        })
                )
        );
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
    }

    /**
     * 지금 리듀싱 과정에서 MaxBy랑 동일하게 작업을 하는데, MaxBy랑 동일한 이유로 컬렉션이 없는 경우를 대비해서
     * Optional<Dish>가 반환된다. 하지만, 실제 동작에서 스트림의 첫 번쨰 요소를 찾은 이후에 그룹화 맵에 추가하므로
     * 만약에 값이 없다면, 리듀싱 컬렉터는 절대 Optional.empty()를 반환하지 않기 때문에 아래처럼 코드를 짜도 아무런 문제가 없다.
     */
    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get)));
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) {
                                return CaloricLevel.DIET;
                            }
                            else if (dish.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            }
                            else {
                                return CaloricLevel.FAT;
                            }
                        },
                        toSet()
                ))
        );
    }

}
