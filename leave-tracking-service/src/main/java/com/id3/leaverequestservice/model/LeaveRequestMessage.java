package com.id3.leaverequestservice.model;

import java.io.Serializable;
import java.util.Date;


public class LeaveRequestMessage implements Serializable {
    private String firstName;
    private String lastName;
    private String mail;
    private String managerMail;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private String description;
    private String leaveType;
    private String status;

    public LeaveRequestMessage(String firstName, String lastName, String mail, String managerMail, Date leaveStartDate, Date leaveEndDate, String description, String leaveType, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.managerMail = managerMail;
        this.leaveStartDate = leaveStartDate;
        this.leaveEndDate = leaveEndDate;
        this.description = description;
        this.leaveType = leaveType;
        this.status = status;
    }

    public LeaveRequestMessage() {
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }

    public Date getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(Date leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public Date getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(Date leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeaveRequestMessage{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", managerMail='" + managerMail + '\'' +
                ", leaveStartDate=" + leaveStartDate +
                ", leaveEndDate=" + leaveEndDate +
                ", description='" + description + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
