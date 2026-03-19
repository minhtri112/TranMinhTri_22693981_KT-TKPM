package observer_ex2;

public class Manager implements Person {
    private String name;
    public Manager(String name) {
        this.name = name;
    }
    @Override
    public void update(String workName, String status) {
        System.out.println(name + " cong viec " + workName + " da thay doi trang thai thanh " + status);
    }
}
