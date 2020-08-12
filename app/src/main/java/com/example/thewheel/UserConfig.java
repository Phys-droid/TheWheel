package com.example.thewheel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class UserConfig extends AppCompatActivity {

    public static User currentUser = null;
    public static String currentColour = "#29b012"; // ToDo: Add correct colour in beginning


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);

        // Check if User exists in order to load, otherwise create new user
        if (checkForCurrentUser() == true) {
            editUserMenu(currentUser);
        } else {
            createNewUserMenu();
        }

        // Set correct colour for bar
        TextView colorBar = (TextView) findViewById(R.id.colourBar);
        colorBar.setTextColor(Color.parseColor(currentColour));


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
                EditText nameBox = (EditText) findViewById(R.id.nameInput);
                EditText colourBox = (EditText) findViewById(R.id.colourInput);

                if (currentUser != null) {
                    updateNameAndColour(nameBox.getText().toString(), colourBox.getText().toString());
                } else {
                    saveNameAndColour(nameBox.getText().toString(), colourBox.getText().toString());
                }
                // ToDo: Move saving
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl storageControl = new StorageControl();
                System.out.println("CurrentView: " + currentFocus);
                storageControl.save(currentFocus, "Wheel_Config.txt");
                // Move saving
                Intent intent = new Intent(UserConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });

        // Create Listener for SeekBars
        SeekBar seekBarRed = (SeekBar) findViewById(R.id.seekRed);
        SeekBar seekBarGreen = (SeekBar) findViewById(R.id.seekGreen);
        SeekBar seekBarBlue = (SeekBar) findViewById(R.id.seekBlue);
        int seekValueRed = seekBarRed.getProgress();
        int seekValueGreen = seekBarGreen.getProgress();
        int seekValueBlue = seekBarBlue.getProgress();

        //ToDo: Find way to do this once

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                SeekBar seekBarRed = (SeekBar) findViewById(R.id.seekRed);
                int seekValueRed = seekBarRed.getProgress();
                ColourRgb oldColour = ColourRgb.hexToRgb(currentColour);
                oldColour.red = seekValueRed;
                currentColour = ColourRgb.rgbToHex(oldColour);
                TextView colorBar = (TextView) findViewById(R.id.colourBar);
                colorBar.setTextColor(Color.parseColor(currentColour));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private void saveNameAndColour(String username, String colour) {
        SetupList.userCounter = SetupList.userCounter + 1; // Add 1 to userCounter because new User will be added
        UserList currentUserList = SetupList.getUserList(Integer.toString(1)); // ToDo: Change to id of current setup/userlist
        String newid = Integer.toString(SetupList.userCounter);
        //System.out.println("---CURRENTLIST:--- " + currentUserList.userListId);
        currentUserList.addUser(newid, username, colour);
        //System.out.println("BEFORE: " + currentUserList.getUser("1").name);
        //Remove if done
        //System.out.println("---USER:---");

        System.out.println("PREF: " + showPreferences());

    }

    private void updateNameAndColour(String username, String colour) {
        // ToDo: Properly update the users fields
        SetupList.searchAllListsByName(currentUser.name).updateUser(username, colour);
        currentUser = null;
    }

    String showPreferences() {
        return SetupList.getUserList("1").getUserById("1").name; //ToDo: Update or remove this method
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

        String colorRGB = "#";
        Random random = new Random();
        for (int x = 0; x < 6; x++) {
            colorRGB = colorRGB + random.nextInt(10);
        }
        colourBox.setText(colorRGB);
    }

    private void editUserMenu(User editUser) {
        EditText colourBox = (EditText) findViewById(R.id.colourInput);
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        colourBox.setText(editUser.colour);
        nameBox.setText(editUser.name);
    }

    private void getColors() {
        SeekBar redBar = findViewById(R.id.seekRed);
        SeekBar greenBar = findViewById(R.id.seekGreen);
        SeekBar blueBar = findViewById(R.id.seekBlue);
        //String hexColour = Colour.rgbToHex(redBar, greenBar, blueBar);


    }


}