package com.examenopdracht.electroman.util;

import android.app.Application;
import android.util.Log;

import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.UserRepository;
import com.examenopdracht.electroman.data.repository.WorkOrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockData {

    private UserRepository userRepository;
    private WorkOrderRepository workOrderRepository;
    public MockData(Application application)
    {
        userRepository = new UserRepository(application);
        workOrderRepository = new WorkOrderRepository(application);
    }
    private void insertMockWorkOrdersForAdmin() {
        User admin = userRepository.getUserByUserName("admin");
        Log.d("MOCKADATe" , admin.toString());
        if (admin == null) { return; };

        for (int i = 1; i <= 10; i++) {
            Log.d("MOCKADATe" , "insertMockWorkOrdersForAdmin: " + i);
            Log.d("MOCKADATe" , "insertMockWorkOrdersForAdmin: " + workOrderRepository.getWorkOrderById((long) i));
            if (workOrderRepository.getWorkOrderById((long) i) != null) { return ;}
            WorkOrder workOrder = new WorkOrder();
            Log.d("MPOCKADATA", "ADMIN ID TO ISERT" + admin.getId());
            workOrder.setUserId(admin.getId());
            workOrder.setCity("City " + i);
            workOrder.setDevice("Device " + i);
            workOrder.setProblemCode("Problem " + i);
            workOrder.setCustomerName("Customer " + i);
            workOrder.setProcessed(i % 2 == 0);
            workOrder.setDetailedProblemDescription("Detailed description of problem " + i);
            workOrder.setRepairInformation("Repair information for device " + i);

            Log.d("MOCKADATe" , "insertMockWorkOrdersForAdmin WORKORDER TO INSERT: " + workOrder.toString());
            workOrderRepository.insertWorkOrder(workOrder);
            Log.d("MOCKADATe", "WorkOrder after insert: " + workOrder.toString());
        }
    }

    public void insertMockUsers(int amountOfMockUsers) {
        if(userRepository.getUserByUserName("admin") == null) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword("admin");
            user.setFirstName("AdminFN");
            user.setLastName("AdminLN");
            user.setBirthDate(LocalDate.now());
            user.setBox("Admin Box");
            user.setHouseNumber("Admin House Number");
            user.setStreet("Admin Street");
            user.setMunicipality("Admin Municipality");
            user.setPostalCode("Admin Postal Code 0000");
            userRepository.insertUser(user);
        }


        for (int i = 0; i < amountOfMockUsers; i++) {
            if (userRepository.getUserByUserName("user" + i) != null) { return ;}
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword("admin");
            user.setFirstName("First Name " + i);
            user.setLastName("Last Name " + i);
            user.setBirthDate(LocalDate.now());
            user.setBox("Test box" + i);
            user.setHouseNumber("5" + i);
            user.setStreet("Test Street" + i);
            user.setMunicipality("Test Municipality" + i);
            user.setPostalCode("0000" + i);

            userRepository.insertUser(user);
        }

        insertMockWorkOrdersForAdmin();
    }
}
