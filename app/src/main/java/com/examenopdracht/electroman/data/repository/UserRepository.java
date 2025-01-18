package com.examenopdracht.electroman.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.examenopdracht.electroman.data.dao.UserDao;
import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.relation.UserWithWorkOrders;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;

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
    public LiveData<List<UserWithWorkOrders>> getAllUsersWithWorkOrdersLive() {
        return userDao.getAllUsersWithWorkOrdersLive();
    }

}
