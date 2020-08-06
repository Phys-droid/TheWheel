package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    private ImageButton options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = (ImageButton) findViewById(R.id.button_options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });

    }

    public void openOptions(){
        Intent intent = new Intent(this, MainActivity.class); /* TODO: change to Pädes activity*/
        startActivity(intent);

    }
}