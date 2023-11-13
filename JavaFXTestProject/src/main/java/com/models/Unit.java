package com.models;

import java.util.ArrayList;
import java.util.Date;

public class Unit {
    private int unitID;
    private boolean isFurnished;
    private double rentAmount;
    private String floorplan;
    private String condition;
    private boolean isRented;
    private ArrayList<String> appliances;
    private boolean rentPaid;
    private Date rentDue;

    public Unit(int unitID, boolean isFurnished, double rentAmount, String floorplan, String condition, boolean isRented, ArrayList<String> appliances, boolean rentPaid, Date rentDue) {
        this.unitID = unitID;
        this.isFurnished = isFurnished;
        this.rentAmount = rentAmount;
        this.floorplan = floorplan;
        this.condition = condition;
        this.isRented = isRented;
        this.appliances = appliances;
        this.rentPaid = rentPaid;
        this.rentDue = rentDue;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getFloorplan() {
        return floorplan;
    }

    public void setFloorplan(String floorplan) {
        this.floorplan = floorplan;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public ArrayList<String> getAppliances() {
        return appliances;
    }

    public void setAppliances(ArrayList<String> appliances) {
        this.appliances = appliances;
    }

    public boolean isRentPaid() {
        return rentPaid;
    }

    public void setRentPaid(boolean rentPaid) {
        this.rentPaid = rentPaid;
    }

    public Date getRentDue() {
        return rentDue;
    }

    public void setRentDue(Date rentDue) {
        this.rentDue = rentDue;
    }



    @Override
    public String toString()
    {
        return unitID+","+ isFurnished +"," + rentAmount+"," + floorplan+"," + condition+"," + isRented+"," + rentPaid+"," + rentDue.toString();
    }


}

