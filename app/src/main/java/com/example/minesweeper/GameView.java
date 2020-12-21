package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    Rect field;

    Paint covered, uncovered, flag;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.WHITE);
        canvas.save();
        canvas.translate(0,0);

        int fieldLength = (getWidth()/10);

        covered = new Paint();
        covered.setStyle(Paint.Style.FILL);
        covered.setColor(Color.BLACK);

        field = new Rect(5, 5, fieldLength, fieldLength);


        for(int i = 0; i < 10; i++) {
            canvas.save();

            for(int j = 0; j < 10; j++){
                canvas.save();
                canvas.translate( (i * fieldLength), (j * fieldLength));
                canvas.drawRect(field, covered);
                canvas.restore();
            }
            canvas.restore();
        }
    }
}
