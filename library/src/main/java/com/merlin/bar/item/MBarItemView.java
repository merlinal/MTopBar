package com.merlin.bar.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merlin.view.Util;
import com.merlin.bar.R;

/**
 * Created by ncm on 16/12/2.
 */

public class MBarItemView extends LinearLayout {

    public MBarItemView(Context context) {
        this(context, null);
    }

    public MBarItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MBarItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = Util.findById(this, R.id.bar_item_text);
        mImageView = Util.findById(this, R.id.bar_item_img);
    }

    public void setData(BarItem bi) {
        setId(bi.getId());
        setImg(bi.getImgId());
        setText(bi.getStrId());
        setTextColor(bi.getTextColor());
        setTextSize(bi.getTextSize());
        setOnClickListener(bi.getOnClickListener());
    }

    private MBarItemView setImg(int resId) {
        if (resId != 0) {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageResource(resId);
        } else {
            mImageView.setVisibility(GONE);
        }
        return this;
    }

    private MBarItemView setText(int resId) {
        if (resId != 0) {
            mTextView.setText(resId);
        } else {
            mTextView.setVisibility(GONE);
        }
        return this;
    }

    public MBarItemView setTextColor(int textColor) {
        if(textColor != 0){
            mTextView.setTextColor(textColor);
        }
        return this;
    }

    public MBarItemView setTextSize(int textSize) {
        if(textSize > 0){
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
        return this;
    }

}
