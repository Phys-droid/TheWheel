package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ImageButton option_button;
    private Button button_spin;
    Animation rotationAnimation;
    ImageView spinning_part;
    static final Random r = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        option_button = (ImageButton) findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });
        button_spin = (Button) findViewById(R.id.button_spin);
        button_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { rotationAnimation();
            }
        });

        spinning_part = (ImageView)findViewById(R.id.spinning_part);



    }
    private void rotationAnimation() {
        RotateAnimation a = new RotateAnimation(0, 180+r.nextInt(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ImageView animated = (ImageView) findViewById(R.id.spinning_part);
        a.setInterpolator(new LinearInterpolator());
        int x =  5 + (int)(Math.random() * 10);
        a.setRepeatCount(x);
        a.setDuration(1000);
        a.setFillAfter(true);
        animated.startAnimation(a);
    }

    public void openOptions(){
        Intent intent = new Intent(this, UserOverview.class);

        startActivity(intent);


    }
    String[] names =  {"Dima", "Päde", "Max"};
    String[] colours = {"RED", "BROWN", "BLUE"};



}