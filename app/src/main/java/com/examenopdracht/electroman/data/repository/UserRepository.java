package com.examenopdracht.electroman.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.dao.UserDao;
import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.relation.UserWithWorkOrders;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;
    private final MutableLiveData<User> loggedInUser = new MutableLiveData<>();

    public UserRepository(Application application) {
        ElectromanDatabase db = ElectromanDatabase.getInstance(application);
        userDao = db.userDao();
    }

    //Basic CRUD operations
    public void insertUser(User user) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> userDao.insertUser(user));
    }
    public void updateUser(User user) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> userDao.updateUser(user));
    }
    public void deleteUser(User user) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> userDao.deleteUser(user));
    }

    public void deleteAll() {
        ElectromanDatabase.dbWriteExecutor.execute(userDao::deleteAll);
    }

    // Query operations$
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }
    public UserWithWorkOrders getUserWithWorkOrders(Long userId) {
        return userDao.getUserWithWorkOrders(userId);
    }
    public List<UserWithWorkOrders> getAllUsersWithWorkOrders() {
        return userDao.getAllUsersWithWorkOrders();
    }

    // LiveData for observing changes
    public LiveData<List<User>> getAllUsersLive() {
        return userDao.getAllUsersLive();
    }
    public LiveData<User> getUserByIdLive(Long id) {
        return userDao.getUserByIdLive(id);
    }
    public LiveData<User> getUserByUserNameLive(String userName){
        return userDao.getUserByUserNameLive(userName);
    }
    public LiveData<List<UserWithWorkOrders>> getAllUsersWithWorkOrdersLive() {
        return userDao.getAllUsersWithWorkOrdersLive();
    }

    public static class LoginResult {
        public final boolean success;
        public final String message;
        public final User user;

        public LoginResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
    }

    public LiveData<LoginResult> login(String userName, String password) {
        MutableLiveData<LoginResult> result = new MutableLiveData<>();

        ElectromanDatabase.dbWriteExecutor.execute(() -> {
            try {
                User user = getUserByUserName(userName);
                if (user != null && verifyPassword(password, user.getPassword())) {
                    loggedInUser.postValue(user);
                    result.postValue(new LoginResult(true, "Login successful", user));
                } else {
                    result.postValue(new LoginResult(false, "Invalid credentials", null));
                }
            } catch (Exception e) {
                result.postValue(new LoginResult(false, "Login failed: " + e.getMessage(), null));
            }
        });

        return result;
    }

    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        loggedInUser.postValue(null);
    }
    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}
