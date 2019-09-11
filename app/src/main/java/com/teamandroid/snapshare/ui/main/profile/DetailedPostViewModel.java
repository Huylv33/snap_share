package com.teamandroid.snapshare.ui.main.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailedPostViewModel extends ViewModel {
    private MutableLiveData<String> mUserId = new MutableLiveData<>();
    private MutableLiveData<String> mPostId = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsFollowed = new MutableLiveData<>();


    public DetailedPostViewModel() {

    }

    public MutableLiveData<String> getUserId() {
        return mUserId;
    }

    public MutableLiveData<String> getPostId() {
        return mPostId;
    }

    public MutableLiveData<Boolean> getIsFollowed() {
        return mIsFollowed;
    }
}
