package factory_method;

public class Cat implements Animal{

    @Override
    public void displayBehavior() {
        System.out.println("I am a cat");
        System.out.println("It talk : meo meo!");
    }
}
