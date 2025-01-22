package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private MutableLiveData<String> loginStatusMessage = new MutableLiveData<>();

    private final UserRepository userRepository;


    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        loginStatusMessage.setValue("Login successful");
        loginErrorMessage.setValue("test");
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }
    public void setUserName(MutableLiveData<String> userName) {
        this.userName = userName;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }
    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public MutableLiveData<String> getLoginErrorMessage() {
        return loginErrorMessage;
    }
    public void setLoginErrorMessage(MutableLiveData<String> loginErrorMessage) {
        this.loginErrorMessage = loginErrorMessage;
    }

    public MutableLiveData<String> getLoginStatusMessage() {
        return loginStatusMessage;
    }
    public void setLoginStatusMessage(MutableLiveData<String> loginStatusMessage) {
        this.loginStatusMessage = loginStatusMessage;
    }

    public void loginUser() {
        String usernameValue = userName.getValue();
        String passwordValue = password.getValue();

        if (usernameValue == null || usernameValue.isEmpty()) {
            loginErrorMessage.setValue("Username cannot be empty");
            return;
        }

        if (passwordValue == null || passwordValue.isEmpty()) {
            loginErrorMessage.setValue("Password cannot be empty");
            return;
        }

        loginStatusMessage.setValue("Logging in...");
        userRepository.login(usernameValue, passwordValue).observeForever(result -> {
            if (result.success) {
                loginStatusMessage.setValue("Login successful");
                loginErrorMessage.setValue(null);
                new Handler().postDelayed(() -> {
                    // TODO: navigate to main fragment
                }, 3000);
            } else {
                loginErrorMessage.setValue(result.message);
                loginStatusMessage.setValue(null);
            }
        });
    }
}
