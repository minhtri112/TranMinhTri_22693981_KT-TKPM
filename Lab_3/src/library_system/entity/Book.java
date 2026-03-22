package library_system.entity;

public class Book {
    protected String id;
    protected String title;
    protected String author;
    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return String.format("ID: %-5s | Tên: %-20s | Tác giả: %-15s", id, title, author);
    }
}
