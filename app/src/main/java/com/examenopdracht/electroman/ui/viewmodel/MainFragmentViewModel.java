package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private final MutableLiveData<List<WorkOrder>> workOrders = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public MainFragmentViewModel(Application application) {
        super(application);
        this.workOrderRepository = new WorkOrderRepository(application);
        this.userRepository = new UserRepository(application);
    }

    public LiveData<List<WorkOrder>> getWorkOrders() {
        return workOrders;
    }

    public LiveData<User> getCurrentUser(){
        return currentUser;
    }

    //Load data
    public void loadWorkOrders(){
        //TODO: Implement loading work orders based on the user
    }
    public void loadCurrentUser(){
        //TODO: Implement loading current user
    }
    public void markWorkOrderAsProcessed(WorkOrder workOrder) {
        //TODO: Implement marking work order as processed
    }
}
