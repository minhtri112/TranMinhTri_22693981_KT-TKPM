package composite_ex2;
import java.util.ArrayList;
import java.util.List;

public class Container extends BaseComponent {
    protected List<UIComponent> children = new ArrayList<UIComponent>();

    public Container(String id) {
        super(id);
    }

    public void add(UIComponent component) {
        children.add(component);
    }

    public void remove(UIComponent component) {
        children.remove(component);
    }

    @Override
    public void reder(String indent) {
        System.out.println(indent + "[Container] " + id + (disabled ? " (disabled)" : ""));
        for (UIComponent child : children) {
            child.reder(indent + "  ");
        }
    }

    @Override
    public void click() {
        System.out.println("Click on container: " + id);
        for (UIComponent child : children) {
            child.click();
        }
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        for(UIComponent child : children) {
            child.setDisabled(disabled);
        }
    }

    @Override
    public UIComponent findById(String id){
        if(this.id.equals(id)) return this;
        for(UIComponent child : children) {
            UIComponent found = child.findById(id);
            if(found != null) return found;
        }
        return null;
    }

}
