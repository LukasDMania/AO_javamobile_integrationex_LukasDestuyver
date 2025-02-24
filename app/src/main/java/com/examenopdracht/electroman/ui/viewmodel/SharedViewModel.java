package com.examenopdracht.electroman.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<WorkOrder> selectedWorkOrder = new MutableLiveData<>();

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
}
