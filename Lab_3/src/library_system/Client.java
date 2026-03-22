package library_system;

import library_system.dto.BookDto;
import library_system.entity.Book;
import library_system.entity.PhysicalBook;
import library_system.factory.BookFactory;
import library_system.factory.EBookFactory;
import library_system.factory.PhysicalBookFactory;
import library_system.singleton.Library;

import java.util.List;

public class Client {
    public static void main(String[] args) {
        Library library = Library.getInstance();


        BookDto dto1 = new BookDto("P001", "Dế Mèn Phiêu Lưu Ký", "Tô Hoài", 300);

        BookDto dto2 = new BookDto("P002", "Đắc Nhân Tâm", "Dale Carnegie", 450);

        BookDto dto3 = new BookDto("E001", "Java Design Patterns", "Erich Gamma", 12.5, "https://lib.com/java-dp.pdf");


        // Tạo Factory cho sách giấy
        BookFactory physicalFactory = new PhysicalBookFactory();
        library.addBook(physicalFactory.createBook(dto1));
        library.addBook(physicalFactory.createBook(dto2));

        // Tạo Factory cho sách điện tử
        BookFactory eBookFactory = new EBookFactory();
        library.addBook(eBookFactory.createBook(dto3));

        library.show();

    }
}
