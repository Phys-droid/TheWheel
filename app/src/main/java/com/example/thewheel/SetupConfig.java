package com.example.thewheel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;


public class SetupConfig extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_config);

        // Check if User exists in order to load, otherwise create new user
        if (checkForCurrentSetup() == true) {
            editSetupMenu(SetupList.currentSetup);
        } else {
            createNewSetupMenu();
        }

        final Button buttonDel = findViewById(R.id.buttonDelete);
        if (SetupList.currentSetup == null) {
            buttonDel.setTextColor(Color.parseColor("#495642"));
        }
        buttonDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SetupList.currentSetup == null) {
                    return;
                }
                if (!SetupList.removeSetupByName(SetupList.currentSetup)) {
                    Toast.makeText(SetupConfig.this, "Cannot delete the last setup!", Toast.LENGTH_SHORT).show();
                    return;
                }
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl.save(currentFocus, "Wheel_Config.txt");
                SoundManager.togg.start();

                // Check which setup should be the current one now
                SetupList.currentSetup = SetupList.getSomeSetupName();

                Intent intent = new Intent(SetupConfig.this, SetupOverview.class);
                startActivity(intent);
            }
        });

        final Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundManager.togg.start();
                if (SetupList.currentSetup == null) {
                    Intent intent = new Intent(SetupConfig.this, SetupOverview.class);
                    startActivity(intent);
                    SetupList.currentSetup = SetupList.getSomeSetupName();
                }
                else {
                    Intent intent = new Intent(SetupConfig.this, SetupOverview.class);
                    startActivity(intent);
                }
            }
        });

        final EditText editName = findViewById(R.id.nameInput);
        editName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button buttonSave =  findViewById(R.id.buttonSave);
                buttonSave.setText("SAVE");
            }
        });

        final Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nameBox = (EditText) findViewById(R.id.nameInput);

                SoundManager.togg.start();
                if (SetupList.currentSetup != null) {
                    if (!checkInput(nameBox, true)) {
                        return;
                    }
                    updateName(nameBox.getText().toString());
                } else {
                    if (!checkInput(nameBox, false)) {
                        return;
                    }
                    saveName(nameBox.getText().toString());
                }
                View currentFocus = findViewById(android.R.id.content).getRootView();
                StorageControl.save(currentFocus, "Wheel_Config.txt");
                // Move saving
                Intent intent = new Intent(SetupConfig.this, UserOverview.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkInput(EditText nameBox, boolean isNotNull) {
        if (nameBox.getText().toString().equals("")){
            Toast.makeText(SetupConfig.this, "Enter a name for your Setup!", Toast.LENGTH_SHORT).show();
            return false;
        }
        SetupList setuplist;
        for (int i = 0; i < SetupList.setupList.size(); i++){
            //System.out.println("CURRENT USERLIST ID: " + SetupList.setupList.get(i).userListId);
            //System.out.println("CURRENT USERLIST ID FIRST PART: " +  SetupList.setupList.get(i).userListId);
            if (!isNotNull) {
                if ( SetupList.setupList.get(i).userListId.equals(nameBox.getText().toString())) {
                    Toast.makeText(SetupConfig.this, "This Setup already exists!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else if(SetupList.currentSetup.equals(nameBox.getText().toString())){
                //do nothing
            }
            else if (nameBox.getText().toString().equals(SetupList.setupList.get(i).userListId)) {
                Toast.makeText(SetupConfig.this, "This Setup already exists!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    private void saveName(String username) {
        UserList newUserList = new UserList(username);
        SetupList.addSetup(newUserList);
        SetupList.currentSetup = username;
    }

    private void updateName(String username) {
        SetupList.getSetupListById(SetupList.currentSetup).userListId = username;
        SetupList.currentSetup = username;
    }

     private boolean checkForCurrentSetup() {
        boolean CurrentSetupExists = false;
        if (SetupList.currentSetup != null) {
            CurrentSetupExists = true;
        }
        return CurrentSetupExists;
    }

    private void createNewSetupMenu() {
        //Auto fill in some color
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.setText("Setup " + (SetupList.setupList.size()+1));
        Button savebutton = (Button) findViewById(R.id.buttonSave);
        savebutton.setText("SAVE");
    }

    private void editSetupMenu(String editSetup) {
        EditText nameBox = (EditText) findViewById(R.id.nameInput);
        nameBox.setText(editSetup);
    }
}