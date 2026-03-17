package ex2_2;

public class BasicProduct extends Product{
    private double price;

    public BasicProduct(double price){
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getBasePrice() {
        return price;
    }
}
