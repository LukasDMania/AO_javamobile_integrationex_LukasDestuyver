package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository mUserRepository;
    private LiveData<User> mAuthenticatedUser;
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
    }

    public LiveData<User> authenticateUser(String userName, String password) {
        return null;
    }

    public LiveData<User> getAuthenticatedUser() {
        return mAuthenticatedUser;
    }
    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }
}
