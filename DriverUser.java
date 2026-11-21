/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */


public class DriverUser extends User {
    public DriverUser(String email, String password) {
        super(email, password);
    }

    public String getName() {
        return super.getEmail(); // Replace with driver-specific logic if needed
    }
}
