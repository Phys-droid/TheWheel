package com.example.thewheel;

public class User {
    String id;
    String name;
    String colour;

    public User(String id, String name, String colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    public void updateUser(String newName, String newColour) {
        this.name = newName;
        this.colour = newColour;
    }
}
