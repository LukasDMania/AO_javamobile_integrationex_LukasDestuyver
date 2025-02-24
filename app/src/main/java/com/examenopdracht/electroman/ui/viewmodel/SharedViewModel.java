package com.examenopdracht.electroman.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examenopdracht.electroman.data.entity.User;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
    }
    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
}
