package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity<start> extends AppCompatActivity {

    //define variables
    private ImageView x;    //Throwaway_variable
    private ImageButton option_button;
    static int number_of_players;
    private float angle;
    private float start = 0;
    private Button button_spin;
    static float values[];
    private int[] colors;
    private String[] names;
    Boolean isTrue = Boolean.TRUE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiate variables
        angle = 0;


        UserList userList = SetupList.getCurrentUserList();
        if(userList == null) {
            number_of_players = 3;
            colors = new int[]{Color.RED, Color.GREEN, Color.BLUE};
            values = new float[number_of_players];
            names = new String[]{"Dima", "Päde", "Max"};
        }
        else{

            number_of_players = userList.userArray.size();
            values = new float[number_of_players];
            colors = new int[number_of_players];
            names = new String[number_of_players];

            for (int i = 0; i<number_of_players; i++){
                names[i] = userList.userArray.get(i).name;
                colors[i] = Color.parseColor(userList.userArray.get(i).colour);
            }
        }


        for (int x = 0; x < number_of_players; x++) {
            values[x] = 100/number_of_players;
        }

        final LinearLayout wheel=(LinearLayout) findViewById(R.id.wheel);
        values=calculateData(values);
        wheel.addView(new MyGraphview(this,values, colors));


        //RNG
        Random r = new Random(System.currentTimeMillis());
        int range = 1000;
        float a = r.nextInt(range);
        int b = r.nextInt(range);
        int c = r.nextInt(range);




        //buttons
        option_button = findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });
        button_spin = findViewById(R.id.button_spin);
        final float finalA = a;
        final int finalB = b;
        final int finalC = c;
        // *Initialize Sound Manager
        SoundManager.initialize(this);
        button_spin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start = animate_wheel(finalA,finalB,finalC, start, wheel);
                // Play sound
                SoundManager.michael.start();
            }
        });
    }





    //classes________________________________________________________________________________________________________________________________________________________________________________________
    public class MyGraphview extends View
    {
        private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        private int[] COLORS;
        RectF rectf;

        int temp=0;
        public MyGraphview(Context context, float[] values, int[] colors) {
            super(context);
            this.COLORS = colors;
            this.rectf = new RectF(0, 0,
                    1080 , 1080);


        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint2 = new Paint();
            canvas.drawPaint(paint2);
            paint2.setColor(Color.BLACK);
            paint2.setTextSize(62);


            for (int i = 0; i < values.length; i++) {//values2.length; i++) {
                if (i == 0) {
                    paint.setColor(COLORS[i]);
                    if (COLORS[i] == Color.BLACK){
                        paint2.setColor(Color.WHITE);
                    }
                    canvas.drawArc(rectf, angle, values[i], true, paint);
                    float alpha = (float) ((angle+(values[i]/2)));
                    canvas.rotate(alpha+2, 540, 540);
                    canvas.drawText(names[i],740, 540, paint2);
                    canvas.rotate(-alpha-2, 540, 540);


                }
                else
                {
                    temp += (int) values[i - 1];
                    paint.setColor(COLORS[i]);
                    if (COLORS[i] == Color.BLACK){
                        paint2.setColor(Color.WHITE);
                    }
                    canvas.drawArc(rectf, temp + angle, values[i], true, paint);
                    float alpha = (float) ((temp+angle+(values[i]/2)));
                    canvas.rotate(alpha+2, 540, 540);
                    canvas.drawText(names[i],740, 540, paint2);
                    canvas.rotate(-alpha-2, 540, 540);
                }
            }
        }

    }






    //functions__________________________________________________________________________________________________________________________________________________________________________________________
    private float rotationAnimation(LinearLayout animated, float start, float x, int y, int z) {
        x = x*y*z;

        while (x > 360){
            x = x-100;
        }


        AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());


        int t = 9;
        float tolerance = (float) 0.000005;
        float delta =  x;
        float x_ = 0;
        int counter = 0;
        int counter2 = 0;
        int exp_counter = 0;
        float x_2 = -1;
        while ((delta)>tolerance){
            RotateAnimation anim = new RotateAnimation(start, start + delta , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(t);
            anim.setStartOffset(t*counter);
            counter += 1;
            as.addAnimation(anim);
            start = start+delta;
            //log(strong damping)
            if (delta/x > 0.15) {
                delta = (float) (x*Math.pow(0.75, counter));
                counter2 = counter;
                x_ = delta;

            }
            //log(weak damping)
            else if (delta/x > 0.09) {
                delta = (float) (x_*Math.pow(0.99, counter2)*Math.pow(0.992, counter-counter2));
                System.out.println(counter);
            }
            //linear(strong damping)
            else if (delta/x > 0.06){
                delta -= x/8000;

            }
            //linear(weak damping)
            else if(delta/x > 0.04){
                delta -= x/10000;
                x_2 = delta;
            }
            //-exp
            else{
                //delta -= delta*Math.exp(exp_counter/30)/Math.exp(25);
                delta = (float) (x_2*Math.pow(0.995, exp_counter));
                exp_counter += 1;
            }
            anim = null;
        }

        animated.startAnimation(as);


        return start;
    }

    private float animate_wheel(float a, int b, int c, float start, LinearLayout wheel){
        return start = rotationAnimation(wheel, start, a, b, c);
    }


    public void openOptions() {
        Intent intent = new Intent(this, UserOverview.class);
        startActivity(intent);
    }
    private float[] calculateData(float[] data) {
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        return data;

    }

}


