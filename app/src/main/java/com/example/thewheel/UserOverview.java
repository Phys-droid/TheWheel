package com.example.thewheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserOverview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_overview);

        //Create Setup
        //ToDo: Think where to initialize the setup list!
        SetupList.addSetup("1");

        fillUserView();

        final Button buttonWheel = findViewById(R.id.buttonWheel);
        buttonWheel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserOverview.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final Button buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserOverview.this, UserConfig.class);
                startActivity(intent);
            }
        });
        }

    void fillUserView() {
        //Create Array Adapter to fill in userView
        ListView userView = (ListView) findViewById(R.id.userView);
        final ArrayList<String> nameList = SetupList.getUserList("1").getAllNames();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,
                nameList);

        userView.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        userView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String selectedUser =nameList.get(position);
                Toast.makeText(getApplicationContext(), "User Selected : "+selectedUser,   Toast.LENGTH_LONG).show();
            }
        });
    }

}