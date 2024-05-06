package org.src.chapter9;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Debugging {

    // 아래의 코드는 에러가 나는 코드이다. 람다식으로 된 임시 메서드는 정확한 객체 할당이 된 것이 아니라서
    // 값이 lambda$~~~ 식으로 나와있고 내부 stackTrace를 따라가도 여전히 알아보기 어렵다.
    public static void main(String[] args) {
        List<Point> points = Arrays.asList(new Point(12, 2), null);
        points.stream().map(p -> p.getX()).forEach(System.out::println);
    }

    public static class Point {

        // 람다를 테스트하고 싶으면 람다를 필드에 저장해서 사용하면 된다.
        public final static Comparator<Point> compareByXAndThenY =
                Comparator.comparing(Point::getX).thenComparing(Point::getY);

        public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
            return points.stream()
                    .map(p -> new Point(p.getX() + x, p.getY()))
                    .collect(toList());
        }

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
