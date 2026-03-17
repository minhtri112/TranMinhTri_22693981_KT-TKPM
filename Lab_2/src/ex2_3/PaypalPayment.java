package ex2_3;

public class PaypalPayment implements PaymentStrategy{

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toán bằng PayPal: " + order.getPrice());
    }
}
