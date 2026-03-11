package factory_abstract.impl;

import factory_abstract.CoffeeTable;

public class ModernCoffeeTable implements CoffeeTable {

    @Override
    public void putCoffee() {
        System.out.println("Putting coffee on modern coffee table");
    }

    @Override
    public String getSize() {
        return "Medium";
    }
}
