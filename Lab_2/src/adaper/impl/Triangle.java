package adaper.impl;

import adaper.TriInterface;

public class Triangle implements TriInterface {
    private double baseLength;
    private double height;
    public Triangle(double baseLength, double height) {
        this.baseLength = baseLength;
        this.height = height;
    }
    @Override
    public void aboutTriangle() {
        System.out.println("Shape type: Triangle.");
    }

    @Override
    public double calculateTriangleArea() {
        return 0.5 * baseLength * height;
    }
}
