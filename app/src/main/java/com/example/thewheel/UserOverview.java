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

public class UserOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_overview);

        //Write correct setup
        final TextView setupnumber = findViewById(R.id.setupNumber);
        setupnumber.setText("Current Setup: " + SetupList.currentSetup);
        fillUserView();

        final Button buttonWheel = findViewById(R.id.buttonWheel);
        buttonWheel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundManager.togg.start();
                Intent intent = new Intent(UserOverview.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final Button buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundManager.togg.start();

                Intent intent = new Intent(UserOverview.this, UserConfig.class);
                startActivity(intent);
            }
        });
        final Button buttonLoad = findViewById(R.id.buttonLoad);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundManager.togg.start();
                Intent intent = new Intent(UserOverview.this, SetupOverview.class);
                startActivity(intent);
            }
        });
        }

    void fillUserView() {
        //Create Array Adapter to fill in userView
        ListView userView = (ListView) findViewById(R.id.userView);
        //System.out.println("CURRR SETUP: " + SetupList.currentSetup);
        final ArrayList<String> nameList = SetupList.getUserList(SetupList.currentSetup).getAllNames();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,
                nameList) {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setTextColor(Color.parseColor("#CCD7D8"));
            return view;
        }
    };;


        userView.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        userView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String selectedUser = nameList.get(position);
                //Toast.makeText(getApplicationContext(), "User Selected : " + selectedUser,   Toast.LENGTH_LONG).show();
                // Change to Edit User
                Intent intent = new Intent(UserOverview.this, UserConfig.class);
                //System.out.println("A USER HAS BEEN CLICKED");
                UserConfig.currentUser =  SetupList.getCurrentUserList().getUserByName(selectedUser);
                //System.out.println("SEARCH USERS: " + UserConfig.currentUser);
                startActivity(intent);
            }
        });
    }
}