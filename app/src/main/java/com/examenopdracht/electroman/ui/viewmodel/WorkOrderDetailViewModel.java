package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

public class WorkOrderDetailViewModel extends ViewModel {
    private WorkOrderRepository workOrderRepository;
    private MutableLiveData<WorkOrder> workOrderDetail = new MutableLiveData<>();

    public WorkOrderDetailViewModel(Application application) {
        workOrderRepository = new WorkOrderRepository(application);
    }

    public MutableLiveData<WorkOrder> getWorkOrderDetail() {
        return workOrderDetail;
    }
    public void setWorkOrderDetail(WorkOrder workOrder) {
        workOrderDetail.setValue(workOrder);
    }

}
