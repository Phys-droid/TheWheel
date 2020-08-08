package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import static com.example.thewheel.R.drawable.singleplayer;

public class MainActivity extends AppCompatActivity {


    private ImageView x;
    private ImageButton option_button;
    private Button button_spin;
    ImageView spinning_part;
    static final Random r = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        option_button = findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });
        button_spin = findViewById(R.id.button_spin);
        button_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { rotationAnimation();
            }
        });
        spinning_part = findViewById(R.id.spinning_part);
    }


    private void rotationAnimation() {
        RotateAnimation a = new RotateAnimation(0, (180+r.nextInt())/1000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ImageView animated = findViewById(R.id.spinning_part);
        a.setInterpolator(new LinearInterpolator());
        int x =  5 + (int)(Math.random() * 10);
        a.setRepeatCount(x);
        a.setDuration(1000);
        a.setFillAfter(true);
        animated.startAnimation(a);
    }

    public void openOptions() {
        Intent intent = new Intent(this, UserOverview.class);
        startActivity(intent);
    }

    public void construct_wheel(int number_of_players, ArrayList<ImageView> Graphics){  //TODO

    }

     public void animate_wheel(ArrayList<ImageView> Graphics){                          //TODO

    }
/*
    public ImageView merge_drawables(ArrayList<ImageView> drawables){                   //TODO

        ImageView a = (ImageButton) findViewById(R.id.button);

        Drawable plusIcon = ContextCompat.getDrawable(this, R.drawable.plus);
        Drawable dotIcon = ContextCompat.getDrawable(this, R.drawable.oval);

        int horizontalInset = (plusIcon.getIntrinsicWidth() - dotIcon.getIntrinsicWidth()) / 2;

        LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {plusIcon, dotIcon});
        finalDrawable.setLayerInset(0, 0, 0, 0, dotIcon.getIntrinsicHeight());
        finalDrawable.setLayerInset(1, horizontalInset, plusIcon.getIntrinsicHeight(), horizontalInset, 0);
        a.setImageDrawable(finalDrawable);
        return a;


    }
*/


    public void wheel(UserList Setup) {
        ArrayList<User> UserArray = Setup.userArray;
        ArrayList<Bitmap> picture = new ArrayList<>();
        ArrayList<ImageView> graphics = new ArrayList<>();
        int number_of_players = UserArray.size();


        for (int i=0;i<number_of_players;i++){     //Change Type String to Color in User class
            /*
            String a = UserArray.get(i).colour;
            Color c = new Color(Integer.parseInt(a));
            */

            x = (ImageView) findViewById(R.id.test);
            //x.setColorFilter(c);
            x.setX(0);
            x.setY(0);



            graphics.add(x);
        }
        construct_wheel(number_of_players, graphics);
        animate_wheel(graphics);

    }
}





