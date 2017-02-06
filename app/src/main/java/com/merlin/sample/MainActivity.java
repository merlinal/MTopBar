package com.merlin.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.merlin.bar.MTopBar;
import com.merlin.bar.tool.UiUtil;
import com.merlin.view.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MTopBar topBar = Util.findById(this, R.id.topBar);
        topBar.showSearch();
        topBar.setBarColor(0xff00cd00)
                .setPopColor(ContextCompat.getColor(this, R.color.theme))
                .setCurrentActivity(this)
                .setTitle("标题")
                .addMenu(1, R.string.abc_action_bar_home_description, 0, this)
                .addMenu(2, R.string.abc_action_mode_done, 0, this)
                .addMenu(3, R.string.app_name, 0, this)
                .addMenu(4, R.string.top_menu1, R.drawable.search_white, this)
                .addMenu(5, R.string.top_menu2, 0, this)
                .addMenu(6, R.string.top_menu3, R.drawable.delete, this)
                .addMenu(7, R.string.top_menu4, 0, this)
                .addMenu(8, R.string.top_menu5, 0, this)
                .addMenu(9, R.string.top_menu6, 0, this)
                .buildMenu();
    }

    @Override
    public void onClick(View view) {
        UiUtil.toast(this, view.getId() + "");
    }

}
