package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
import android.widget.Toast;

import java.util.Random;

public class MainActivity<start> extends AppCompatActivity {

    //define variables
    private ImageView x;    //Throwaway_variable
    private ImageButton option_button;
    static int number_of_players;
    private float angle;
    private double time;
    private float start = 0;
    private ImageButton button_spin;
    private ImageButton sound_button;
    static float values[];
    private int[] colors;
    private String[] names;
    Boolean isTrue = Boolean.TRUE;
    Boolean sound = Boolean.TRUE;

    public static boolean startUp = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        //Check if application runs first time ever, if so, create first Setup
        if (!StorageControl.configAlreadyExists()) {
            SetupList.addSetup(new UserList("Setup 1"));
            startUp = false;
        }
        //Else check if App just started
        else if (startUp) {
            //Load data into temp Storage
            StorageControl.load(findViewById(android.R.id.content).getRootView());
            startUp = false;
        }
        SoundManager.initialize(this);

        //Initiate variables
        angle = 0;
        if (sound == true){
            SoundManager.michael.setVolume(1, 1);
        }
        else{
            SoundManager.michael.setVolume(0, 0);
        }


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
        MyGraphview gv = new MyGraphview(this,values, colors);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width, Gravity.CENTER);
        wheel.addView(gv, params);





        //buttons
        option_button = findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();

            }
        });
        button_spin = findViewById(R.id.button_spin);
        sound_button = findViewById(R.id.soundbutton);
        sound_button.setImageResource(R.drawable.sound_on);
        sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound == Boolean.TRUE){
                    sound = Boolean.FALSE;
                    sound_button.setImageResource(R.drawable.sound_off);
                    SoundManager.michael.setVolume(0, 0);
                }
                else{
                    sound = Boolean.TRUE;
                    sound_button.setImageResource(R.drawable.sound_on);
                    if (SoundManager.michael.isPlaying()){
                        SoundManager.michael.start();
                    }
                    SoundManager.michael.setVolume(1, 1);
                }
            }
        });
        // *Initialize Sound Manager
        button_spin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //rng
                Random r = new Random(System.currentTimeMillis());
                int range = 1000;
                float a = r.nextInt(range);
                int b = r.nextInt(range);
                int c = r.nextInt(range);
                final float finalA = a;
                final int finalB = b;
                final int finalC = c;

                time = rotationAnimation(wheel, start, a, b, c, sound);





            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(MainActivity.this, "Permission denied, the App won't be able to save configurations.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int size = width;
            this.rectf = new RectF(0, 0,
                    size , size);


        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint2 = new Paint();
            canvas.drawPaint(paint2);
            paint2.setColor(Color.BLACK);
            paint2.setTextSize(58);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int size = width;
            float alpha;


            for (int i = 0; i < values.length; i++) {//values2.length; i++) {
                    paint.setColor(COLORS[i]);
                    UserList userList = SetupList.getCurrentUserList();
                    if (userList != null){
                        if (ColourRgb.isDarkColour(ColourRgb.hexToRgbConverter(userList.userArray.get(i).colour))){
                            paint2.setColor(Color.WHITE);
                    }
                        else{
                            paint2.setColor(Color.BLACK);
                        }

                    }
                    if (i == 0){
                        canvas.drawArc(rectf, angle, values[i], true, paint);
                        alpha = (float) ((angle+(values[i]/2)));
                    }
                    else {
                        canvas.drawArc(rectf, temp + angle, values[i], true, paint);
                        alpha = (float) ((temp+angle+(values[i]/2)));
                    }


                    canvas.rotate(alpha+2, size/2, size/2);
                    canvas.drawText(names[i],size/2+200, size/2, paint2);
                    canvas.rotate(-alpha-2, size/2, size/2);

                    temp += (int) values[i];




            }
        }

    }






    //functions__________________________________________________________________________________________________________________________________________________________________________________________
    private double rotationAnimation(LinearLayout animated, float start, float x, int y, int z, Boolean music) {
        x = x*y*z;

        while (x > 360){
            x = x-100;
        }


        AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());


        int t = 9;
        float tolerance = (float) 0.005;
        float delta =  x;
        float x_ = 0;
        int counter = 0;
        int counter2 = 0;
        int exp_counter = 0;
        int height = animated.getHeight() / 2;
        int width = animated.getWidth() / 2;
        double t_tot = 0;

        float x_2 = -1;
        while ((delta)>tolerance){
            RotateAnimation anim = new RotateAnimation(start, start + delta , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(t);
            t_tot += t;
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

        // Play sound

        MediaPlayer a = SoundManager.michael;

        if (!SoundManager.michael.isPlaying()){
            SoundManager.initialize(this);
        }


        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                SoundManager.michael.seekTo(0);
                SoundManager.michael.start();
                if (sound){
                    SoundManager.michael.setVolume(1, 1);
                }
                else{
                    SoundManager.michael.setVolume(0, 0);
                }

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SoundManager.michael.stop();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        System.out.println(t_tot);
        return t_tot;
    }




    public void openOptions() {
        SoundManager.michael.stop();
        SoundManager.initialize(this);
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

    private void swapstate(Boolean a){
        if (a == Boolean.TRUE){
            a = Boolean.FALSE;
        }
        else{
            a = Boolean.TRUE;
        }
    }

}


