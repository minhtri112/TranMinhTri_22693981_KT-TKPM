package library_system.singleton;

import library_system.entity.Book;
import library_system.entity.EBook;
import library_system.entity.PhysicalBook;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private List<Book> books = new ArrayList<Book>();
    private Library() {};

    public static Library getInstance() {
        if(instance == null){
            instance = new Library();
        }
        return instance;
    }
    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() { return books; }
}
