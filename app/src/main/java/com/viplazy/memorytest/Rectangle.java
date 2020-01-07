package com.viplazy.memorytest;

public class Rectangle {

    int posX, posY, width, height;

    public Rectangle(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
        this.posX = 0;
        this.posY = 0;
        this.width = 120;
        this.height = 120;
    }

    public Rectangle(int width, int height) {
        this.posX = 0;
        this.posY = 0;
        this.width = width;
        this.height = height;
    }
}
