package com.example.thewheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class CircleView extends View {
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    public CircleView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor(UserConfig.getCurrentColourHex()));
        canvas.drawCircle(150,150, 150, paint);
    }

}
