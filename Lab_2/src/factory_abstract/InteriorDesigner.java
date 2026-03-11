package factory_abstract;

class InteriorDesigner {
    private Chair chair;
    private Sofa sofa;
    private CoffeeTable coffeeTable;
    public InteriorDesigner(FurnitureFactory furnitureFactory) {
        this.chair = furnitureFactory.createChair();
        this.sofa = furnitureFactory.createSofa();
        this.coffeeTable = furnitureFactory.createCoffeeTable();
    }
    public void decorate() {
        chair.sitOn();
        sofa.lieOn();
        coffeeTable.putCoffee();
        System.out.println("Table size: " + coffeeTable.getSize());
    }
}