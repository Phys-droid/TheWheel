package com.example.thewheel;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;

public class StorageControl {

    //ToDo: Add following Code to the manifest:
    /*
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     */

    public void save(View currentView) {
        Context currentContext = currentView.getContext();
        String filenameExternal = "Users.txt";

        //Text of the Document
        // ToDo: Fill in all users.
        String textToWrite = "AllUsersListedHere";

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
            outputStream = new FileOutputStream(file, false);

            outputStream.write(textToWrite.getBytes());
            System.out.println("SUCCESFULLY WRITTEN: " + file);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
