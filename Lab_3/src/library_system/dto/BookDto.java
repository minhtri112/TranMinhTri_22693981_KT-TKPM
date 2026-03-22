package library_system.dto;

public class BookDto {
    private String id;
    private String title;
    private String author;
    // ebook
    private double fileSize;
    private String link;
    // physicalbook
    private int weight;
    public BookDto(String id, String title, String author, double fileSize, String link) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.fileSize = fileSize;
        this.link = link;
    }
    public BookDto(String id, String title, String author,int weight) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
