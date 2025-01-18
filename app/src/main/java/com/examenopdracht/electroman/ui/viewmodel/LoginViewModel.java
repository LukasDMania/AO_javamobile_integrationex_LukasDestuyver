package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> authenticateUser(String userName, String password) {
        return null;
    }
}
