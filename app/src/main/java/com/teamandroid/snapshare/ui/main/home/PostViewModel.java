package com.teamandroid.snapshare.ui.main.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.teamandroid.snapshare.data.model.Post;
import com.teamandroid.snapshare.data.repository.FirestoreRepository;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {
    private MutableLiveData<List<Post>> mPosts = new MutableLiveData<>();
    private FirestoreRepository mFirestoreRepository = FirestoreRepository.getInstance();

    public PostViewModel() {
        loadPosts();
    }

    public LiveData<List<Post>> getPosts() {
        return mPosts;
    }

    public void loadPosts() {
        final List<Post> posts = new ArrayList<>();
        mFirestoreRepository.getAllPosts(new FirestoreRepository.Callback<List<Post>>() {
            @Override
            public void onSuccess(final List<Post> result) {
                mFirestoreRepository.getFollowing(FirebaseAuth.getInstance().getCurrentUser().getUid()
                        , new FirestoreRepository.Callback<List<String>>() {
                            @Override
                            public void onSuccess(List<String> listFollowing) {
                                for (Post post : result) {
                                    if (listFollowing.contains(post.getUserId())) {
                                        posts.add(post);
                                    }
                                }
                                mPosts.setValue(posts);
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
            }

            @Override
            public void onFailure(Exception e) {
                //TODO handle when fetching fail
            }
        });
    }


}

