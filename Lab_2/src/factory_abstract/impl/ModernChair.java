package factory_abstract.impl;

import factory_abstract.Chair;

public class ModernChair implements Chair {

    @Override
    public void sitOn() {
        System.out.println("Sitting on a modern chair");
    }

    @Override
    public boolean hasLegs() {
        return true;
    }
}
