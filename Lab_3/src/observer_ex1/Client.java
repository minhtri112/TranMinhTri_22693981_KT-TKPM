package observer_ex1;

public class Client {
    public static void main(String[] args) {
        Stock apple = new Stock("Apple", 150);

        Investor investor1 = new ConcreteInvestor("Tri");
        Investor investor2 = new ConcreteInvestor("Nam");

        apple.addInvestor(investor1);
        apple.addInvestor(investor2);

        apple.setPrice(200);

        apple.removeInvestor(investor1);
        apple.setPrice(300);

    }
}
