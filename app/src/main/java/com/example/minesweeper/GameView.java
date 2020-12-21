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
import android.widget.Toast;

import androidx.annotation.Nullable;


public class GameView extends View {
    Rect field;
    Mine[][] mines = new Mine[10][10];
    int mineCount = 0;
    enum ClickMode {UNCOVER, FLAG}
    ClickMode mode = ClickMode.UNCOVER;
    int flagCount = 0;
    boolean lost = false;

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

        flag = new Paint();
        flag.setStyle(Paint.Style.FILL);
        flag.setColor(Color.YELLOW);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!lost) {
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
        }

        else {
            return false;
        }

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
                if(mines[i][j].isUncovered && mode == ClickMode.UNCOVER && !mines[i][j].isBomb && !mines[i][j].isFlag){
                    canvas.drawRect(field, uncovered);
                    //couldn't get this to work without certain mines crashig the game
                    /*int bombCount = 0;
                    if(mines[i][j].getX() > 0 && mines[i][j].getX() < 10 && mines[i][j].getY() > 0 && mines[i][j].getY() < 10) {
                        if(mines[i-1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i-1][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j+1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j+1].isBomb) {
                            bombCount++;
                        }
                    }
                    else if(mines[i][j].getX() == 0 && mines[i][j].getY() != 0 && mines[i][j].getY() != 10) {
                        if(mines[i][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j+1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j+1].isBomb) {
                            bombCount++;
                        }
                    }

                    else if(mines[i][j].getY() == 0 && mines[i][j].getX() != 0 && mines[i][j].getX() != 10) {
                        if(mines[i-1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j+1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j+1].isBomb) {
                            bombCount++;
                        }
                    }

                    else if(mines[i][j].getX() == 10 && mines[i][j].getY() != 10 && mines[i][j].getY() != 0) {
                        if(mines[i-1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i-1][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j+1].isBomb) {
                            bombCount++;
                        }
                    }

                    else if(mines[i][j].getY() == 10 && mines[i][j].getX() != 10 && mines[i][j].getX() != 0) {
                        if(mines[i-1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i-1][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j].isBomb) {
                            bombCount++;
                        }
                    }

                    else if(mines[i][j].getX() == 0 && mines[i][j].getY() == 0) {
                        if(mines[i+1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i+1][j+1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j+1].isBomb) {
                            bombCount++;
                        }
                    }

                    else if(mines[i][j].getX() == 10 && mines[i][j].getY() == 10) {
                        if(mines[i-1][j].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i-1][j-1].isBomb) {
                            bombCount++;
                        }
                        else if(mines[i][j-1].isBomb) {
                            bombCount++;
                        }
                    }

                    canvas.drawText(Integer.toString(bombCount), i+50,j+100, bombText);*/
                }

                else if(mines[i][j].isUncovered && mode == ClickMode.FLAG && !mines[i][j].isBomb){
                    canvas.drawRect(field, flag);
                    mines[i][j].setFlag();
                    flagCount++;
                }

                else if(mines[i][j].isUncovered && mines[i][j].isBomb){
                    canvas.drawRect(field, bomb);
                    canvas.drawText("M", i+50,j+100, bombText);
                    lost = true;
                    Toast.makeText(getContext(), "YOU LOST!", Toast.LENGTH_SHORT).show();
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
