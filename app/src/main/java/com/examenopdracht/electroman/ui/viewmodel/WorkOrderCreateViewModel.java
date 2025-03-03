package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

public class WorkOrderCreateViewModel extends AndroidViewModel {
    private final WorkOrderRepository workOrderRepository;

    private final MutableLiveData<String> city = new MutableLiveData<>();
    private final MutableLiveData<String> device = new MutableLiveData<>();
    private final MutableLiveData<String> problemCode = new MutableLiveData<>();
    private final MutableLiveData<String> customerName = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> insertSuccess = new MutableLiveData<>();

    public WorkOrderCreateViewModel(@NonNull Application application) {
        super(application);
        workOrderRepository = new WorkOrderRepository(application);
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public MutableLiveData<String> getDevice() {
        return device;
    }


    public MutableLiveData<String> getProblemCode() {
        return problemCode;
    }


    public MutableLiveData<String> getCustomerName() {
        return customerName;
    }


    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getInsertSuccess() {
        return insertSuccess;
    }

    public boolean validInputWorkOrder() {
        if (city.getValue() == null || city.getValue().isEmpty()) {
            errorMessage.setValue("City cannot be empty");
            return false;
        }

        if (device.getValue() == null || device.getValue().isEmpty()) {
            errorMessage.setValue("Device cannot be empty");
            return false;
        }

        if (problemCode.getValue() == null || problemCode.getValue().isEmpty()) {
            errorMessage.setValue("ProblemCode cannot be empty");
            return false;
        }

        if (customerName.getValue() == null || customerName.getValue().isEmpty()) {
            errorMessage.setValue("CustomerName cannot be empty");
            return false;
        }

        return true;
    }
    public void doesWorkOrderAlreadyExistAndInsert(long userId) {
        workOrderRepository.doesWorkOrderExist(city.getValue(), device.getValue(), customerName.getValue()).observeForever(exists -> {
            if (exists) {
                errorMessage.setValue(
                        String.format("Work order already exists for city: %s, device: %s, problem code: %s",
                        city.getValue(), device.getValue(), customerName.getValue()));

                insertSuccess.setValue(false);
            } else {
                insertWorkOrder(userId);
                insertSuccess.setValue(true);
            }
        });
    }

    public void insertWorkOrder(long userId) {
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