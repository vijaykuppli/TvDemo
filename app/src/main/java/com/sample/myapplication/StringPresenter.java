package com.sample.myapplication;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

public class StringPresenter extends Presenter {
    private static final String TAG = "StringPresenter";
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        TextView tv = new TextView(parent.getContext());
        tv.setFocusable(true);
        tv.setFocusableInTouchMode(true);
        tv.setBackground(
                parent.getContext().getResources().getDrawable(R.drawable.app_icon_your_company));
        return new ViewHolder(tv);
    }
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder for " + item.toString());
        ((TextView) viewHolder.view).setText(item.toString());
    }
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }
}