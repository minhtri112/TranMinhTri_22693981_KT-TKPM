package ex2_2;

public abstract class ProductTax extends Product{
    protected Product product;
    public ProductTax(Product product){
        this.product = product;
    }

}
