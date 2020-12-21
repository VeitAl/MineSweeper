package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView flagsCount, minesCount;
    Button reset, mode;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagsCount = findViewById(R.id.minesMarkedTV);
        minesCount = findViewById(R.id.totalMinesTV);

        reset = findViewById(R.id.resetBtn);
        mode = findViewById(R.id.modeBtn);

        gameView = findViewById(R.id.minesweeper);

        flagsCount.setText("Mines marked: " + gameView.flagCount);
        minesCount.setText("Total mines: " + gameView.mineCount);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.mineCount = 0;
                gameView.lost = false;
                gameView.init();
                gameView.invalidate();
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameView.mode == GameView.ClickMode.FLAG) {
                    gameView.mode = GameView.ClickMode.UNCOVER;
                }
                else {
                    gameView.mode = GameView.ClickMode.FLAG;
                }
            }
        });
    }

}