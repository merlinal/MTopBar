package com.merlin.bar.more;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.merlin.view.Util;

/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */
public class RecyclerListDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private int mDividerWidth;
    private final int mOrientation;
    private int mHeaderCount;
    private int mFooterCount;

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public RecyclerListDecoration(Context context) {
        this(context, LinearLayoutManager.VERTICAL, Util.dp2px(context, 1), 0, 0);
    }

    public RecyclerListDecoration(Context context, int orientation) {
        this(context, orientation, Util.dp2px(context, 1), 0, 0);
    }

    public RecyclerListDecoration(Context context, int orientation, int dividerWidth) {
        this(context, orientation, dividerWidth, 0, 0);
    }

    public RecyclerListDecoration(Context context, int orientation, int dividerWidth, int headerCount, int footerCount) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        this.mOrientation = orientation;
        this.mDividerWidth = dividerWidth;
        this.mHeaderCount = headerCount;
        this.mFooterCount = footerCount;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 0);
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            if (isHideDivider(parent, parent.getChildLayoutPosition(view), parent.getAdapter().getItemCount())) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDividerWidth, 0);
            }
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            if (isHideDivider(parent, parent.getChildLayoutPosition(view), parent.getAdapter().getItemCount())) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDividerWidth);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isHideDivider(parent, parent.getChildLayoutPosition(child), parent.getAdapter().getItemCount())) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + mDividerWidth;
            final int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isHideDivider(parent, parent.getChildLayoutPosition(child), parent.getAdapter().getItemCount())) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
            final int right = left + mDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isHideDivider(RecyclerView parent, int pos, int childCount) {
        if (pos >= mHeaderCount - 1 && pos < childCount - mFooterCount - 1) {
            return false;
        }
        return true;
    }

}
