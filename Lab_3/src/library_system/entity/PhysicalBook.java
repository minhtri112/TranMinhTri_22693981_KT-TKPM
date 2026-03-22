package library_system.entity;

public class PhysicalBook extends Book{
    private int weight;

    public PhysicalBook(String id, String title, String author, int weight) {
        super(id, title, author);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Loại: Sách Giấy | Trọng lượng: %dg", weight);
    }
}
