package com.example.thewheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SetupOverview extends AppCompatActivity {
    public static boolean startUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_overview);

        //Create Setup

        fillSetupView();
        //Create Save button
        final Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundManager.togg.start();
                Intent intent = new Intent(SetupOverview.this, UserOverview.class);
                startActivity(intent);
            }
        });

    }

    void fillSetupView() {
        //Create Array Adapter to fill in userView
        ListView setupView = (ListView) findViewById(R.id.setupView);
        ListView userView = (ListView) findViewById(R.id.userView);

        final ArrayList<String> setupNameList = SetupList.getAllSetupIds();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                setupNameList);
        setupView.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        setupView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                String selectedSetup = setupNameList.get(position);
                //Toast.makeText(getApplicationContext(), "User Selected : " + selectedUser,   Toast.LENGTH_LONG).show();
                // Change to Edit User
                Intent intent = new Intent(SetupOverview.this, UserOverview.class);
                //System.out.println("A USER HAS BEEN CLICKED");
                SetupList.currentSetup = selectedSetup;
                //System.out.println("SEARCH USERS: " + UserConfig.currentUser);
                startActivity(intent);
            }
        });
    }
}