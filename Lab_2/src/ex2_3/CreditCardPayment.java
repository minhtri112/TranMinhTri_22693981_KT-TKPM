package ex2_3;

public class CreditCardPayment implements PaymentStrategy{

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toán bằng thẻ: " + order.getPrice());
    }
}
