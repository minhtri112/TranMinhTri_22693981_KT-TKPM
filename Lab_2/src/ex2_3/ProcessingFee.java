package ex2_3;

public class ProcessingFee extends CalFee{

    public ProcessingFee(Order order) {
        super(order);
        System.out.println("ProcessingFee is : " + 200);
    }

    @Override
    public double getPrice() {
        return order.getPrice() + 200;
    }

    @Override
    public double getPriceBase() {
        return order.getPriceBase();
    }
}
