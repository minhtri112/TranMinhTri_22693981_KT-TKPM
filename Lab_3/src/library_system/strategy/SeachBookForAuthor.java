package library_system.strategy;

import library_system.entity.Book;

import java.util.List;

public class SeachBookForAuthor implements SearchBookStrategy{

    @Override
    public void showBooks(String key, List<Book> books) {
        for (Book book : books) {
            if(book.getAuthor().contains(key)){
                System.out.println(book);
            }
        }
    }
}
