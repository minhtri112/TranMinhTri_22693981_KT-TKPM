package library_system.strategy;

import library_system.entity.Book;

import java.util.List;

public interface SearchBookStrategy {
    public void showBooks(String key, List<Book> books);
}
