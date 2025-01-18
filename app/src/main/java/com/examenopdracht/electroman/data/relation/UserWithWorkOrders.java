package com.examenopdracht.electroman.data.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.entity.WorkOrder;

import java.util.List;

public class UserWithWorkOrders {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<WorkOrder> workOrders;
}
