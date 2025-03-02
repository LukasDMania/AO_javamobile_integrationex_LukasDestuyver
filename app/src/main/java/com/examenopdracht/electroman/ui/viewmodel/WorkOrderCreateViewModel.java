package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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

    public boolean validInputWorkOrder(){
        if (city.getValue() == null || city.getValue().isEmpty()) {
            errorMessage.setValue("City cannot be empty");
            return false;}

        if (device.getValue() == null || device.getValue().isEmpty()) {
            errorMessage.setValue("Device cannot be empty");
            return false;}
        if (problemCode.getValue() == null || problemCode.getValue().isEmpty()) {
            errorMessage.setValue("ProblemCode cannot be empty");
            return false;}
        if (customerName.getValue() == null || customerName.getValue().isEmpty()) {
            errorMessage.setValue("CustomerName cannot be empty");
            return false;
        }


        return true;
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
