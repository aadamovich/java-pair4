package com.playtech;

class Rectangle {
    private int x;
    private int y;
    private int h;
    private int l;

    Rectangle(int x, int y, int h, int l) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.l = l;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getH() {
        return h;
    }

    int getL() {
        return l;
    }
}
