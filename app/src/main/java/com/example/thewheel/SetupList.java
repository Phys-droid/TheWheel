package com.example.thewheel;
import java.util.ArrayList;
import java.util.Objects;


public class SetupList {
    //ToDo: Make SetupList read in files and save lists to file, with help of StorageControl

    public static String currentSetup = "1";

    public static ArrayList<UserList> setupList = new ArrayList<>();

    public static void addSetup(UserList newUserList) {
        setupList.add(newUserList);
    }

    public static UserList getUserList(String id) {
        for (int x = 0; x < setupList.size(); x++) {
            //System.out.println(setupList.get(x).userListId);
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
            if (foundUser != null) {
                return foundUser;
            }
        }
        return null;
    }

    public static UserList getSetupListById(String id) {
        for (int x = 0; x < setupList.size(); x++) {
            if (setupList.get(x).userListId.equals(id)) {
                return setupList.get(x);
            }
        }
        return null;
    }

    public static UserList getCurrentUserList() {
        return getSetupListById(currentSetup);
    }

    public static ArrayList<String> getAllSetupIds() {
        ArrayList<String> nameList = new ArrayList();
        for (int x = 0; x < setupList.size(); x++){
            nameList.add(setupList.get(x).userListId);
        }
        return nameList;
    }
}
