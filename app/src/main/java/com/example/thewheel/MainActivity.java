package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin = (Button) findViewById(R.id.button_options);
        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });
    }

    public void openOptions

    {
        Intent intent = new Intent(this, MainActivity.class); /* TODO: change to Pädes activity*/
        startActivity(intent);

    }
}