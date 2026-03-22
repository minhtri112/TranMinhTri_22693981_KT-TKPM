package library_system.singleton;

import library_system.entity.Book;
import library_system.observer.Librarian;
import library_system.observer.LibraryObserver;
import library_system.strategy.SeachBookForAuthor;
import library_system.strategy.SearchBookContext;
import library_system.strategy.SearchBookForName;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private List<Book> books = new ArrayList<Book>();
    private SearchBookContext searchBookContext;
    private List<Librarian> librarianList = new ArrayList<>();

    private Library() {};

    public static Library getInstance() {
        if(instance == null){
            instance = new Library();
        }
        return instance;
    }

    public Book findById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(String id, String title) {
        for(Book book : books){
            if(book.getId().equals(id)){
                book.setTitle(title);
                findLibrarian(id).notifyInvestors();
            }
        }
    }
    public Librarian findLibrarian(String bookId) {
        for(Librarian librarian : librarianList){
            if(librarian.getBook().getId().equals(bookId)){
                return librarian;
            }
        }
        return null;
    }
    public void addUserTolibrarian(LibraryObserver observer,Book book) {
        Librarian librarian = findLibrarian(book.getId());
        if(librarian == null){
            librarianList.add(new Librarian(book));
            librarian = findLibrarian(book.getId());
            librarian.addObserver(observer);
            return;
        }
        librarian.addObserver(observer);

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
