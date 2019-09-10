package com.teamandroid.snapshare.ui.main.search;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.teamandroid.snapshare.BuildConfig;
import com.teamandroid.snapshare.data.model.User;
import com.teamandroid.snapshare.data.repository.FirestoreRepository;
import com.teamandroid.snapshare.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserSearchViewModel extends ViewModel {
    private final String TAG = UserSearchViewModel.class.getSimpleName();
    private FirestoreRepository mFirestore = FirestoreRepository.getInstance();
    private Client mClient = new Client(BuildConfig.ALGOLIA_APP_ID, BuildConfig.ALGOLIA_SEARCH_API_KEY);
    private Index mIndex = mClient.getIndex(Constants.AlGOLIA_INDEX);
    private MutableLiveData<List<User>> mUsers = new MutableLiveData<>();

    public MutableLiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void getUserListBySearchQuery(CharSequence queryString) {
        Query query = new Query(queryString);
        mIndex.searchAsync(query, new CompletionHandler() {
            @Override
            public void requestCompleted(@Nullable JSONObject jsonObject, @Nullable AlgoliaException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                if (jsonObject == null) return;
                List<User> users = new ArrayList<>();
                try {
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject jObject = hits.getJSONObject(i);
                        JSONObject highlightResult = jObject.getJSONObject("_highlightResult");
                        String matchLevelUsername = highlightResult.getJSONObject("username")
                                .getString("matchLevel");
                        User user = new User();
                        user.setUsername(jObject.getString(User.FIELD_USERNAME));
                        user.setId(jObject.getString(User.FIELD_ID));
                        user.setAvatarUrl(jObject.getString(User.FIELD_AVATAR_URL));
                        user.setFullName(jObject.getString(User.FIELD_FULL_NAME));
                        users.add(user);
                    }
                    mUsers.setValue(users);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
