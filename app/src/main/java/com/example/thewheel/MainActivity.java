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
    private Button button_spin;
    static final Random r = new Random(System.currentTimeMillis());
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
        int a = r.nextInt();
        int b = r.nextInt();
        int c = r.nextInt();

        while (a<b || a<c || b<c){
            int d = a;
            a = b;
            b = d;
            d = c;
            b = c;
            c = d;
        }



        //buttons
        option_button = findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });
        button_spin = findViewById(R.id.button_spin);
        final int finalA = a;
        final int finalB = b;
        final int finalC = c;
        button_spin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float start2 = rotationAnimation(wheel, 0, finalA);
                float start3 = rotationAnimation(wheel, start2, finalB);
                float end = rotationAnimation(wheel, start3, finalC);
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

            for (int i = 0; i < values.length; i++) {//values2.length; i++) {
                if (i == 0) {
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, angle, values[i], true, paint);
                }
                else
                {
                    temp += (int) values[i - 1];
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, temp + angle, values[i], true, paint);
                }
            }
        }

    }






//functions__________________________________________________________________________________________________________________________________________________________________________________________
    private float rotationAnimation(LinearLayout animated, float start, int z) {
        RotateAnimation a = new RotateAnimation(start, (180 + z) / 100000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        a.setInterpolator(new LinearInterpolator());
        a.setDuration(3000);
        a.setFillAfter(true);
        animated.startAnimation(a);

        return start+(180+z)/10000;
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





