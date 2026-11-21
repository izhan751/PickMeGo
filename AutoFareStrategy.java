/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
class AutoFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 30.0;
    private static final double PER_KM_RATE = 10.0;
    private static final double PER_MINUTE_RATE = 2.0;

    @Override
    public double calculateFare(double distance, double time) {
        // Basic fare calculation: base + (distance * per_km_rate) + (time * per_minute_rate)
        return BASE_FARE + (distance * PER_KM_RATE) + (time * PER_MINUTE_RATE);
    }
}