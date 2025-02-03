package com.examenopdracht.electroman.util;

import android.app.Application;

import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;
import com.examenopdracht.electroman.data.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockData {
    public static List<WorkOrder> getMockWorkOrdersForUserId(Long userId) {
        List<WorkOrder> mockOrders = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setUserId(userId);
            workOrder.setCity("City " + i);
            workOrder.setDevice("Device " + i);
            workOrder.setProblemCode("Problem " + i);
            workOrder.setCustomerName("Customer " + i);
            workOrder.setProcessed(i % 2 == 0);
            workOrder.setDetailedProblemDescription("Detailed description of problem " + i);
            workOrder.setRepairInformation("Repair information for device " + i);

            mockOrders.add(workOrder);
        }

        return mockOrders;
    }

    public static List<User> getMockUsers(int amountOfMockUsers) {
        List<User> mockUsers = new ArrayList<>();

        for (int i = 0; i < amountOfMockUsers; i++) {
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

            mockUsers.add(user);
        }

        return mockUsers;
    }
}
