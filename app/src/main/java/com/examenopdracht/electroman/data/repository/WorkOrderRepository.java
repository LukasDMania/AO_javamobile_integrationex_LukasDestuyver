package com.examenopdracht.electroman.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.examenopdracht.electroman.data.dao.WorkOrderDao;
import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.List;
public class WorkOrderRepository {
    private final WorkOrderDao workOrderDao;

    public WorkOrderRepository(Application application) {
        ElectromanDatabase db = ElectromanDatabase.getInstance(application);
        workOrderDao = db.workOrderDao();
    }

    //Basic CRUD operations
    public void insertWorkOrder(WorkOrder workOrder) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> workOrderDao.insertWorkOrder(workOrder));
    }
    public void updateWorkOrder(WorkOrder workOrder) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> workOrderDao.updateWorkOrder(workOrder));
    }
    public void deleteWorkOrder(WorkOrder workOrder) {
        ElectromanDatabase.dbWriteExecutor.execute(() -> workOrderDao.deleteWorkOrder(workOrder));
    }

    // Query operations
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderDao.getAllWorkOrders();
    }
    public WorkOrder getWorkOrderById(Long id) {
        return workOrderDao.getWorkOrderById(id);
    }
    public List<WorkOrder> getWorkOrdersByUserId(Long userId) {
        return workOrderDao.getWorkOrdersByUserId(userId);
    }
    public List<WorkOrder> getWorkOrdersByProcessedStatus(boolean isProcessed) {
        return workOrderDao.getWorkOrdersByProcessedStatus(isProcessed);
    }

    // LiveData for observing changes
    public LiveData<List<WorkOrder>> getAllWorkOrdersLive() {
        return workOrderDao.getAllWorkOrdersLive();
    }
    public LiveData<WorkOrder> getWorkOrderByIdLive(Long id) {
        return workOrderDao.getWorkOrderByIdLive(id);
    }
    public LiveData<List<WorkOrder>> getWorkOrdersForUserLive(Long userId) {
        return workOrderDao.getWorkOrdersForUserLive(userId);
    }

    public void deleteAllWorkOrders() {
        ElectromanDatabase.dbWriteExecutor.execute(workOrderDao::deleteAll);
    }

    public LiveData<Integer> doesWorkOrderExist(String city, String device, String problemCode) {
        return workOrderDao.doesWorkOrderExist(city, device, problemCode);
    }
}
