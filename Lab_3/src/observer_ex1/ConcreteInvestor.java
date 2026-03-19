package observer_ex1;

public class ConcreteInvestor implements Investor{
    private String name;

    public ConcreteInvestor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double price) {
        System.out.println(name + " received update: " + stockName + " price is now " + price);
    }
}
