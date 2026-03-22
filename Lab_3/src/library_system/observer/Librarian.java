package library_system.observer;
import library_system.entity.Book;

import java.util.ArrayList;
import java.util.List;


public class Librarian {
    private Book book;
    private List<LibraryObserver> observers;
    public Librarian(Book book){
        this.book = book;
        observers = new ArrayList<>();
    }
    public void notifyInvestors(){
        for(LibraryObserver observer : observers){
            observer.update(book.getTitle(),"Xem thu di");
        }
    }
    public void addObserver(LibraryObserver observer){
        observers.add(observer);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<LibraryObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<LibraryObserver> observers) {
        this.observers = observers;
    }
}
