package com.example.thewheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class UserConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);

        //Auto fill in some color
        // ToDo: Make color random
        EditText colourBox = (EditText) findViewById(R.id.colourInput);
        colourBox.setText("#640808");

        final Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });

        final Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ToDo: Move saving
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl storageControl = new StorageControl();
                System.out.println("CurrentView: " + currentFocus);

                storageControl.save(currentFocus);
                // Move saving

                EditText nameBox = (EditText) findViewById(R.id.nameInput);
                EditText colourBox = (EditText) findViewById(R.id.colourInput);
                saveNameAndColour(nameBox.getText().toString(), colourBox.getText().toString());
                Intent intent = new Intent(UserConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });
    }

    void saveNameAndColour(String username, String colour)  {
        UserList currentUserList = SetupList.getUserList("1"); // ToDo: Change to id of current setup/userlist
        String newid = Integer.toString(currentUserList.userArray.size() + 1);
        //System.out.println("---CURRENTLIST:--- " + currentUserList.userListId);
        currentUserList.addUser(newid, username, colour);
        //System.out.println("BEFORE: " + currentUserList.getUser("1").name);
        //Remove if done
        //System.out.println("---USER:---");

        System.out.println("PREF: " + showPreferences());

    }

    String showPreferences() {
        return SetupList.getUserList("1").getUser("1").name;
    }

}