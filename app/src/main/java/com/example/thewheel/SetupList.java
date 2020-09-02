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

    public static void removeLastSetup() {
        setupList.remove(setupList.size()-1);
    }

    public static boolean removeSetupByName(String setupName) {
        if (currentSetup.equals(setupName)) {
            if (setupList.size() == 1) {
                // ToDo: Make toast
                System.out.println("LAST SETUPLIST, CANNOT DELETE!!!");
                return false;
            }
            currentSetup = getSomeSetupName();
        }
        //System.out.println("MY INPUT: " + setupName);
        for (int x = 0; x < setupList.size(); x++) {
            //System.out.println("SetupList @ X: " + setupList.get(x).userListId);
            if (setupList.get(x).userListId.equals(setupName)) {
                //System.out.println("I'M REMOVING FOLOWING NAME: " + setupName);
                setupList.remove(x);
                return true;
            }
        }
        return false;
    }

    public static String getSomeSetupName() {
        return setupList.get(0).userListId;
    }

    public static UserList getUserList(String id) {
        for (int x = 0; x < setupList.size(); x++) {
            System.out.println("HI: " + setupList.get(x).userListId);
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
