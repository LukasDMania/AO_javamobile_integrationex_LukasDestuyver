package com.examenopdracht.electroman.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.examenopdracht.electroman.data.dao.UserDao;
import com.examenopdracht.electroman.data.dao.WorkOrderDao;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, WorkOrder.class}, version = 1)
public abstract class ElectromanDatabase extends RoomDatabase {
    private static volatile ElectromanDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract WorkOrderDao workOrderDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ElectromanDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ElectromanDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ElectromanDatabase.class,
                            "electroman_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
