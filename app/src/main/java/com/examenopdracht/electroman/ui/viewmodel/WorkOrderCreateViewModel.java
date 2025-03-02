package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.examenopdracht.electroman.R;
import com.examenopdracht.electroman.WorkOrderDetailFragment;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

public class WorkOrderCreateViewModel extends AndroidViewModel {

    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> device = new MutableLiveData<>();
    private MutableLiveData<String> problemCode = new MutableLiveData<>();
    private MutableLiveData<String> customerName = new MutableLiveData<>();

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private WorkOrderRepository workOrderRepository;

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }

    public MutableLiveData<String> getDevice() {
        return device;
    }

    public void setDevice(MutableLiveData<String> device) {
        this.device = device;
    }

    public MutableLiveData<String> getProblemCode() {
        return problemCode;
    }

    public void setProblemCode(MutableLiveData<String> problemCode) {
        this.problemCode = problemCode;
    }

    public MutableLiveData<String> getCustomerName() {
        return customerName;
    }

    public void setCustomerName(MutableLiveData<String> customerName) {
        this.customerName = customerName;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public WorkOrderCreateViewModel(@NonNull Application application) {
        super(application);
        workOrderRepository = new WorkOrderRepository(application);
    }

    public void insertWorkOrder(long userId){
        Log.d("WorkOrderCreateViewModel", "insertWorkOrder: " + city.getValue() + " " + device.getValue() + " " + problemCode.getValue() + " " + customerName.getValue());
        WorkOrder workOrder = new WorkOrder(
                null,
                userId,
                city.getValue(),
                device.getValue(),
                problemCode.getValue(),
                customerName.getValue(),
                false,
                "",
                ""
        );

        workOrderRepository.insertWorkOrder(workOrder);
    }
}
