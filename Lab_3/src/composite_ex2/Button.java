package composite_ex2;

public class Button extends BaseComponent{
    private String label;

    public Button(String id,String label) {
        super(id);
        this.label = label;
    }

    @Override
    public void reder(String indent) {
        System.out.println(indent + "[Button] " + label + (disabled ? " (disabled)" : ""));
    }

    @Override
    public void click() {
        if (disabled) {
            System.out.println(label + " is disabled!");
        } else {
            System.out.println("Clicked button: " + label);
        }
    }
}
