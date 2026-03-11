package factory_method;

public class CatFactory extends AnimalFactory{

    @Override
    protected Animal createAnimal() {
        return new Cat();
    }
}
