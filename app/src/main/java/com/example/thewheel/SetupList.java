package com.example.thewheel;
import java.util.ArrayList;



public class SetupList {
    //ToDo: Make SetupList read in files and save lists to file, with help of StorageControl

    public static int userCounter = 0;
    public static String currentSetup = "1";

    public static ArrayList<UserList> setupList = new ArrayList<>();

    public static void addSetup(String id) {
        UserList newUserList = new UserList(id);
        setupList.add(newUserList);
    }

    public static UserList getUserList(String id) {
        for (int x = 0; x < setupList.size(); x++) {
            System.out.println(setupList.get(x).userListId);
            if (setupList.get(x).userListId.equals(id)) {

                return setupList.get(x);
            }
        }
        System.out.println("Specified userList not found!");
        return null; // Would be better to return empty list, shouldn't return null
    }

    public static User searchAllListsByName (String name) {
        for (int x = 0; x < setupList.size(); x++) {
            User foundUser = setupList.get(x).getUserByName(name);
            if (!foundUser.name.equals("N/A")) {
                return foundUser;
            }
        }
        return new User("-1","N/A","N/A");
    }


}
