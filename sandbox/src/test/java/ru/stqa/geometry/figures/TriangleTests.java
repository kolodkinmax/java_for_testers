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

    @Test
    void cannotCreateTriangleWithNegativeSide() {
        try {
            new Triangle(-5.0, -5.0, -6.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void canCreateTriangleWithNonNegativeSide() {
        try {
            new Triangle(5.0, 5.0, 6.0);
            Assertions.fail("Все стороны треугольника > 0");
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateTriangleWithSideGreaterSumOtherSides() {
        try {
            new Triangle(10.0, 4.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void canCreateTriangleWithSideNonGreaterSumOtherSides() {
        try {
            new Triangle(3.0, 3.0, 6.0);
            Assertions.fail("Длина стороны не может быть больше суммы длин двух других сторон");
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
}
