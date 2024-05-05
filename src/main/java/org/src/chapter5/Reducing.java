package org.src.chapter5;

import org.src.chapter4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.src.chapter4.Dish.menu;

public class Reducing {

    public static void main(String...args){
        List<Integer> numbers = Arrays.asList(3,4,5,1,2);

        /**
         *  int sum = 0;
         *  for (int x : numbers) {
         *     sum += x;
         *  }
         */
        int sum = numbers.stream().reduce(0, (a,b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a,b) -> Integer.max(a,b));
        System.out.println(max);

        /**
         * Optional<Integer>는 초기 값이 없는 경우를 대비해서 이 때는 합계를 반환할 수 없으니
         * 그 처리를 위한 것이다.
         *
         */
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        /**
         * 이 경우에는 menu 내에 값이 없더라도 초기 값이 0으로 되어있으니 이후를 고려하지 않아도 된다.
         */
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories: " + calories);
    }
}
