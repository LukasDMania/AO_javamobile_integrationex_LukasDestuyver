package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<List<WorkOrder>> workOrders = new MutableLiveData<>();

    private MutableLiveData<String> userDisplayName = new MutableLiveData<>();

    private MutableLiveData<Boolean> navigateToWorkOrderDetail = new MutableLiveData<>(false);

    public MainFragmentViewModel(Application application) {
        super(application);
        this.workOrderRepository = new WorkOrderRepository(application);
        this.userRepository = new UserRepository(application);
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
        updateUserDisplayName();
    }

    public LiveData<List<WorkOrder>> getWorkOrders() {
        return workOrders;
    }

    public MutableLiveData<String> getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(MutableLiveData<String> userDisplayName) {
        this.userDisplayName = userDisplayName;
    }


    public MutableLiveData<Boolean> getNavigateToWorkOrderDetail() {
        return navigateToWorkOrderDetail;
    }

    public void setNavigateToWorkOrderDetail(MutableLiveData<Boolean> navigateToWorkOrderDetail) {
        this.navigateToWorkOrderDetail = navigateToWorkOrderDetail;
    }

    private void updateUserDisplayName() {
        if (currentUser.getValue() != null) {
            userDisplayName.setValue(currentUser.getValue().getUserName());
        }
        Log.d("MainFragmentViewModel", "User Display Name: " + userDisplayName.getValue());
    }

    public void loadWorkOrders() {
        if (currentUser.getValue() != null) {
            workOrderRepository.getWorkOrdersForUserLive(currentUser.getValue().getId())
                    .observeForever(orders -> {
                        workOrders.setValue(orders);
                    });
        } else {
            Log.e("MainFragmentViewModel", "Cannot load work orders: currentUser is null");
        }
    }
}