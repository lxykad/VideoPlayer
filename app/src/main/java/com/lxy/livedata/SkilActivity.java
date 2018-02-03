package com.lxy.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxy.livedata.base.BaseActivity;
import com.lxy.livedata.databinding.ActivitySkilBinding;
import com.lxy.livedata.ui.SkilAdapter;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.LoadingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a
 */
public class SkilActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final int PAGE_SIZE = 15;
    private ActivitySkilBinding mBinding;
    private SkilViewModel mViewModel;
    private SkilAdapter mAdapter;
    private List<SkilEntity> mList;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_skil);

        mViewModel = ViewModelProviders.of(this).get(SkilViewModel.class);

        initAdapter();
        loadData(true);
    }

    public void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new SkilAdapter(R.layout.list_item_skil, mList);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.refreshLayout.setOnRefreshListener(this);

    }

    public void loadData(boolean isRefresh) {
        mViewModel.loadData("Android", 15, mPage);
        subscribeData(isRefresh);
    }

    public void subscribeData(boolean isRefresh) {

        // 数据更新时 可以收到通知
        // onstop 时 自动阻断数据流
        mViewModel.liveList.observe(this, skilBeanResource -> {
            NetworkState status = skilBeanResource.status;
            switch (status) {
                case LOADING:
                    if (mPage == 1) {
                        LoadingUtil.showLoading(this);
                    }
                    break;
                case SUCCESS:
                    LoadingUtil.dismiss(this);
                    if (mBinding.refreshLayout.isRefreshing()) {
                        mBinding.refreshLayout.setRefreshing(false);
                    }
                    if (isRefresh) {
                        mAdapter.setEnableLoadMore(true);
                    }
                    if (skilBeanResource.data != null) {
                        setList(isRefresh, skilBeanResource.data);
                    }
                    break;
                case FAILED:
                    LoadingUtil.dismiss(this);
                    if (mBinding.refreshLayout.isRefreshing()) {
                        mBinding.refreshLayout.setRefreshing(false);
                    }
                    if (isRefresh) {
                        mAdapter.setEnableLoadMore(true);
                    }
                    break;
            }
        });
    }

    public void setList(boolean isRefresh, List<SkilEntity> list) {

        int size = list == null ? 0 : list.size();
        if (isRefresh) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();

        } else {
            if (size > 0) {
                mAdapter.loadMoreComplete();
                mList.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        }

        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        loadData(false);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        // 防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        loadData(true);
    }
}
