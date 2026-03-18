package composite_ex2;

public interface UIComponent {
    void reder(String indent);
    void click();
    void setDisabled(boolean disabled);
    UIComponent findById(String id);
    String getId();
}
