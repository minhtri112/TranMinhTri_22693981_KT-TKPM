package composite_ex2;

import java.awt.*;

public class Modal extends Container {
    public Modal(String id) {
        super(id);
    }
    @Override
    public void reder(String indent) {
        System.out.println(indent + "=== Modal ===");
        super.reder(indent + "  ");
        System.out.println(indent + "=============");
    }
}
