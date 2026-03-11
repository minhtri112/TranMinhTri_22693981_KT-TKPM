package factory_abstract;

import factory_abstract.impl.ModernFurnitureFactory;

public class Client {
    public static void main(String[] args) {
        FurnitureFactory factory = new ModernFurnitureFactory();
        InteriorDesigner interiorDesigner = new InteriorDesigner(factory);
        interiorDesigner.decorate();
    }
}
