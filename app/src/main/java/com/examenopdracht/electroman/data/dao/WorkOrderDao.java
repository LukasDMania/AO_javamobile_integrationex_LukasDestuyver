package com.examenopdracht.electroman.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.List;

@Dao
public interface WorkOrderDao {
    @Insert
    void insertWorkOrder(WorkOrder workOrder);
    @Update
    void updateWorkOrder(WorkOrder workOrder);
    @Delete
    void deleteWorkOrder(WorkOrder workOrder);

    @Query("SELECT * FROM WorkOrder")
    List<WorkOrder> getAllWorkOrders();
    @Query("SELECT * FROM WorkOrder WHERE id = :id")
    WorkOrder getWorkOrderById(Long id);
    @Query("SELECT * FROM WorkOrder WHERE userId = :userId")
    List<WorkOrder> getWorkOrdersByUserId(Long userId);
    @Query("SELECT * FROM WorkOrder WHERE processed = :isProcessed")
    List<WorkOrder> getWorkOrdersByProcessedStatus(boolean isProcessed);

    // LiveData for observing changes
    @Query("SELECT * FROM WorkOrder")
    LiveData<List<WorkOrder>> getAllWorkOrdersLive();
    @Query("SELECT * FROM WorkOrder WHERE userId = :userId")
    LiveData<List<WorkOrder>> getWorkOrdersForUserLive(Long userId);


    @Query("SELECT COUNT(*) FROM WorkOrder")
    int getTotalWorkOrderCount();
    @Query("SELECT COUNT(*) FROM WorkOrder WHERE processed = 1")
    int getProcessedWorkOrderCount();
    @Query("SELECT COUNT(*) FROM WorkOrder WHERE userId = :userId")
    int getWorkOrderCountForUser(Long userId);
}
