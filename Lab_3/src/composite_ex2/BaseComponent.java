package composite_ex2;

public abstract  class BaseComponent implements UIComponent{
    protected String id;
    protected boolean disabled = false;

    public BaseComponent(String id) {
        this.id = id;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public UIComponent findById(String id) {
        if (this.id.equals(id)) return this;
        return null;
    }

    @Override
    public String getId() {
        return id;
    }
}
