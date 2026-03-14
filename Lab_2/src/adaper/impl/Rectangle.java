package adaper.impl;


import adaper.RectInterface;

public class Rectangle implements RectInterface {
    private double length;
    private double width;
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    @Override
    public void aboutMe() {
        System.out.println("Shape type: Rectangle.");
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}
