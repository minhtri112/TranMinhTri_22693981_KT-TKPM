package observer_ex2;

public class Client {
    public static void main(String[] args) {
        Tasks tasks = new Tasks("Backend", "init");

        Employee e1 = new Employee("Tri");
        Employee e2 = new Employee("Dung");
        Manager m = new Manager("Hoa");

        tasks.addPerson(e1);
        tasks.addPerson(e2);
        tasks.addPerson(m);

        tasks.setStatus("done");

    }
}
