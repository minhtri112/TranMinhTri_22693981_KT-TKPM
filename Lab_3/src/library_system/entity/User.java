package library_system.entity;

import library_system.observer.LibraryObserver;

public class User implements LibraryObserver {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(String title,String message) {
        System.out.println(name + " oi " + title + ": " + message);
    }
}
