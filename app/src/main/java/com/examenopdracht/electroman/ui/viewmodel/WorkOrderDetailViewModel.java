package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

import java.util.Objects;

public class WorkOrderDetailViewModel extends AndroidViewModel {
    private final WorkOrderRepository workOrderRepository;

    private String initialRepairInfo;

    private MutableLiveData<WorkOrder> workOrderDetail = new MutableLiveData<>();

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public WorkOrderDetailViewModel(Application application) {
        super(application);
        workOrderRepository = new WorkOrderRepository(application);
    }

    public MutableLiveData<WorkOrder> getWorkOrderDetail() {
        return workOrderDetail;
    }

    public void setWorkOrderDetail(WorkOrder workOrder) {
        workOrderDetail.setValue(workOrder);
        initialRepairInfo = workOrder.getRepairInformation();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        errorMessage.setValue(message);
    }

    // Business Logic Methods
    public void updateWorkOrder() {
        if (workOrderDetail.getValue() != null) {
            workOrderDetail.getValue().setProcessed(true);
        }

        workOrderRepository.updateWorkOrder(workOrderDetail.getValue());
    }

    public boolean validRepairInfo() {
        Log.d("WorkOrderDetailViewModel", "Repair info: " + workOrderDetail.getValue().getRepairInformation());
        Log.d("WorkOrderDetailViewModel", "Initial repair info: " + initialRepairInfo);
        Log.d("WorkOrderDetailViewModel", "Is valid: " + !Objects.equals(workOrderDetail.getValue().getRepairInformation(), initialRepairInfo));
        return !Objects.equals(workOrderDetail.getValue().getRepairInformation(), initialRepairInfo);
    }

    public void reopenWorkOrder() {
        WorkOrder workorder = workOrderDetail.getValue();
        if (workorder != null && workorder.isProcessed()) {
            workorder.setProcessed(false);
            workOrderRepository.updateWorkOrder(workorder);
            workOrderDetail.setValue(workorder);
        }
    }
}