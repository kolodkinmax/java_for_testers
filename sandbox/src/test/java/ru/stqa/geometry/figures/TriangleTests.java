package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void canCalculatePerimeter() {
        Assertions.assertEquals(18.0, new Triangle(2.0, 10.0, 6.0).perimeter());
    }

    @Test
    void canCalculateArea() {
        Assertions.assertEquals(6.0, new Triangle(3.0, 4.0, 5.0).area());
    }
}
