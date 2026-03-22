package library_system.strategy;

import library_system.entity.Book;

import java.util.List;

public class SearchBookContext {
    private SearchBookStrategy strategy;
    public SearchBookContext(SearchBookStrategy strategy) {
        this.strategy = strategy;
    }
    public void showBooks(String key, List<Book> books){
        strategy.showBooks(key,books);
    }
}
