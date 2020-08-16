package com.example.thewheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import static com.example.thewheel.R.drawable.singleplayer;

public class MainActivity extends AppCompatActivity {

    //define variables
    private ImageView x;    //Throwaway_variable
    private ImageButton option_button;
    private int[] colors;
    static int number_of_players;
    private float angle;
    private Button button_spin;
    static final Random r = new Random(System.currentTimeMillis());
    static float values[];
    Boolean isTrue = Boolean.TRUE;


    private void sectionPie(int playerAmount) {
        for (int x = 0; x < playerAmount; x++) {
            values[x] = 100/playerAmount;
            System.out.println("VALUES: " + values);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiate variables
        angle = 0;
        number_of_players = 4;
        colors= new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};
        values= new float[number_of_players];
        int color = Color.parseColor("#AE6118");

        sectionPie(number_of_players);

        final LinearLayout linear=(LinearLayout) findViewById(R.id.wheel);
        values=calculateData(values);
        linear.addView(new MyGraphview(this,values, colors));
        rotationAnimation(linear);





        //connections to functions
        option_button = findViewById(R.id.button_options);
        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openOptions();
            }
        });
        button_spin = findViewById(R.id.button_spin);
        button_spin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rotationAnimation(linear);
            }
        });







        //Mainloop
        /*
        while(isTrue == Boolean.TRUE){
            recreate();
            isTrue = Boolean.FALSE;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        */




    }





    //________________________________________________________________________________________________________________________________________________________________________________________
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
                    1090 , 1090);


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



















//__________________________________________________________________________________________________________________________________________________________________________________________
    private void rotationAnimation(LinearLayout animated) {
        RotateAnimation a = new RotateAnimation(0, (180+r.nextInt())/10000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        a.setInterpolator(new LinearInterpolator());
        a.setDuration(3000);
        a.setFillAfter(true);
        animated.startAnimation(a);
    }


    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    public void openOptions() {
        Intent intent = new Intent(this, UserOverview.class);
        startActivity(intent);
    }

    public void construct_wheel(int number_of_players, ArrayList<ImageView> Graphics){  //TODO

    }

     public void animate_wheel(ArrayList<ImageView> Graphics){                          //TODO

    }
    public ImageView merge_Drawables(ArrayList<Drawable> drawables){

        Drawable a = drawables.get(0);
        int l = drawables.size();
        ImageView r = new ImageView(this);
        int c = drawables.size();

        for(int i = 1; i<c; i++){
            Drawable b = drawables.get(i);
            int horizontalInset = (a.getIntrinsicWidth() - b.getIntrinsicWidth()) / 2;
            LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {a, b});
            finalDrawable.setLayerInset(0, 0, 0, 0, a.getIntrinsicHeight());
            finalDrawable.setLayerInset(1, horizontalInset, b.getIntrinsicHeight(), horizontalInset, 0);
            r.setImageDrawable(finalDrawable);

        }
        return r;


    }


    public void wheel(UserList Setup, ArrayList<ImageView> circle_fragments) {                 //TODO
        ArrayList<User> UserArray = Setup.userArray;
        ArrayList<Bitmap> picture = new ArrayList<>();
        ArrayList<ImageView> graphics = new ArrayList<>();
        int number_of_players = UserArray.size();





        for (int i=0;i<number_of_players;i++){     //Change Type String to Color in User class

            /*
            Color a = UserArray.get(i).colour;
            imageViewIcon.setColorFilter(ContextCompat.getColor(context, a));
            Color c = new Color(Integer.parseInt(a));
            */



        }
        construct_wheel(number_of_players, graphics);
        animate_wheel(graphics);

    }
}





