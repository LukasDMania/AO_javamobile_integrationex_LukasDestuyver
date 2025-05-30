package com.examenopdracht.electroman.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index(value = "userId")}
)
public class WorkOrder {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    // Foreign key
    private Long userId;

    private String city;
    private String device;
    private String problemCode;
    private String customerName;
    private boolean processed;
    private String detailedProblemDescription;
    private String repairInformation;

    // Constructors
    public WorkOrder() {
    }
    @Ignore
    public WorkOrder(Long id, Long userId, String city, String device, String problemCode, String customerName, boolean processed, String detailedProblemDescription, String repairInformation) {
        this.id = id;
        this.userId = userId;
        this.city = city;
        this.device = device;
        this.problemCode = problemCode;
        this.customerName = customerName;
        this.processed = processed;
        this.detailedProblemDescription = detailedProblemDescription;
        this.repairInformation = repairInformation;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getDevice() {
        return device;
    }
    public void setDevice(String device) {
        this.device = device;
    }

    public String getProblemCode() {
        return problemCode;
    }
    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isProcessed() {
        return processed;
    }
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getDetailedProblemDescription() {
        return detailedProblemDescription;
    }
    public void setDetailedProblemDescription(String detailedProblemDescription) {
        this.detailedProblemDescription = detailedProblemDescription;
    }

    public String getRepairInformation() {
        return repairInformation;
    }
    public void setRepairInformation(String repairInformation) {
        this.repairInformation = repairInformation;
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkOrder{" +
                "userId=" + userId +
                ", city='" + city + '\'' +
                ", device='" + device + '\'' +
                ", problemCode='" + problemCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", processed=" + processed +
                ", detailedProblemDescription='" + detailedProblemDescription + '\'' +
                ", repairInformation='" + repairInformation + '\'' +
                '}';
    }
}
