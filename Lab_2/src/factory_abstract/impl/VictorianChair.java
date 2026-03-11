package factory_abstract.impl;

import factory_abstract.Chair;

public class VictorianChair implements Chair {

    @Override
    public void sitOn() {
        System.out.println("Sitting on a victorian chair");
    }

    @Override
    public boolean hasLegs() {
        return true;
    }
}
