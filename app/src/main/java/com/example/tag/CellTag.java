package com.example.tag;

import android.widget.ImageView;

public class CellTag {

    private int x;
    private int y;
    private int number;
    private ImageView imageView;

    public CellTag(int x, int y,int number, ImageView imageView) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.imageView = imageView;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
