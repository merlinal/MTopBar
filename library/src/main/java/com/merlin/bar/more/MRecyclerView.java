package com.merlin.bar.more;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.merlin.view.Util;

/**
 * Created by ncm on 16/11/13.
 */

public class MRecyclerView extends RecyclerView {

    public MRecyclerView(Context context) {
        super(context);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean loading = false;

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (onScrollListener != null) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null) { //it maybe unnecessary
                throw new RuntimeException("LayoutManagers is null,Please check it!");
            }
            Adapter adapter = getAdapter();
            if (adapter == null) { //it maybe unnecessary
                throw new RuntimeException("Adapter is null,Please check it!");
            }
            //GridLayoutManager
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int rowCount = (int) Math.ceil(adapter.getItemCount() * 1.0 / gridLayoutManager.getSpanCount());
                int lastVisibleRowPosition = (int) Math.ceil(gridLayoutManager.findLastVisibleItemPosition() * 1.0 / gridLayoutManager.getSpanCount());
                if (!loading) {
                    onScrollListener.onScroll(this, getScrollState(), lastVisibleRowPosition, rowCount);
                }
            }
            //LinearLayoutManager
            else if (layoutManager instanceof LinearLayoutManager) {
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int rowCount = adapter.getItemCount();
                if (!loading) {
                    onScrollListener.onScroll(this, getScrollState(), lastVisibleItemPosition, rowCount);
                }
            }
            //StaggeredGridLayoutManager
            else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int spanCount = staggeredGridLayoutManager.getSpanCount();
                int[] into = new int[spanCount];
                int[] eachSpanListVisibleItemPosition = staggeredGridLayoutManager.findLastVisibleItemPositions(into);
                int lastPosition = 0;
                for (int i = 0; i < spanCount; i++) {
                    lastPosition = Math.max(lastPosition, eachSpanListVisibleItemPosition[i]);
                }
                if (!loading) {
                    onScrollListener.onScroll(this, getScrollState(),
                            (int) Math.ceil(lastPosition / spanCount),
                            (int) Math.ceil(adapter.getItemCount() * 1.0 / spanCount));
                }
            }
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public interface OnScrollListener {
        void onScroll(MRecyclerView recyclerView, int state, int lastVisibleRow, int rowCount);
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void list() {
        list(LinearLayoutManager.VERTICAL, Util.dp2px(getContext(), 1), 0, 0);
    }

    public void list(int orientation, int dividerWidth, int headerCount, int footerCount) {
        setLayoutManager(new LinearLayoutManager(getContext(), orientation, false));
        addItemDecoration(new RecyclerListDecoration(getContext(), orientation, dividerWidth, headerCount, footerCount));
        setItemAnimator(new DefaultItemAnimator());
    }

    public void grid(int numColumns) {
        setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        //addItemDecoration(new RecyclerGridDecoration(getContext()));
        setItemAnimator(new DefaultItemAnimator());
    }

    public void staggered(int numColumns) {
        setLayoutManager(new StaggeredGridLayoutManager(numColumns, StaggeredGridLayoutManager.VERTICAL));
        //addItemDecoration(new RecyclerGridDecoration(getContext()));
        setItemAnimator(new DefaultItemAnimator());
    }

}
