package library_system.factory;

import library_system.dto.BookDto;
import library_system.entity.EBook;

public class EBookFactory extends BookFactory{

    @Override
    public EBook createBook(BookDto bookDto) {
        return new EBook(bookDto.getId(),bookDto.getTitle(), bookDto.getAuthor(), bookDto.getFileSize(),bookDto.getLink());
    }
}
