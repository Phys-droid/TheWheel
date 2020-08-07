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
        System.out.println("My Name: " + newUser.name);
        this.userArray.add(newUser);
    }
    public User getUser(String id) {
        for (int x = 0; x < this.userArray.size(); x++) {
            //System.out.println("I'm fetching: " + this.userArray.get(x).id + "My Name is: " + this.userArray.get(x).name);
            // System.out.println("Is: [" + this.userArray.get(x).id + "] Same as:  [" + id + "]");
            if (this.userArray.get(x).id.equals(id)) {
                return this.userArray.get(x);
            }
        }
        System.out.println("Specified user not found!"); // Would be better to return empty list, shouldn't return null
        return null;
    }
    public ArrayList<String> getAllNames() {
        ArrayList<String> nameList = new ArrayList();
        for (int x = 0; x < this.userArray.size(); x++) {
            nameList.add(this.userArray.get(x).name);
        }
        return nameList;
    }
}
