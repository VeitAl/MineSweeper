package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class GameView extends View {
    Rect field;
    Mine[][] mines = new Mine[10][10];
    int mineCount = 0;

    TextPaint bombText;
    Paint covered, uncovered, bomb, flag;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                mines[i][j] = new Mine(i,j);
            }
        }

        while(mineCount < 20) {
            mineCount++;
            mines[(int) (Math.random()*(10 - 1)) + 1][(int) (Math.random()*(10 - 1)) + 1].setBomb();
        }

        covered = new Paint();
        covered.setStyle(Paint.Style.FILL);
        covered.setColor(Color.BLACK);

        uncovered = new Paint();
        uncovered.setStyle(Paint.Style.FILL);
        uncovered.setColor(Color.GRAY);

        bomb = new Paint();
        bomb.setStyle(Paint.Style.FILL);
        bomb.setColor(Color.RED);

        bombText = new TextPaint();
        bombText.setColor(Color.BLACK);
        bombText.setTextSize(50);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int col = 0;
        int row = 0;
        float x = event.getX();
        float y = event.getY();

        for(int i = 1; i <= 10; i++) {
            if (x < (i * getWidth()/10)) {
                col = i;
                break;
            }
        }

        for (int j = 1; j <= 10; j++) {
            if (y < (j * getHeight()/10)) {
                row = j;
                break;
            }
        }

        mines[col-1][row-1].setUncovered();
        invalidate();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.WHITE);
        canvas.save();
        canvas.translate(0,0);

        int fieldWidth = (getWidth()/10);
        int fieldHeight = (getHeight()/10);
        field = new Rect(10, 10, fieldWidth, fieldHeight);

        for(int i = 0; i < 10; i++) {
            canvas.save();

            for(int j = 0; j < 10; j++){
                canvas.save();
                canvas.translate( (i * fieldWidth), (j * fieldHeight));
                if(mines[i][j].isUncovered && !mines[i][j].isBomb){
                    canvas.drawRect(field, uncovered);
                }

                else if(mines[i][j].isUncovered && mines[i][j].isBomb){
                    canvas.drawRect(field, bomb);
                    canvas.drawText("M", i+50,j+100, bombText);
                }

                else {
                    canvas.drawRect(field, covered);
                }

                canvas.restore();
            }
            canvas.restore();
        }
    }

}
