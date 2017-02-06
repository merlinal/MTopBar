package com.merlin.bar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merlin.bar.item.BarItem;
import com.merlin.bar.item.MBarItemView;
import com.merlin.bar.more.AbstractRecyclerAdapter;
import com.merlin.bar.more.MRecyclerView;
import com.merlin.bar.more.RecyclerViewHolder;
import com.merlin.bar.tool.SafeOnClickListener;
import com.merlin.bar.tool.UiUtil;
import com.merlin.bar.tool.ValiUtil;
import com.merlin.view.MSearchView;
import com.merlin.view.Util;

import java.util.ArrayList;

/**
 * Created by ncm on 16/12/2.
 */

public class MTopBar extends LinearLayout implements View.OnClickListener {
    public MTopBar(Context context) {
        this(context, null);
    }

    public MTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ViewGroup mBackLayout;
    private ImageView mBackImg;
    private TextView mTitleText;
    private TextView mSubTitleText;
    private MSearchView mSearchView;
    private LinearLayout mContainerLayout;

    private Dialog mMoreMenuView;

    private int popColor;
    private int moreImgResId;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleText = Util.findById(this, R.id.topBar_title);
        mSubTitleText = Util.findById(this, R.id.topBar_subtitle);
        mBackLayout = Util.findById(this, R.id.topBar_back_layout);
        mBackImg = Util.findById(this, R.id.topBar_back_img);
        mSearchView = Util.findById(this, R.id.topBar_search);
        mContainerLayout = Util.findById(this, R.id.topBar_menu_container);
    }

    public MTopBar setBarColor(int color) {
        setBgColor(this, color);
        return this;
    }

    public MTopBar setTitle(int textSize, int textColor) {
        setTextView(mTitleText, textSize, textColor);
        return this;
    }

    public MTopBar setTitle(int strId) {
        mTitleText.setText(strId);
        return this;
    }

    public MTopBar setTitle(String str) {
        mTitleText.setText(str);
        return this;
    }

    public MTopBar setSubTitle(int textSize, int textColor) {
        setTextView(mSubTitleText, textSize, textColor);
        return this;
    }

    public MTopBar setSubTitle(int strId) {
        mSubTitleText.setVisibility(View.VISIBLE);
        mSubTitleText.setText(strId);
        return this;
    }

    public MTopBar setSubTitle(String str) {
        mSubTitleText.setVisibility(View.VISIBLE);
        mSubTitleText.setText(str);
        return this;
    }

    public MTopBar setBackImgRes(int imgResId) {
        mBackImg.setImageResource(imgResId);
        return this;
    }

    public MTopBar setPopColor(int color) {
        popColor = color;
        return this;
    }

    public MTopBar setMoreImgRes(int imgResId) {
        moreImgResId = imgResId;
        return this;
    }

    private MTopBar setBgColor(View view, int color) {
        view.setBackgroundColor(color);
        return this;
    }

    private MTopBar setTextView(TextView textView, int textSize, int textColor) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        return this;
    }

    private MTopBar setImage(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
        return this;
    }

    public MTopBar setCurrentActivity(final Activity currentActivity) {
        mBackLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentActivity != null) {
                    currentActivity.onBackPressed();
                }
            }
        });
        return this;
    }


    private ArrayList<BarItem> menuList = new ArrayList<>();
    private int alwaysShow = 3;

    public MTopBar hideBack() {
        mBackImg.setVisibility(View.GONE);
        return this;
    }

    public MTopBar addMenu(int id, int strId, int resId, OnClickListener onClickListener) {
        menuList.add(new BarItem(id, strId, resId, onClickListener));
        return this;
    }

    public MSearchView showSearch() {
        mSearchView.setVisibility(VISIBLE);
        alwaysShow--;
        buildMenu();
        return mSearchView;
    }

    public void buildMenu() {
        if (mBackImg.getVisibility() == View.GONE) {
            mBackLayout.setOnClickListener(null);
        }
        mContainerLayout.removeAllViews();
        if (!ValiUtil.isEmpty(menuList)) {
            if (menuList.size() > alwaysShow) {
                menuList.add(alwaysShow - 1, new BarItem(R.id.bar_more, 0, moreImgResId != 0 ? moreImgResId : R.drawable.bar_more, this));
            }
            for (int i = 0; i < Math.min(alwaysShow, menuList.size()); i++) {
                mContainerLayout.addView(getMenuItemView(menuList.get(i)));
            }
        }
    }

    private MBarItemView getMenuItemView(BarItem bi) {
        MBarItemView barItemView = (MBarItemView) UiUtil.findLayout(getContext(), R.layout.view_bar_item, this, false);
        barItemView.setData(bi);
        return barItemView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bar_more) {
            if (mMoreMenuView == null) {
                ArrayList<BarItem> moreMenus = new ArrayList<>(menuList.subList(alwaysShow, menuList.size()));
                mMoreMenuView = UiUtil.showDialog(getContext(), getMenuMoreView(moreMenus), 0, 0, Util.dp2px(getContext(), 8), getBottom(), 0, R.style.anim_alpha, Gravity.TOP | Gravity.RIGHT, true);
            } else {
                mMoreMenuView.show();
            }
        }
    }

    private View getMenuMoreView(ArrayList<BarItem> barItems) {
        MRecyclerView recyclerView = new MRecyclerView(getContext());
        recyclerView.list();
        recyclerView.setAdapter(new AbstractRecyclerAdapter<BarItem>(barItems) {

            @Override
            public int getItemResId(ViewGroup parent, int viewType) {
                return R.layout.view_bar_item;
            }

            @Override
            public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
                final BarItem bi = getData(position);
                MBarItemView barItemView = (MBarItemView) holder.itemView;
                barItemView.setData(bi);
                barItemView.setOnClickListener(new SafeOnClickListener() {
                    @Override
                    protected void onClickView(View view) {
                        mMoreMenuView.dismiss();
                        if (bi.getOnClickListener() != null) {
                            bi.getOnClickListener().onClick(view);
                        }
                    }
                });
            }
        });
        recyclerView.setBackgroundColor(popColor);
        return recyclerView;
    }

}
