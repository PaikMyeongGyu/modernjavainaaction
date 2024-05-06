package org.src.chapter9;

import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.src.chapter9.Debugging.*;

class DebuggingTest {

    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);
        int result = Point.compareByXAndThenY.compare(p1, p2);
        assertTrue(result < 0);
    }

    @Test
    public void testMoveAllPointsRightBy() throws Exception {
        List<Point> points = Arrays.asList(new Point(5,  5), new Point(10, 5));
        List<Point> expectedPoints = Arrays.asList(new Point(15,  5), new Point(20, 5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        assertEquals(expectedPoints, newPoints);
    }

}