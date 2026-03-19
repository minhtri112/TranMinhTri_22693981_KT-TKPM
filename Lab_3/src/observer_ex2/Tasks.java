package observer_ex2;

import java.util.ArrayList;
import java.util.List;

public class Tasks {
    private String name;
    private String status;
    private List<Person> people = new ArrayList<Person>();
    public Tasks(String name, String status) {
        this.name = name;
        this.status = status;
    }
    public void addPerson(Person person) {
        people.add(person);
    }
    public void removePerson(Person person) {
        people.remove(person);
    }
    public void notifyPersons() {
        for (Person person : people) {
            person.update(name,status);
        }
    }
    public void setStatus(String status) {
        this.status = status;
        notifyPersons();
    }
}
