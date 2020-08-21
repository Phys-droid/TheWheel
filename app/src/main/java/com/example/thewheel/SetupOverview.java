package com.example.thewheel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

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
        final Button buttonDel = findViewById(R.id.buttonDeleteSetup);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ToDo: Offer option to delete certain setup
                SetupList.removeLastSetup();
                if (SetupList.setupList.size() == Integer.parseInt(SetupList.currentSetup) - 1) {
                    SetupList.currentSetup = Integer.toString(Integer.parseInt(SetupList.currentSetup) - 1);
                }
                SoundManager.togg.start();
                recreate();
            }
        });

    }

    void fillSetupView() {
        //Create Array Adapter to fill in userView
        ListView setupView = (ListView) findViewById(R.id.setupView);

        final ArrayList<String> setupNameList = SetupList.getAllSetupIds();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                setupNameList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#CCD7D8"));
                return view;
            }
        };
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