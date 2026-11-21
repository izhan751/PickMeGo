/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */
import javax.swing.*;
import java.awt.*;

/**
 * The main JFrame for the PickMeGo GUI.
 * Uses a CardLayout to switch between different panels (Login, Registration, Passenger, Driver).
 */
public class MainFrame extends JFrame {

    // Panel Identifiers for CardLayout
    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String REGISTRATION_PANEL = "RegistrationPanel";
    public static final String PASSENGER_PANEL = "PassengerPanel";
    public static final String DRIVER_PANEL = "DriverPanel";

    private JPanel cardPanel; // Panel that uses CardLayout
    private CardLayout cardLayout; // Layout manager for switching panels

    // References to the individual panels
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private PassengerPanel passengerPanel;
    private DriverPanel driverPanel;

    /**
     * Constructor for MainFrame.
     * Sets up the main window and initializes the CardLayout.
     * @param title The title of the JFrame.
     */
    public MainFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set a reasonable size for the window
        setLocationRelativeTo(null); // Center the window on the screen

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout); // Initialize the panel with CardLayout
        add(cardPanel); // Add the cardPanel to the JFrame
    }

    /**
     * Sets the LoginPanel instance for the frame and adds it to the card layout.
     * @param panel The LoginPanel instance.
     */
    public void setLoginPanel(LoginPanel panel) {
        this.loginPanel = panel;
        cardPanel.add(loginPanel, LOGIN_PANEL); // Add panel with its identifier
    }

    /**
     * Sets the RegistrationPanel instance for the frame and adds it to the card layout.
     * @param panel The RegistrationPanel instance.
     */
    public void setRegistrationPanel(RegistrationPanel panel) {
        this.registrationPanel = panel;
        cardPanel.add(registrationPanel, REGISTRATION_PANEL);
    }

    /**
     * Sets the PassengerPanel instance for the frame and adds it to the card layout.
     * @param panel The PassengerPanel instance.
     */
    public void setPassengerPanel(PassengerPanel panel) {
        this.passengerPanel = panel;
        cardPanel.add(passengerPanel, PASSENGER_PANEL);
    }

    /**
     * Sets the DriverPanel instance for the frame and adds it to the card layout.
     * @param panel The DriverPanel instance.
     */
    public void setDriverPanel(DriverPanel panel) {
        this.driverPanel = panel;
        cardPanel.add(driverPanel, DRIVER_PANEL);
    }

    /**
     * Displays the panel identified by the given panel name.
     * @param panelName The identifier string of the panel to show (e.g., MainFrame.LOGIN_PANEL).
     */
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName); // Show the specified card
    }

    /**
     * Gets the current PassengerUser logged in, if any.
     * This relies on SessionManager and is used by PassengerPanel to update its state.
     * @return The currently logged-in PassengerUser, or null if no passenger is logged in.
     */
    public PassengerUser getCurrentPassengerUser() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser instanceof PassengerUser) {
            return (PassengerUser) currentUser;
        }
        return null;
    }

    /**
     * Gets the current DriverUser logged in, if any.
     * This relies on SessionManager.
     * @return The currently logged-in DriverUser, or null if no driver is logged in.
     */
    public DriverUser getCurrentDriverUser() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser instanceof DriverUser) {
            return (DriverUser) currentUser;
        }
        return null;
    }

    /**
     * Updates the PassengerPanel after a balance change or pre-booking action.
     * This method is called from other panels/dialogs to refresh the passenger view.
     */
    public void updatePassengerPanel() {
        if (passengerPanel != null) {
            passengerPanel.refreshBalanceDisplay();
            passengerPanel.refreshPreBookingsList();
        }
    }

    /**
     * Updates the DriverPanel after a pre-booking acceptance.
     * This method is called from other panels/dialogs to refresh the driver view.
     */
    public void updateDriverPanel() {
        if (driverPanel != null) {
            driverPanel.refreshPreBookingsDisplay();
        }
    }
}