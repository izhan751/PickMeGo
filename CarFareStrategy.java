/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */

class CarFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 15.0;
    private static final double PER_MINUTE_RATE = 3.0;

    @Override
    public double calculateFare(double distance, double time) {
        return BASE_FARE + (distance * PER_KM_RATE) + (time * PER_MINUTE_RATE);
    }
}
