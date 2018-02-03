package com.lxy.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lxy.livedata.databinding.ActivityMainBinding;
import com.lxy.livedata.player.PlayerViewModel;

/**
 * @author a
 */
public class MainActivity extends AppCompatActivity {

    private String mUrl = "http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571/SD/nNTov5571-mobile.mp4";
    private ActivityMainBinding mBinding;
    private PlayerViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MutableLiveData<String> liveData = new MutableLiveData<>();
        mViewModel = new PlayerViewModel(mBinding.playerLayout, liveData);
        liveData.setValue(mUrl);

        getLifecycle().addObserver(mViewModel);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mViewModel.configurationChanged(newConfig);
    }
}
