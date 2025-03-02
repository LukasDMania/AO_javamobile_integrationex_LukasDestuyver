package com.examenopdracht.electroman.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<WorkOrder> selectedWorkOrder = new MutableLiveData<>();
    private final MutableLiveData<List<WorkOrder>> allWorkOrders = new MutableLiveData<>();

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
    }
    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setSelectedWorkOrder(WorkOrder workOrder) {
        selectedWorkOrder.setValue(workOrder);
    }
    public LiveData<WorkOrder> getSelectedWorkOrder() {
        return selectedWorkOrder;
    }

    public MutableLiveData<List<WorkOrder>> getAllWorkOrders() {
        return allWorkOrders;
    }
    public void setAllWorkOrders(List<WorkOrder> workOrders) {
        allWorkOrders.setValue(workOrders);
    }
}
