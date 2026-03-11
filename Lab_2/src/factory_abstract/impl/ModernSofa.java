package factory_abstract.impl;

import factory_abstract.Sofa;

public class ModernSofa implements Sofa {
    @Override
    public void lieOn() {
        System.out.println("Lying on a modern sofa");
    }

    @Override
    public boolean isComforatable() {
        return true;
    }
}
