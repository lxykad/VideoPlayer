package com.lxy.livedata;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.lxy.livedata.repository.SkilRepository;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 * @author a
 * @date 2018/1/4
 */

public class SkilViewModel extends ViewModel {

    public MediatorLiveData<Resource<List<SkilEntity>>> liveList;
    public SkilRepository skilRepository;

    public SkilViewModel() {

    }

    public SkilViewModel(SkilRepository repository) {
        System.out.println("=========SkilViewModel====");
    }

    public void loadData(String type, int count, int page) {
        if (skilRepository == null) {
            skilRepository = new SkilRepository();
        }

        liveList = skilRepository.getDataList(type, count, page);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        System.out.println("=========clear====");
    }
}
