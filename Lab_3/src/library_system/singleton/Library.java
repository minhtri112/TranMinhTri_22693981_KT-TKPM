package library_system.singleton;

import library_system.entity.Book;
import library_system.strategy.SeachBookForAuthor;
import library_system.strategy.SearchBookContext;
import library_system.strategy.SearchBookForName;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private List<Book> books = new ArrayList<Book>();
    private SearchBookContext searchBookContext;
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

    public void show(){
        System.out.println("Tổng số sách trong thư viện: " + books.size());
        for (Book b : books) {
            System.out.println(b.getClass());
            System.out.println(b);
        }
    }

    public void searchForTitle(String key){
        searchBookContext = new SearchBookContext(new SearchBookForName());
        searchBookContext.showBooks(key,books);
    }

    public void searchForAuhor(String key){
        searchBookContext = new SearchBookContext(new SeachBookForAuthor());
        searchBookContext.showBooks(key,books);
    }

    public List<Book> getBooks() { return books; }
}
