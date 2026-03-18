package composite_ex1;

public class Client {
    public static void main(String[] args) {
        Folder folder = new Folder("root");
        File file1 = new File("file1", 2);
        File file2 = new File("file2", 2);
        Folder subFolder = new Folder("subFolder");
        File file3 = new File("file3", 3);

        subFolder.add(file3);
        subFolder.add(file2);
        folder.add(file1);
        folder.add(subFolder);

//        subFolder.show("");
        folder.show("");
    }
}
