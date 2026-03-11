package factory_abstract.impl;

import factory_abstract.CoffeeTable;

public class VictorianCoffeeTable implements CoffeeTable {

    @Override
    public void putCoffee() {
        System.out.println("Putting coffee on victorian coffee table");
    }

    @Override
    public String getSize() {
        return "Large";
    }
}
