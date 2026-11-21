/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */


public class PassengerUser extends User {
    private double balance;

    /**
     * Constructs a new PassengerUser.
     * @param email The email address of the passenger.
     * @param password The password of the passenger.
     */
    public PassengerUser(String email, String password) {
        super(email, password);
        this.balance = 0; // Initialize balance to 0 upon creation
    }

    /**
     * Gets the current balance of the passenger.
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the passenger.
     * @param balance The new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Adds a specified amount to the passenger's balance.
     * @param amount The amount to add.
     */
    public void addBalance(double amount) {
        this.balance += amount;
    }

    /**
     * Returns the name of the passenger, currently derived from their email.
     * Can be replaced with passenger-specific name logic if needed in the future.
     * @return The passenger's name (email).
     */
    public String getName() {
        return super.getEmail();
    }
}