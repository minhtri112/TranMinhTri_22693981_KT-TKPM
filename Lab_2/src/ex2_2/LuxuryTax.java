package ex2_2;

public class LuxuryTax extends ProductTax{

    public LuxuryTax(Product product) {
        super(product);
        System.out.println("Luxury Tax is 15% : " +  getPrice());
    }

    @Override
    public double getPrice() {
        return product.getPrice() + product.getBasePrice()*0.15;
    }

    @Override
    public double getBasePrice() {
        return product.getBasePrice();
    }
}
