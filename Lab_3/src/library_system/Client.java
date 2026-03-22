package library_system;

import library_system.dto.BookDto;
import library_system.entity.User;
import library_system.factory.BookFactory;
import library_system.factory.EBookFactory;
import library_system.factory.PhysicalBookFactory;
import library_system.observer.LibraryObserver;
import library_system.singleton.Library;


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

        System.out.println("============= Tim kiem sach  ============");
        System.out.println("============= Tim theo title  ============");
        library.searchForTitle("Tâm");
        System.out.println("============= Tim theo author  ============");
        library.searchForAuhor("a");


        System.out.println("============= user theo doi trang thai sach  ============");
        LibraryObserver user1 = new User(111, "Tri");
        LibraryObserver user2 = new User(112, "Minh");
        library.addUserTolibrarian(user1,library.findById("P001"));
        library.addUserTolibrarian(user2,library.findById("P001"));

        library.updateBook("P001", "Dế Mèn Phiêu Lưu Ký kkk");




    }
}
