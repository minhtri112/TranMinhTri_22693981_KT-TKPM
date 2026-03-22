package library_system.factory;

import library_system.dto.BookDto;
import library_system.entity.Book;

public abstract class BookFactory {
   public abstract Book createBook(BookDto bookDto);
}
