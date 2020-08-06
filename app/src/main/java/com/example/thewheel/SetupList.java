package com.example.thewheel;

import java.util.ArrayList;

public class SetupList {
    public static ArrayList<UserList> setupList = new ArrayList<>();

    public static void addSetup(String id) {
        UserList newUserList = new UserList(id);
        setupList.add(newUserList);
    }
    public static UserList getUserList(String id) {
        for (int x = 0; x < setupList.size(); x++) {
            System.out.println(setupList.get(x).userListId);
            System.out.println("--------XX---------");
            System.out.println(setupList.get(x).getUser("1").name);
            if (setupList.get(x).userListId == id) {
                return setupList.get(x);
            }
        }
        System.out.println("Specified user not found!");
        return null; // Would be better to return empty list
    }
}
