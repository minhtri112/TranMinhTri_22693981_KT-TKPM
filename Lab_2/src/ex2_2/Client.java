package ex2_2;

public class Client {
    public static void main(String[] args) {
        Product product = new BasicProduct(100);

        product = new VatTax(product);
        product = new ConsumptionTax(product);
        product = new LuxuryTax(product);

        System.out.println("Final price: " + product.getPrice());
    }
}
