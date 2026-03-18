package composite_ex1;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }
    public void add(FileSystemItem item) {
        children.add(item);
    }

    public void remove(FileSystemItem item) {
        children.remove(item);
    }

    @Override
    public void show(String indent) {
        System.out.println(indent + "+ Folder: " + name + " (" + getSize() + " KB)");
        for(FileSystemItem item : children) {
            item.show(indent + "  ");
        }
    }

    @Override
    public int getSize() {
        int total = 0;
        for(FileSystemItem item : children) {
            total += item.getSize();
        }
        return total;
    }
}
