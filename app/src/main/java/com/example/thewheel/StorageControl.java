package com.example.thewheel;

import android.content.Context;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class StorageControl {
    public static String configname = "Wheel_Config.txt";

    public static void save(View currentView, String filename) {
        Context currentContext = currentView.getContext();
        String filenameExternal = filename;

        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //If it isn't mounted - we can't write into it.
            return;
        }
        //Create a new file that points to the root directory, with the given name:
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), filenameExternal);

        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();

            // Here the writing process takes place
            outputStream = new FileOutputStream(file, false);
            for (int x = 0; x < SetupList.setupList.size(); x++) {
                UserList currentUserList = SetupList.setupList.get(x);
                writeLine("NUMBER_OF_SETUPS:", outputStream);
                writeLine(Integer.toString(SetupList.setupList.size()), outputStream);
                writeLine("NUMBER_OF_USERS:", outputStream);
                writeLine(Integer.toString(User.userCounter), outputStream);
                writeLine("CURRENT_SETUP_ID:", outputStream);
                writeLine(SetupList.currentSetup, outputStream);
                if (currentUserList != null) {
                    SetupList.currentSetup = currentUserList.userListId;
                    writeLine("SETUP_ID:", outputStream);
                    writeLine(SetupList.currentSetup, outputStream);
                    for (int y = 0; x < currentUserList.getArraySize(); x++) {
                        writeLine("USER_ID:", outputStream);
                        writeLine(currentUserList.userArray.get(x).id, outputStream);
                        writeLine("USER_NAME:", outputStream);
                        writeLine(currentUserList.userArray.get(x).name, outputStream);
                        writeLine("USER_COLOUR:", outputStream);
                        writeLine(currentUserList.userArray.get(x).colour,outputStream);
                    }

                    System.out.println("SUCCESFULLY WRITTEN: " + file);
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(View currentView) {
        if (!configAlreadyExists()) {
            System.out.println("File does not exist. Can't load!");
            return;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), configname);
        try {
            InputStream inputStream = new FileInputStream(file);
            String configtext = readText(inputStream);
            String[] configlines = configtext.split(System.getProperty("line.separator"));
            String currentName = null;
            String currentColour = null;
            UserList currentUserList = null;

            for (int x = 0; x < configlines.length; x++) {
                //System.out.println("Configline[x]: " + configlines[x]);
                if (configlines[x].equals("NUMBER_OF_SETUPS:")) {
                    x += 1;
                }
                if (configlines[x].equals("NUMBER_OF_USERS:")) {
                    x += 1;
                    User.userCounter = Integer.parseInt(configlines[x]);
                }
                if (configlines[x].equals("CURRENT_SETUP_ID:")) {
                    x += 1;
                    SetupList.currentSetup = configlines[x];
                }
                if (configlines[x].equals("SETUP_ID:")) {
                    x += 1;
                    currentUserList = new UserList();
                    SetupList.addSetup(currentUserList);
                }
                if (configlines[x].equals("USER_ID:")) {
                    x += 1;
                }
                if (configlines[x].equals("USER_NAME:")) {
                    x += 1;
                    currentName = configlines[x];
                }
                if (configlines[x].equals("USER_COLOUR:")) {
                    x += 1;
                    currentColour = configlines[x];
                    // Save User
                    System.out.println("CURRENT_USERLIST_ID: " + currentUserList.userListId);
                    currentUserList.addUser(currentName, currentColour);
                }
             }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLine(String line, OutputStream outputStream) throws IOException {
        outputStream.write((line + "\n").getBytes());
    }

    public static String readText(InputStream inputStream) throws IOException {
        int nextByte;
        String text = "";
        while ((nextByte = inputStream.read()) != -1) {
            //System.out.println("IN WHILE: " + text);
            text += (char)nextByte;
        }
        System.out.println("TEXT: " + text);
        return text;
    }

    public static boolean configAlreadyExists() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), configname);
        return file.exists();
    }
}
