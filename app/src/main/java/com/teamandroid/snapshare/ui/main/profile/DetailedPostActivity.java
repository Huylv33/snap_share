package com.teamandroid.snapshare.ui.main.profile;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.teamandroid.snapshare.R;
import com.teamandroid.snapshare.data.model.Post;
import com.teamandroid.snapshare.data.model.User;
import com.teamandroid.snapshare.data.repository.FirestoreRepository;
import com.teamandroid.snapshare.databinding.ActivityDetailedPostBinding;
import com.teamandroid.snapshare.utils.Constants;

public class DetailedPostActivity extends AppCompatActivity {
    private ActivityDetailedPostBinding mBinding;
    private DetailedPostViewModel mDetailedPostViewModel;

    private String mPostId;
    private String mUserId;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        listenToArguments();
        handleArguments();
//        loadInfo(mUserId, mPostId);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        setToolbar();
        mDetailedPostViewModel = ViewModelProviders.of(this).get(DetailedPostViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_post);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mDetailedPostViewModel);
    }

    private void setToolbar() {
        this.setActionBar(mToolbar);
    }

    private void handleArguments() {
        Bundle args = getIntent().getExtras();
        mDetailedPostViewModel.getPostId().setValue(args.getString(Constants.ARGUMENT_POST_ID));
        mDetailedPostViewModel.getUserId().setValue(args.getString(Constants.ARGUMENT_USER_ID));
    }

    private void listenFollow() {
        mDetailedPostViewModel.getIsFollowed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    mBinding.buttonFollow.setText(getResources().getString(R.string.un_follow));
                else
                    mBinding.buttonFollow.setText(getResources().getString(R.string.follow));
            }
        });
    }

   /* private void loadInfo(String userId, String postId) {
        FirestoreRepository.getInstance().getUserById(userId,
                new FirestoreRepository.Callback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mBinding.setUser(result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });

        FirestoreRepository.getInstance().getPostById(postId,
        new FirestoreRepository.Callback<Post>() {
            @Override
            public void onSuccess(Post result) {
                mBinding.setPost(result);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }*/

    private void listenToArguments() {
        mDetailedPostViewModel.getUserId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mUserId = s;
            }
        });
        mDetailedPostViewModel.getPostId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPostId = s;
            }
        });
    }
}
