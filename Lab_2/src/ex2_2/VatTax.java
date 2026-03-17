package ex2_2;

public class VatTax extends ProductTax{

    public VatTax(Product product) {
        super(product);
        System.out.println("VAT Tax is 10% : " + getPrice());
    }

    @Override
    public double getPrice() {
        return product.getPrice() + product.getBasePrice()*0.1;
    }

    @Override
    public double getBasePrice() {
        return product.getBasePrice();
    }
}
