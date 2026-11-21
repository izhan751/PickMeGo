/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
class FareContext {
    private FareStrategy fareStrategy;

    public void setFareStrategy(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public double getFare(double distance, double time) {
        if (fareStrategy == null) {
            throw new IllegalStateException("Fare strategy is not set");
        }
        return fareStrategy.calculateFare(distance, time);
    }
}
