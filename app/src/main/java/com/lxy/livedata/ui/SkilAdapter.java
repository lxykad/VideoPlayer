package com.lxy.livedata.ui;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxy.livedata.R;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author a
 * @date 2018/1/25
 */

public class SkilAdapter extends BaseQuickAdapter<SkilEntity, BaseViewHolder> {

    private List<SkilEntity> mList;

    public SkilAdapter(int layoutResId, @Nullable List<SkilEntity> data) {
        super(layoutResId, data);
        mList = new ArrayList<>();
      //  Collections.sort(data);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, SkilEntity bean) {

        Date date = DateUtil.parseDate(bean.createdAt);
        String str = DateUtil.formatDate(date);

        holder.setText(R.id.tv_des, bean.desc)
                .setText(R.id.tv_date, str);
        TextView tv = holder.getView(R.id.tv_des);
        // 测试代码
        if (bean._id.equals("5a685120421aa911548992ab")) {
            tv.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }
    }
}
