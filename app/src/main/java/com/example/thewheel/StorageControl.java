package com.example.thewheel;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StorageControl {

    public void save(View currentView, String filename) {
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
            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            //file.getParentFile().mkdirs();

            // Here the writing process takes place
            outputStream = new FileOutputStream(file, false);

            UserList currentUserList = SetupList.getUserList(SetupList.currentSetup);
            writeLine("CURRENT_SETUP_NUMBER: " + SetupList.currentSetup, outputStream);
            writeLine("NUMBER_OF_USERS: " + SetupList.userCounter, outputStream);
            for (int x = 0; x < currentUserList.getArraySize(); x ++) {
                System.out.println("LOOOK:-------" + x);
                writeLine("USER_ID: " + currentUserList.userArray.get(x).id, outputStream);
                writeLine("USER_NAME: " + currentUserList.userArray.get(x).name, outputStream);
                writeLine("USER_COLOUR: " + currentUserList.userArray.get(x).colour, outputStream);
            }

            System.out.println("SUCCESFULLY WRITTEN: " + file);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(View currentView, String filename) {
        //ToDo: Load config from .txt file
    }

    public void writeLine (String line, OutputStream outputStream) throws IOException {
        outputStream.write((line + "\n").getBytes());
    }
}
