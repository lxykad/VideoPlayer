package com.lxy.livedata;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lxy.livedata.databinding.LivePlayerLayoutTxliveBinding;

/**
 * @author a
 * @date 2018/1/4
 */

public class PlayerLayout extends FrameLayout {

    public LivePlayerLayoutTxliveBinding mViewBinding;
    /**
     * 关联的Activity
     */
    public AppCompatActivity mAttachActivity;

    public PlayerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 布局
        init(context);
    }

    public void init(Context context) {

        if (context instanceof AppCompatActivity) {
            mAttachActivity = (AppCompatActivity) context;
        } else {
            throw new IllegalArgumentException("Context must be AppCompatActivity");
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.live_player_layout_txlive, this, true);

    }

}
