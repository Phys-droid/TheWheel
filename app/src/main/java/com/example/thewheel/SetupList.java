package com.example.thewheel;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//These imports are needed to store and get data from files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetupList {
    //ToDo: Make SetupList read in files and save lists to file, with help of StorageControl


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
}
