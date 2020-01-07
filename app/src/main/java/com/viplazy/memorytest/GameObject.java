package com.viplazy.memorytest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameObject {

    private Context context;

    private ImageView img;

    private RelativeLayout.LayoutParams params;

    private Rectangle rect;

    private RelativeLayout container;

    private String labels;

    public GameObject(Context context, RelativeLayout container, Drawable imgDrawable, String labels) {
        this.context = context;
        this.container = container;
        this.labels = labels;

        img = new ImageView(context);

        img.setImageDrawable(imgDrawable);
        img.setAdjustViewBounds(true);

        rect = new Rectangle(140, 140);

        params = new RelativeLayout.LayoutParams(rect.width, rect.height);
        params.leftMargin = rect.posX;
        params.topMargin = rect.posY;
    }

    public RelativeLayout getContainer() {
        return container;
    }

    public boolean isVaildPos(Rectangle rect) {
        boolean res = true;

        if (rect.posX + rect.width >= this.rect.posX && rect.posX <= this.rect.posX + this.rect.width)
            if (rect.posY + rect.height >= this.rect.posY && rect.posY <= this.rect.posY + this.rect.height)
                res = false;

        return res;
    }

    public void addView() {
        container.addView(img, params);

        img.setVisibility(ImageView.VISIBLE);
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public ImageView getImage() {
        return img;
    }

    public void setVisible(int visible) {
        img.setVisibility(visible);
    }

    public int getWidth() {
        return rect.width;
    }

    public void setWidth(int width) {
        this.rect.width = width;

        params.width = width;
    }

    public int getHeight() {
        return rect.height;
    }

    public void setHeight(int height) {
        this.rect.height = height;

        params.height = height;
    }

    public int getPosX() {
        return rect.posX;
    }

    public void setPosX(int posX) {
        this.rect.posX = posX;

        params.leftMargin = posX;
    }

    public int getPosY() {
        return rect.posY;
    }

    public void setPosY(int posY) {
        this.rect.posY = posY;

        params.topMargin = posY;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
        params.width = rect.width;
        params.height = rect.height;
        params.leftMargin = rect.posX;
        params.topMargin = rect.posY;
    }
}
