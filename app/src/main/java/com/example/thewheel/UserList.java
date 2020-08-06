package com.example.thewheel;

import java.util.ArrayList;

public class UserList {

    public ArrayList<User> userArray;
    public String userListId;

    public UserList(String id) {
        this.userArray = new ArrayList<>();
        this.userListId = id;
    }
    public void addUser(String id, String name, String colour) {
        User newUser = new User(id, name, colour);
        this.userArray.add(newUser);
    }
    public User getUser(String id) {
        for (int x = 0; x < this.userArray.size(); x++) {
            System.out.println(this.userArray.get(x).id);
            if (this.userArray.get(x).id == id) {
                return this.userArray.get(x);
            }
        }
        System.out.println("Specified user not found!");
        return new User("Null", "Null", "Null");
    }
}
