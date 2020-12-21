package com.example.minesweeper;

public class Mine {
    int x;
    int y;
    boolean isUncovered = false;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setUncovered() {
        isUncovered = true;
    }
}
