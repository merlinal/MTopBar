package com.merlin.bar.tool;

import android.util.Log;
import android.view.View;

/**
 * Created by ncm on 16/11/30.
 */

public abstract class SafeOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (view != null) {
            onClickView(view);
        } else {
            Log.e("SafeOnClickListener", "view is null");
        }
    }

    protected abstract void onClickView(View view);

}
