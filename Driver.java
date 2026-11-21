/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
class Driver implements Observer {
    private String name;

    public Driver(String name) {
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println("Driver " + name + " received update: " + status);
    }
}
