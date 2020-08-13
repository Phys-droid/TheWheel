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
    public static String currentColour = "#000000"; // ToDo: Add correct colour in beginning


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

                if (currentUser != null) {
                    updateNameAndColour(nameBox.getText().toString(), currentColour);
                } else {
                    saveNameAndColour(nameBox.getText().toString(), currentColour);
                }
                // ToDo: Move saving
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl storageControl = new StorageControl();
                //System.out.println("CurrentView: " + currentFocus);
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
                int seekValueRed = (int)(seekBarRed.getProgress()*2.55); //Adapt to go to 255
                //System.out.println("SeekValueRedBefore: " + (int)(seekBarRed.getProgress())+ " After: " + (int)(seekBarRed.getProgress()*2.55));
                ColourRgb oldColour = ColourRgb.hexToRgbConverter(currentColour);
                oldColour.red = seekValueRed;
                currentColour = ColourRgb.rgbToHexConverter(oldColour);
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
                SeekBar seekBarGreen = (SeekBar) findViewById(R.id.seekGreen);
                int seekValueGreen = (int)(seekBarGreen.getProgress()*2.55);
                ColourRgb oldColour = ColourRgb.hexToRgbConverter(currentColour);
                //System.out.println("Color, Red: " +oldColour.red+ " Green: " + oldColour.green + " newGreen: " + seekValueGreen);
                oldColour.green = seekValueGreen;
                currentColour = ColourRgb.rgbToHexConverter(oldColour);
                //System.out.println("HEX OF CURRRENT COLOUR: " + currentColour);
                TextView colorBar = (TextView) findViewById(R.id.colourBar);
                colorBar.setTextColor(Color.parseColor(currentColour));
                //System.out.println("CurrentColor Green: " + ColourRgb.hexToRgbConverter(currentColour).green + " HEX: " + currentColour);
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
                SeekBar seekBarBlue = (SeekBar) findViewById(R.id.seekBlue);
                int seekValueBlue = (int)(seekBarBlue.getProgress()*2.55);
                ColourRgb oldColour = ColourRgb.hexToRgbConverter(currentColour);
                oldColour.blue = seekValueBlue;
                currentColour = ColourRgb.rgbToHexConverter(oldColour);
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
        //System.out.println("PREF: " + showPreferences());

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

        String colorRGB = "#";
        Random random = new Random();
        for (int x = 0; x < 6; x++) {
            colorRGB = colorRGB + random.nextInt(10);
        }
    }

    private void editUserMenu(User editUser) {
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.setText(editUser.name);
    }
}