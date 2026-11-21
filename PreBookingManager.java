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
 * Manages pre-scheduled ride bookings.
 * This class handles the logic for viewing, accepting, scheduling, and cancelling pre-bookings.
 * It is adapted to work with a GUI, accepting parameters directly rather than using Scanner.
 */
public class PreBookingManager {
    private List<PreBooking> preBookings = new ArrayList<>();

    /**
     * Retrieves a list of all pending pre-bookings that are not yet accepted by any driver.
     * @return A list of available PreBooking objects.
     */
    public List<PreBooking> getPendingPreBookings() {
        List<PreBooking> pending = new ArrayList<>();
        for (PreBooking booking : preBookings) {
            if (!booking.isAccepted() && booking.getDriver() == null) {
                pending.add(booking);
            }
        }
        return pending;
    }

    /**
     * Retrieves a list of pre-scheduled bookings for a specific passenger that are not yet accepted.
     * @param passenger The PassengerUser whose bookings are to be retrieved.
     * @return A list of PreBooking objects belonging to the passenger and not yet accepted.
     */
    public List<PreBooking> getPassengerPendingPreBookings(PassengerUser passenger) {
        List<PreBooking> passengerBookings = new ArrayList<>();
        for (PreBooking booking : preBookings) {
            // Check if the booking belongs to this passenger and is not yet accepted
            if (booking.getPassenger() == passenger && !booking.isAccepted()) {
                passengerBookings.add(booking);
            }
        }
        return passengerBookings;
    }


    /**
     * Accepts a specific pre-booking for a driver.
     * @param driver The DriverUser accepting the booking.
     * @param bookingToAccept The PreBooking object to be accepted.
     * @return true if the booking was successfully accepted, false otherwise.
     */
    public boolean acceptPreBooking(DriverUser driver, PreBooking bookingToAccept) {
        if (bookingToAccept != null && !bookingToAccept.isAccepted() && bookingToAccept.getDriver() == null) {
            bookingToAccept.setAccepted(true);
            bookingToAccept.setDriver(driver); // Assign driver to the booking
            // Optional: Notify passenger via the observer pattern if implemented for GUI updates
            // bookingToAccept.getPassenger().update("Your pre-booking has been accepted by " + driver.getEmail());
            return true;
        }
        return false; // Invalid booking or already accepted
    }

    /**
     * Calculates the fare for an immediate ride based on selected vehicle type, distance, and time.
     * This method directly performs fare calculation without modifying internal state.
     * @param vehicleType The type of vehicle (e.g., "Car", "Bike").
     * @param distance The distance of the ride in kilometers.
     * @param time The duration of the ride in minutes.
     * @return The calculated fare, or -1 if the vehicle type is invalid.
     */
    public double calculateRideFare(String vehicleType, double distance, double time) {
        FareContext fareContext = new FareContext();
        FareStrategy strategy = getFareStrategyForType(vehicleType);
        if (strategy == null) {
            return -1.0; // Indicate invalid vehicle type
        }
        fareContext.setFareStrategy(strategy);
        return fareContext.getFare(distance, time);
    }

    /**
     * Schedules a new pre-booking for a passenger.
     * This method handles fare calculation, balance deduction, and adding the booking to the list.
     * @param passenger The PassengerUser scheduling the pre-booking.
     * @param pickupLocation The pickup location.
     * @param dropoffLocation The drop-off location.
     * @param pickupTime The scheduled pickup time.
     * @param distance The estimated distance in kilometers.
     * @param rideTime The estimated ride time in minutes.
     * @param vehicleType The selected vehicle type for fare calculation.
     * @return true if pre-booking was successful, false otherwise (e.g., insufficient balance).
     */
    public boolean schedulePreBooking(PassengerUser passenger, String pickupLocation, String dropoffLocation,
                                      String pickupTime, double distance, double rideTime, String vehicleType) {
        FareContext fareContext = new FareContext();
        FareStrategy strategy = getFareStrategyForType(vehicleType);
        if (strategy == null) {
            return false; // Invalid vehicle type
        }
        fareContext.setFareStrategy(strategy);
        double estimatedFare = fareContext.getFare(distance, rideTime);

        double deduction = estimatedFare * 0.1; // Deduct 10% of the calculated fare
        if (passenger.getBalance() < deduction) {
            return false; // Insufficient balance
        } else {
            passenger.setBalance(passenger.getBalance() - deduction);
            // Store all necessary details in the PreBooking object
            PreBooking newBooking = new PreBooking(passenger, null, pickupLocation, dropoffLocation,
                                                   pickupTime, distance, rideTime, estimatedFare, deduction);
            preBookings.add(newBooking);
            return true;
        }
    }

    /**
     * Cancels a specific pre-booking for a passenger and refunds the deducted amount.
     * @param passenger The PassengerUser attempting to cancel.
     * @param bookingToCancel The PreBooking object to cancel.
     * @return true if pre-booking was successfully cancelled and refunded, false otherwise.
     */
    public boolean cancelPreBooking(PassengerUser passenger, PreBooking bookingToCancel) {
        if (bookingToCancel != null && bookingToCancel.getPassenger() == passenger && !bookingToCancel.isAccepted()) {
            preBookings.remove(bookingToCancel);
            // Refund the 10% deduction
            passenger.addBalance(bookingToCancel.getDeductedAmount());
            return true;
        }
        return false; // Cannot cancel (e.g., already accepted, not owner, or invalid booking)
    }

    /**
     * Helper method to get the correct FareStrategy instance based on vehicle type string.
     * @param vehicleType The string representing the vehicle type (e.g., "Car", "Bike").
     * @return The corresponding FareStrategy instance, or null if type is unknown.
     */
    private FareStrategy getFareStrategyForType(String vehicleType) {
        return switch (vehicleType) {
            case "Car" -> new CarFareStrategy();
            case "Bike" -> new BikeFareStrategy();
            case "Auto-Rickshaw" -> new AutoFareStrategy();
            case "Cargo" -> new CargoFareStrategy();
            default -> null; // Invalid vehicle type
        };
    }
}