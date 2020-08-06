package com.example.thewheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class UserOverview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_overview);
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


}