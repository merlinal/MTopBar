package com.merlin.bar.item;

import android.view.View;

/**
 * Created by ncm on 16/12/2.
 */

public class BarItem {

    private int id;
    private int strId;
    private int imgId;
    private int textColor;
    private int textSize;
    private View.OnClickListener onClickListener;

    public BarItem(int id, int strId, int imgId, View.OnClickListener onClickListener) {
        this(id, strId, imgId, 0, 0, onClickListener);
    }

    public BarItem(int id, int strId, int imgId, int textColor, int textSize, View.OnClickListener onClickListener) {
        this.id = id;
        this.strId = strId;
        this.imgId = imgId;
        this.textColor = textColor;
        this.textSize = textSize;
        this.onClickListener = onClickListener;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrId() {
        return strId;
    }

    public void setStrId(int strId) {
        this.strId = strId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
