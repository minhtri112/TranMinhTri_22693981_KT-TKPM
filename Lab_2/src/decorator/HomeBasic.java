package decorator;

public class HomeBasic extends Home{

    public HomeBasic() {
        super();
        // No additional cost for a basic home.
        System.out.println("The basic home with some standard facilities are ready.");
                System.out.println(" You need to pay $" + this.getPrice() + " for this.");
    }

    @Override
    public double getPrice() {
        return basePrice;
    }
}
