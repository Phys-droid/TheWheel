package com.example.thewheel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;


public class UserConfig extends AppCompatActivity {

    public static User currentUser = null;
    public static String currentColour = "#000000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);

        showNewColour();

        // Check if User exists in order to load, otherwise create new user
        if (checkForCurrentUser() == true) {
            editUserMenu(currentUser);
        } else {
            createNewUserMenu();
        }

        final Button buttonDel = findViewById(R.id.buttonDelete);
        if (currentUser == null) {
            buttonDel.setBackgroundColor(Color.parseColor("#EFEFEF"));
            buttonDel.setTextColor(Color.parseColor("#C7C7C7"));
        }
        buttonDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentUser == null) {
                    return;
                }
                UserList currentUserList = SetupList.getUserList(SetupList.currentSetup);
                currentUserList.deleteUserById(currentUser.id);
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl.save(currentFocus, "Wheel_Config.txt");
                SoundManager.togg.start();
                currentUser = null;
                Intent intent = new Intent(UserConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });

        final Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentUser = null;
                SoundManager.togg.start();
                Intent intent = new Intent(UserConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });

        final Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nameBox = (EditText) findViewById(R.id.nameInput);
                SoundManager.togg.start();
                if (currentUser != null) {
                    updateNameAndColour(nameBox.getText().toString(), currentColour);
                } else {
                    saveNameAndColour(nameBox.getText().toString(), currentColour);
                }
                // ToDo: Move saving
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl.save(currentFocus, "Wheel_Config.txt");
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
                showNewColour();
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
                oldColour.green = seekValueGreen;
                currentColour = ColourRgb.rgbToHexConverter(oldColour);
                showNewColour();
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
                showNewColour();
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
        UserList currentUserList = SetupList.getUserList(SetupList.currentSetup);
        //System.out.println("---CURRENTLIST:--- " + currentUserList.userListId);
        currentUserList.addUser(username, colour);
        //System.out.println("BEFORE: " + currentUserList.getUser("1").name);
        //Remove if done
        //System.out.println("---USER:---");
        //System.out.println("PREF: " + showPreferences());

    }

    private void updateNameAndColour(String username, String colour) {
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
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.setText("");
        currentColour = ColourRgb.rgbToHexConverter(new ColourRgb(0,0,0));
    }

    private void editUserMenu(User editUser) {
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.setText(editUser.name);
        currentColour = editUser.colour;
        ColourRgb currentRgbColour = ColourRgb.hexToRgbConverter(editUser.colour);
        SeekBar seekBarRed = (SeekBar) findViewById(R.id.seekRed);
        seekBarRed.setProgress((int)(currentRgbColour.red/2.55));
        SeekBar seekBarGreen = (SeekBar) findViewById(R.id.seekGreen);
        seekBarGreen.setProgress((int)(currentRgbColour.green/2.55));
        SeekBar seekBarBlue = (SeekBar) findViewById(R.id.seekBlue);
        seekBarBlue.setProgress((int)(currentRgbColour.blue/2.55));
    }

    private void showNewColour() {
        final LinearLayout linear=(LinearLayout) findViewById(R.id.colour_circle);
        if (linear.getChildAt(0) == null) {
            linear.addView(new CircleView(this));
        }
        else {
            linear.getChildAt(0).invalidate();
            linear.addView(new CircleView(this));
        }
    }

    public static String getCurrentColourHex() {
        return currentColour;
    }

}