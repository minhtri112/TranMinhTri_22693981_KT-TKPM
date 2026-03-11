package factory_abstract.impl;

import factory_abstract.Sofa;

public class VictorianSofa implements Sofa {

    @Override
    public void lieOn() {
        System.out.println("Lying on a victorian sofa");
    }

    @Override
    public boolean isComforatable() {
        return true;
    }
}
