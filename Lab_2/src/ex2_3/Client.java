package ex2_3;

public class Client {
    public static void main(String[] args) {
        // dercorator
        Order order = new OrderBase(100);
        order = new DiscountFee(order);
        order = new ProcessingFee(order);

        // Strategy
        System.out.println(order.getPrice());
        PaymentContext paymentContext = new PaymentContext(new CreditCardPayment());

        paymentContext.executePayment(order);



    }
}
