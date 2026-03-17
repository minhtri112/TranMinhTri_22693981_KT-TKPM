package ex2_2;

public class ConsumptionTax extends ProductTax{


    public ConsumptionTax(Product product) {
        super(product);
        System.out.println("Consumption Tax is 5% : " + getPrice());
    }

    @Override
    public double getPrice() {
        return product.getPrice() + product.getBasePrice()*0.05;
    }

    @Override
    public double getBasePrice() {
        return product.getBasePrice();
    }
}
