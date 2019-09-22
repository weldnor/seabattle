package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    private Point point1;
    private Point point2;
    private Point point3;

    @Before
    public void testSetUp() {
        point1 = new Point(1, 2);
        point2 = new Point(1, 2);
        point3 = new Point(2, 3);
    }

    @Test
    public void testGetX() {
        assertThat(point1.getX()).isEqualTo(1);
        assertThat(point1.getX()).isNotEqualTo(2);
        assertThat(point1.getX()).isNotNull();
    }

    @Test
    public void testGetY() {
        assertThat(point1.getY()).isEqualTo(2);
        assertThat(point1.getY()).isNotEqualTo(1);
        assertThat(point1.getY()).isNotNull();
    }

    @Test
    public void testEquals() {
        assertThat(point1).isEqualTo(point2);
        assertThat(point1).isNotEqualTo(point3);
    }

    @Test
    public void testClone() {
        Point newPoint = (Point) point1.clone();
        assertThat(newPoint).isEqualTo(point1);
        assertThat(newPoint).isNotEqualTo(point3);
    }
}