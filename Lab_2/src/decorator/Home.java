package decorator;

public abstract class Home {
    public double basePrice;
    public double additionalCost;
    public Home(double basePrice, double additionalCost) {
        this.basePrice = 10000;
        this.additionalCost = 0;
    }

    public Home() {

    }

    public abstract double getPrice();
}
