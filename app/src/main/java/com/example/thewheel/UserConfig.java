package com.example.thewheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class UserConfig extends AppCompatActivity {

    public static User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);
        if (checkForCurrentUser() == true) {
            editUserMenu(currentUser);
        }
        else {
            createNewUserMenu();
        }


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

                storageControl.save(currentFocus, "User.txt");
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
        SetupList.userCounter = SetupList.userCounter + 1; // Add 1 to userCounter because new User will be added
        UserList currentUserList = SetupList.getUserList(Integer.toString(SetupList.userCounter)); // ToDo: Change to id of current setup/userlist
        String newid = Integer.toString(currentUserList.userArray.size() + 1);
        //System.out.println("---CURRENTLIST:--- " + currentUserList.userListId);
        currentUserList.addUser(newid, username, colour);
        //System.out.println("BEFORE: " + currentUserList.getUser("1").name);
        //Remove if done
        //System.out.println("---USER:---");

        System.out.println("PREF: " + showPreferences());

    }

    String showPreferences() {
        return SetupList.getUserList("1").getUserById("1").name;
    }

    private boolean checkForCurrentUser() {
        boolean CurrentUserExists = false;
        if (currentUser != null) {
            CurrentUserExists = true;
        }
        return CurrentUserExists;
    }

    private void createNewUserMenu() {
        //Auto fill in some color
        // ToDo: Make color random
        EditText colourBox = (EditText) findViewById(R.id.colourInput);
        colourBox.setText("#640808");
    }

    private void editUserMenu(User editUser) {
        EditText colourBox = (EditText) findViewById(R.id.colourInput);
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        colourBox.setText(editUser.colour);
        nameBox.setText(editUser.name);
    }



}