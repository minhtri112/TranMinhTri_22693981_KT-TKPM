package library_system.factory;

import library_system.dto.BookDto;
import library_system.entity.Book;
import library_system.entity.PhysicalBook;

public class PhysicalBookFactory extends BookFactory{

    @Override
    public PhysicalBook createBook(BookDto bookDto) {
        return new PhysicalBook(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getWeight());
    }
}
