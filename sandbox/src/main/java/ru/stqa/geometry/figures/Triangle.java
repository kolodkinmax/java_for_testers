package ru.stqa.geometry.figures;

public record Triangle(double a, double b, double c) {


    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area() {
        var halfPerimeter = perimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - this.a) * (halfPerimeter - this.b) * (halfPerimeter - this.c));
    }
}
