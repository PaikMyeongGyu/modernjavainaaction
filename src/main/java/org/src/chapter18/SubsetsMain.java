package org.src.chapter18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubsetsMain {

    public static void main(String[] args) {
        List<List<Integer>> subs = subsets(Arrays.asList(1,4,9));
        subs.forEach(System.out::println);
    }

    public static <T> List<List<T>> subsets(List<T> l) {
        if (l.isEmpty()) {
            List<List<T>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }
        T first = l.get(0);
        List<T> rest = l.subList(1, l.size());
        List<List<T>> subans = subsets(rest);
        List<List<T>> subans2 = insertAll(first, subans);
        return concat(subans, subans2);
    }

    public static <T> List<List<T>> insertAll(T first, List<List<T>> lists) {
        List<List<T>> result = new ArrayList<>();
        for (List<T> l : lists) {
            List<T> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(l);
            result.add(copyList);
        }
        System.out.println("result = " + result);
        return result;
    }

    // 여기서 고민해볼 점은 a를 다시 참조할 것인가? 참조하지 않을 것인가에 대한 고민이다.
    // 아래의 메서드에서는 a를 다시 참조할 것을 가정하고 a를 dest로 b를 붙여놨다.
//    static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
//        a.addAll(b);
//        return a;
//    }

    // 만약에 a를 참조하지 않고 완전히 새로운 객체에 넣어야 한다면 아래와 같이 작성해야 한다.
    static <T> List<List<T>> concat(List<List<T>> a, List<List<T>> b) {
        List<List<T>> r = new ArrayList<>(a);
        r.addAll(b);
        return r;
    }


}
