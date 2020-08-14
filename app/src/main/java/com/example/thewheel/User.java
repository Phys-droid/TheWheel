package com.example.thewheel;

public class User {
    public static int userCounter = 0;
    String id;
    String name;
    String colour;

    public User(String name, String colour) {
        userCounter += 1;
        this.id = Integer.toString(userCounter);
        this.name = name;
        this.colour = colour;
    }

    public void updateUser(String newName, String newColour) {
        this.name = newName;
        this.colour = newColour;
    }
}
