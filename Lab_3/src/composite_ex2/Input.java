package composite_ex2;

public class Input extends BaseComponent{
    private String value;

    public Input(String id, String value) {
        super(id);
        this.value = value;
    }

    @Override
    public void reder(String indent) {
        System.out.println(indent + "[Input] value=" + value + (disabled ? " (disabled)" : ""));
    }

    @Override
    public void click() {
        System.out.println("Focus input: " + id);
    }
}
