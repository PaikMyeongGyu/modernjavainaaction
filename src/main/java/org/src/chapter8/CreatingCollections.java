package org.src.chapter8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class CreatingCollections {

    /**
     * 마음 편하게 정적 팩토리 메서드인 of 같은 걸로 만들면,
     * 조회는 가능하지만 수정, 추가, 삭제는 불가능한 객체가 생성이 된다.
     */
    public static void main(String[] args) {
        creatingLists();
        creatingSets();
        creatingMaps();
    }

    private static void creatingLists() {
        System.out.println("------ Creating Lists ---------");

        // ArrayList에서 add로 개별 요소를 추가한 경우엔 추가, 삭제, 변경이 자유로움
        System.out.println("--> Creating a list the old-fashioned way");
        List<String> friends = new ArrayList<>();
        friends.add("Raphael");
        friends.add("Olivia");
        friends.add("Thibaut");
        System.out.println(friends);

        // Arrays.asList()라는 정적 팩토리 메서드로 만들어진 객체는
        // 수정에는 열려있지만, 추가 및 삭제에 대해서는 닫혀있다.
        System.out.println("--> Using Arrays.asList()");
        List<String> friends2 = Arrays.asList("Raphael", "Olivia");
        friends2.set(0, "Richard");
        System.out.println(friends2);
        try {
            friends2.add("Thibaut");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("As expected, we can't add items to a list created with Arrays.asList().");
        }

        System.out.println("--> Creating a Set from a List");
        Set<String> friends3 = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));
        System.out.println(friends3);

        System.out.println("--> Creating a Set from a Stream");
        Set<String> friends4 = Stream.of("Raphael", "Olivia", "Thibaut")
                .collect(Collectors.toSet());
        System.out.println(friends4);

        // List.of로 만든 List는 조회만 가능함 나머지 모두 불가능!
        System.out.println("--> Creating a List with List.of()");
        List<String> friends5 = List.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends5);
        try {
            friends5.add("Chih-Chun");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("As expected, we can't add items to a list created with List.of().");
        }
        try {
            friends5.set(1, "Chih-Chun");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Neither can we replace items in such a list.");
        }
    }

    private static void creatingSets() {
        System.out.println("-------- Creating Sets -------");

        System.out.println("--> Creating a Set with Set.of()");
        Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends);

        System.out.println("--> Trying to pass duplicate items to Set.of()");
        try {
            Set<String> friends2 = Set.of("Raphael", "Olivia", "Olivia");
            System.out.println("We shouldn't get here...");
        }
        catch (IllegalArgumentException e) {
            System.out.println("As expected, duplicate items are not allowed with Set.of().");
        }
    }

    private static void creatingMaps() {
        System.out.println("--> Creating a Map with Map.of()");
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        System.out.println(ageOfFriends);

        System.out.println("--> Creating a Map with Map.ofEntries()");
        Map<String, Integer> ageOfFriends2 = Map.ofEntries(
                entry("Raphael", 30),
                entry("Olivia", 25),
                entry("Thibaut", 26));
        System.out.println(ageOfFriends2);
    }
}
