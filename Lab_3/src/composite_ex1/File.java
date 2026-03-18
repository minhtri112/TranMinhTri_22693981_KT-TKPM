package composite_ex1;

public class File implements FileSystemItem{
    private String name;
    private int size;

    public File(String name, int size){
        this.name = name;
        this.size = size;
    }

    @Override
    public void show(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + "KB)");
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
