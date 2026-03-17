package ex2_3;

public class OrderBase extends Order{
    private double price;

    public OrderBase(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getPriceBase() {
        return price;
    }
}
