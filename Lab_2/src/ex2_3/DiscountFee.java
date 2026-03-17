package ex2_3;

public class DiscountFee extends CalFee{

    public DiscountFee(Order order) {
        super(order);
        System.out.println("Discount Fee is 10% : " + getPrice());
    }

    @Override
    public double getPrice() {
        return order.getPrice() - order.getPriceBase()*0.1;
    }

    @Override
    public double getPriceBase() {
        return 0;
    }
}
