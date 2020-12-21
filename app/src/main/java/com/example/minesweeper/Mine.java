package com.example.minesweeper;

public class Mine {
    int x;
    int y;
    boolean isUncovered = false;
    boolean isBomb = false;
    boolean isFlag = false;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setUncovered() {
        isUncovered = true;
    }

    public void setBomb() {
        isBomb = true;
    }

    public void setFlag() {
        if(isFlag) {
            isFlag = false;
        }
        isFlag = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
