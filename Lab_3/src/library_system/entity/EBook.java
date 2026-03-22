package library_system.entity;

public class EBook extends Book{
    private double fileSize;
    private String link;

    public EBook(String id, String title, String author, double fileSize, String link) {
        super(id, title, author);
        this.fileSize = fileSize;
        this.link = link;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" | Loại: E-Book | Dung lượng: %-5.2fMB | Link: %s", fileSize, link);
    }
}
