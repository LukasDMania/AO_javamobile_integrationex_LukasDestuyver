package com.examenopdracht.electroman.util;

public class MockData {
    public static List<WorkOrder> getMockWorkOrders() {
        List<WorkOrder> mockOrders = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setUserId(31L); // userId is 31 for all mock orders
            workOrder.setCity("City " + i);
            workOrder.setDevice("Device " + i);
            workOrder.setProblemCode("Problem " + i);
            workOrder.setCustomerName("Customer " + i);
            workOrder.setProcessed(i % 2 == 0); // Alternate processed status (true/false)
            workOrder.setDetailedProblemDescription("Detailed description of problem " + i);
            workOrder.setRepairInformation("Repair information for device " + i);

            mockOrders.add(workOrder);
        }

        return mockOrders;
    }
}
