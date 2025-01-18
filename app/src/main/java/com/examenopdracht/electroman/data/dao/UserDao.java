package com.examenopdracht.electroman.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.relation.UserWithWorkOrders;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();
    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(Long id);
    @Query("SELECT * FROM User WHERE userName = :userName")
    User getUserByUserName(String userName);
    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    UserWithWorkOrders getUserWithWorkOrders(Long userId);
    @Transaction
    @Query("SELECT * FROM User")
    List<UserWithWorkOrders> getAllUsersWithWorkOrders();


    // LiveData for obseving changes
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsersLive();
    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserWithWorkOrders>> getAllUsersWithWorkOrdersLive();
}
