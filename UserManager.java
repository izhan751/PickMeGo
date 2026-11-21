/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Manages user accounts (drivers and passengers) for the PickMeGo ride booking app.
 * Handles adding new users and authenticating existing users.
 * Currently stores users in-memory.
 */
public class UserManager {
    private List<DriverUser> drivers = new ArrayList<>();
    private List<PassengerUser> passengers = new ArrayList<>();

    /**
     * Adds a new DriverUser to the system.
     * @param driver The DriverUser object to add.
     */
    public void addDriver(DriverUser driver) {
        drivers.add(driver);
    }

    /**
     * Adds a new PassengerUser to the system.
     * @param passenger The PassengerUser object to add.
     */
    public void addPassenger(PassengerUser passenger) {
        passengers.add(passenger);
    }

    /**
     * Authenticates a user based on their email and password.
     * Searches through both driver and passenger lists.
     * @param email The email provided by the user.
     * @param password The password provided by the user.
     * @return The authenticated User object (DriverUser or PassengerUser) if credentials are valid,
     * otherwise returns null.
     */
    public User authenticate(String email, String password) {
        // Check drivers first
        for (DriverUser driver : drivers) {
            if (driver.getEmail().equals(email) && driver.getPassword().equals(password)) {
                return driver;
            }
        }
        // If not a driver, check passengers
        for (PassengerUser passenger : passengers) {
            if (passenger.getEmail().equals(email) && passenger.getPassword().equals(password)) {
                return passenger;
            }
        }
        return null; // Authentication failed
    }
}
