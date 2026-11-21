/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
public class PreBooking {
    private String pickupLocation;
    private String dropoffLocation;
    private String pickupTime;
    private boolean isAccepted;
    private PassengerUser passenger;
    private DriverUser driver; // Assigned later when accepted
    private double distance; // Added for fare calculation context
    private double rideTime; // Added for fare calculation context
    private double estimatedFare; // Added to store the fare at the time of pre-booking
    private double deductedAmount; // Added to store the 10% deducted amount

    public PreBooking(PassengerUser passenger, DriverUser driver, String pickupLocation,
                      String dropoffLocation, String pickupTime, double distance,
                      double rideTime, double estimatedFare, double deductedAmount) {
        this.passenger = passenger;
        this.driver = driver; // Can be null initially
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.pickupTime = pickupTime;
        this.isAccepted = false; // Initially not accepted
        this.distance = distance;
        this.rideTime = rideTime;
        this.estimatedFare = estimatedFare;
        this.deductedAmount = deductedAmount;
    }

    // --- Getters ---
    public PassengerUser getPassenger() {
        return passenger;
    }

    public DriverUser getDriver() {
        return driver;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public double getDistance() {
        return distance;
    }

    public double getRideTime() {
        return rideTime;
    }

    public double getEstimatedFare() {
        return estimatedFare;
    }

    public double getDeductedAmount() {
        return deductedAmount;
    }

    // --- Setters ---
    public void setDriver(DriverUser driver) {
        this.driver = driver;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    // toString method for easier debugging/display in future (optional, but good practice)
    @Override
    public String toString() {
        return "PreBooking{" +
               "pickupLocation='" + pickupLocation + '\'' +
               ", dropoffLocation='" + dropoffLocation + '\'' +
               ", pickupTime='" + pickupTime + '\'' +
               ", isAccepted=" + isAccepted +
               ", passenger=" + (passenger != null ? passenger.getEmail() : "N/A") +
               ", driver=" + (driver != null ? driver.getEmail() : "N/A") +
               ", distance=" + distance +
               ", rideTime=" + rideTime +
               ", estimatedFare=" + estimatedFare +
               ", deductedAmount=" + deductedAmount +
               '}';
    }
}
