package com.example.thewheel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PieDemo extends AppCompatActivity {
    /** Called when the activity is first created. */
    static int playerAmount = 3;
    static float values[] = new float[playerAmount];

    public static float angle = 0;

    private void sectionPie(int playerAmount) {
        for (int x = 0; x < playerAmount; x++) {
            values[x] = 100/playerAmount;
            System.out.println("VALUES: " + values);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_view);

        sectionPie(playerAmount);

        final LinearLayout linear=(LinearLayout) findViewById(R.id.colour_circle);
        values=calculateData(values);
        linear.addView(new MyGraphview(this,values));


        final Button buttonToDemo = findViewById(R.id.buttonTurn);
        buttonToDemo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recreate();
            }
        });

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
    public static class MyGraphview extends View
    {
        private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        private int[] COLORS={Color.RED,Color.GREEN, Color.BLUE};
        RectF rectf = new RectF(0, 0, 1000, 1000);
        int temp=0;
        public MyGraphview(Context context, float[] values) {
            super(context);
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
            angle += 10;
        }

    }
}