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
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.text.DecimalFormat; // For formatting double values

/**
 * GUI panel for Passenger functionalities.
 * Provides options for booking rides, pre-scheduling, adding balance,
 * cancelling pre-bookings, and logging out.
 * Displays current balance and lists pre-scheduled rides.
 */
public class PassengerPanel extends JPanel {
    private MainFrame mainFrame;
    private PreBookingManager preBookingManager;
    private SessionManager sessionManager;
    private PassengerUser currentUser;

    private JLabel balanceLabel;
    private JTextArea preBookingsTextArea;
    private JButton bookRideButton;
    private JButton preScheduleRideButton;
    private JButton addBalanceButton;
    private JButton cancelPreBookingButton;
    private JButton logoutButton;

    // DecimalFormat for currency display
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("Rs#,##0.00");


    /**
     * Constructs a PassengerPanel.
     * @param mainFrame The main application frame for navigation.
     * @param preBookingManager The PreBookingManager to handle booking logic.
     * @param sessionManager The SessionManager to get current user info and handle logout.
     */
    public PassengerPanel(MainFrame mainFrame, PreBookingManager preBookingManager, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.preBookingManager = preBookingManager;
        this.sessionManager = sessionManager;

        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for overall structure
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Top Panel for User Info and Balance
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Padding at bottom
        JLabel welcomeLabel = new JLabel("Welcome, Passenger!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel = new JLabel("Current Balance: Rs0.00");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createHorizontalStrut(50)); // Spacer
        topPanel.add(balanceLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow buttons to stretch horizontally

        bookRideButton = new JButton("Book a Ride");
        preScheduleRideButton = new JButton("Pre-Schedule a Ride");
        addBalanceButton = new JButton("Add Balance");
        cancelPreBookingButton = new JButton("Cancel Pre-Scheduled Ride");
        logoutButton = new JButton("Logout");

        // Set consistent button sizes/fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 40);

        bookRideButton.setFont(buttonFont);
        bookRideButton.setPreferredSize(buttonSize);
        preScheduleRideButton.setFont(buttonFont);
        preScheduleRideButton.setPreferredSize(buttonSize);
        addBalanceButton.setFont(buttonFont);
        addBalanceButton.setPreferredSize(buttonSize);
        cancelPreBookingButton.setFont(buttonFont);
        cancelPreBookingButton.setPreferredSize(buttonSize);
        logoutButton.setFont(buttonFont);
        logoutButton.setPreferredSize(buttonSize);

        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(bookRideButton, gbc);
        gbc.gridy = 1; centerPanel.add(preScheduleRideButton, gbc);
        gbc.gridy = 2; centerPanel.add(addBalanceButton, gbc);
        gbc.gridy = 3; centerPanel.add(cancelPreBookingButton, gbc);
        gbc.gridy = 4; centerPanel.add(logoutButton, gbc);

        add(centerPanel, BorderLayout.WEST); // Add buttons to the west side

        // Right Panel for Pre-Scheduled Rides List
        JPanel preBookingsPanel = new JPanel(new BorderLayout());
        preBookingsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Your Pre-Scheduled Rides",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        preBookingsTextArea = new JTextArea();
        preBookingsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(preBookingsTextArea);
        preBookingsPanel.add(scrollPane, BorderLayout.CENTER);

        add(preBookingsPanel, BorderLayout.CENTER); // Add pre-bookings list to center

        addListeners(); // Attach event listeners
    }

    /**
     * Attaches action listeners to the buttons.
     */
    private void addListeners() {
        bookRideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookRideDialog();
            }
        });

        preScheduleRideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreScheduleRideDialog();
            }
        });

        addBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddBalanceDialog();
            }
        });

        cancelPreBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCancelPreBookingDialog();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sessionManager.logout();
                mainFrame.showPanel(MainFrame.LOGIN_PANEL); // Go back to login
                MessageDialog.showInfo(mainFrame, "Logged Out", "You have been logged out successfully.");
            }
        });
    }

    /**
     * Refreshes the display of the current passenger's balance.
     */
    public void refreshBalanceDisplay() {
        currentUser = mainFrame.getCurrentPassengerUser();
        if (currentUser != null) {
            balanceLabel.setText("Current Balance: " + CURRENCY_FORMAT.format(currentUser.getBalance()));
        } else {
            balanceLabel.setText("Current Balance: N/A");
        }
    }

    /**
     * Refreshes the list of pre-scheduled rides for the current passenger.
     */
    public void refreshPreBookingsList() {
        currentUser = mainFrame.getCurrentPassengerUser();
        preBookingsTextArea.setText(""); // Clear existing text
        if (currentUser != null) {
            List<PreBooking> bookings = preBookingManager.getPassengerPendingPreBookings(currentUser);
            if (bookings.isEmpty()) {
                preBookingsTextArea.setText("No pending pre-scheduled rides.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bookings.size(); i++) {
                    PreBooking booking = bookings.get(i);
                    sb.append(String.format("%d. From: %s To: %s At: %s (Fare: %s)\n",
                            (i + 1), booking.getPickupLocation(), booking.getDropoffLocation(),
                            booking.getPickupTime(), CURRENCY_FORMAT.format(booking.getEstimatedFare())));
                }
                preBookingsTextArea.setText(sb.toString());
            }
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) { // When panel becomes visible, refresh data
            refreshBalanceDisplay();
            refreshPreBookingsList();
        }
    }

    /**
     * Displays a dialog for booking an immediate ride.
     */
    private void showBookRideDialog() {
        JDialog dialog = new JDialog(mainFrame, "Book a Ride", true); // Modal dialog
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(mainFrame); // Center relative to main frame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField pickupField = new JTextField(15);
        JTextField dropoffField = new JTextField(15);
        JTextField distanceField = new JTextField(10);
        JTextField timeField = new JTextField(10);
        String[] vehicleTypes = {"Car", "Bike", "Auto-Rickshaw", "Cargo"};
        JComboBox<String> vehicleTypeComboBox = new JComboBox<>(vehicleTypes);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Pickup Location:"), gbc);
        gbc.gridx = 1; dialog.add(pickupField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Dropoff Location:"), gbc);
        gbc.gridx = 1; dialog.add(dropoffField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Distance (km):"), gbc);
        gbc.gridx = 1; dialog.add(distanceField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Time (minutes):"), gbc);
        gbc.gridx = 1; dialog.add(timeField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Vehicle Type:"), gbc);
        gbc.gridx = 1; dialog.add(vehicleTypeComboBox, gbc);

        JLabel fareDisplayLabel = new JLabel("Estimated Fare: N/A");
        fareDisplayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; dialog.add(fareDisplayLabel, gbc);


        JButton calculateFareButton = new JButton("Calculate Fare");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; dialog.add(calculateFareButton, gbc);

        JButton bookButton = new JButton("Book Ride");
        bookButton.setEnabled(false); // Disabled until fare calculated
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; dialog.add(bookButton, gbc);


        calculateFareButton.addActionListener(e -> {
            try {
                double distance = Double.parseDouble(distanceField.getText());
                double time = Double.parseDouble(timeField.getText());
                String vehicleType = Objects.requireNonNull(vehicleTypeComboBox.getSelectedItem()).toString();

                double fare = preBookingManager.calculateRideFare(vehicleType, distance, time);
                if (fare >= 0) {
                    fareDisplayLabel.setText("Estimated Fare: " + CURRENCY_FORMAT.format(fare));
                    bookButton.setEnabled(true);
                } else {
                    fareDisplayLabel.setText("Estimated Fare: Invalid Vehicle Type");
                    bookButton.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                MessageDialog.showError(dialog, "Input Error", "Please enter valid numbers for distance and time.");
                fareDisplayLabel.setText("Estimated Fare: N/A");
                bookButton.setEnabled(false);
            }
        });

        bookButton.addActionListener(e -> {
            // In a real app, this would involve driver matching and ride start
            // For now, it just confirms the booking based on fare calculation.
            String pickup = pickupField.getText();
            String dropoff = dropoffField.getText();
            String fareText = fareDisplayLabel.getText().replace("Estimated Fare: ", "");

            if (pickup.isEmpty() || dropoff.isEmpty() || fareText.contains("N/A") || fareText.contains("Invalid")) {
                MessageDialog.showError(dialog, "Booking Error", "Please fill all details and calculate fare first.");
                return;
            }

            MessageDialog.showInfo(dialog, "Ride Booked", "Ride from " + pickup + " to " + dropoff + " booked.\n" +
                                                      "Total fare: " + fareText);
            dialog.dispose(); // Close dialog after booking
        });

        dialog.setVisible(true);
    }

    /**
     * Displays a dialog for pre-scheduling a ride.
     */
    private void showPreScheduleRideDialog() {
        JDialog dialog = new JDialog(mainFrame, "Pre-Schedule a Ride", true); // Modal dialog
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(mainFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField pickupField = new JTextField(15);
        JTextField dropoffField = new JTextField(15);
        JTextField timeField = new JTextField(15); // For pickup time string
        JTextField distanceField = new JTextField(10);
        JTextField rideTimeField = new JTextField(10); // For estimated ride duration
        String[] vehicleTypes = {"Car", "Bike", "Auto-Rickshaw", "Cargo"};
        JComboBox<String> vehicleTypeComboBox = new JComboBox<>(vehicleTypes);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Pickup Location:"), gbc);
        gbc.gridx = 1; dialog.add(pickupField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Dropoff Location:"), gbc);
        gbc.gridx = 1; dialog.add(dropoffField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Pickup Time (e.g., 10:00 AM):"), gbc);
        gbc.gridx = 1; dialog.add(timeField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Distance (km):"), gbc);
        gbc.gridx = 1; dialog.add(distanceField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Est. Ride Time (minutes):"), gbc);
        gbc.gridx = 1; dialog.add(rideTimeField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Vehicle Type:"), gbc);
        gbc.gridx = 1; dialog.add(vehicleTypeComboBox, gbc);


        JLabel fareDisplayLabel = new JLabel("Estimated Fare: N/A");
        fareDisplayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; dialog.add(fareDisplayLabel, gbc);

        JLabel deductionDisplayLabel = new JLabel("10% Deduction: N/A");
        deductionDisplayLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; dialog.add(deductionDisplayLabel, gbc);


        JButton calculateFareButton = new JButton("Calculate Fare & Check Balance");
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; dialog.add(calculateFareButton, gbc);

        JButton scheduleButton = new JButton("Schedule Pre-Booking");
        scheduleButton.setEnabled(false); // Disabled until fare calculated and balance checked
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; dialog.add(scheduleButton, gbc);


        calculateFareButton.addActionListener(e -> {
            try {
                double distance = Double.parseDouble(distanceField.getText());
                double rideTime = Double.parseDouble(rideTimeField.getText());
                String vehicleType = Objects.requireNonNull(vehicleTypeComboBox.getSelectedItem()).toString();

                double estimatedFare = preBookingManager.calculateRideFare(vehicleType, distance, rideTime);
                if (estimatedFare >= 0) {
                    fareDisplayLabel.setText("Estimated Fare: " + CURRENCY_FORMAT.format(estimatedFare));
                    double deduction = estimatedFare * 0.1;
                    deductionDisplayLabel.setText("10% Deduction: " + CURRENCY_FORMAT.format(deduction));

                    currentUser = mainFrame.getCurrentPassengerUser();
                    if (currentUser.getBalance() >= deduction) {
                        scheduleButton.setEnabled(true);
                    } else {
                        MessageDialog.showError(dialog, "Insufficient Balance",
                                "Not enough balance for the 10% deduction (" + CURRENCY_FORMAT.format(deduction) + "). " +
                                "Your current balance: " + CURRENCY_FORMAT.format(currentUser.getBalance()));
                        scheduleButton.setEnabled(false);
                    }
                } else {
                    fareDisplayLabel.setText("Estimated Fare: Invalid Vehicle Type");
                    deductionDisplayLabel.setText("10% Deduction: N/A");
                    scheduleButton.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                MessageDialog.showError(dialog, "Input Error", "Please enter valid numbers for distance and estimated ride time.");
                fareDisplayLabel.setText("Estimated Fare: N/A");
                deductionDisplayLabel.setText("10% Deduction: N/A");
                scheduleButton.setEnabled(false);
            }
        });

        scheduleButton.addActionListener(e -> {
            String pickup = pickupField.getText();
            String dropoff = dropoffField.getText();
            String pickupTime = timeField.getText();
            try {
                double distance = Double.parseDouble(distanceField.getText());
                double rideTime = Double.parseDouble(rideTimeField.getText());
                String vehicleType = Objects.requireNonNull(vehicleTypeComboBox.getSelectedItem()).toString();

                if (pickup.isEmpty() || dropoff.isEmpty() || pickupTime.isEmpty()) {
                    MessageDialog.showError(dialog, "Input Error", "Please fill all location and time fields.");
                    return;
                }

                currentUser = mainFrame.getCurrentPassengerUser(); // Ensure currentUser is up-to-date

                boolean success = preBookingManager.schedulePreBooking(currentUser, pickup, dropoff,
                        pickupTime, distance, rideTime, vehicleType);

                if (success) {
                    MessageDialog.showInfo(dialog, "Pre-Booking Scheduled", "Your pre-booking has been scheduled successfully!");
                    mainFrame.updatePassengerPanel(); // Refresh passenger panel
                    dialog.dispose();
                } else {
                    // This case should ideally be caught by the calculateFareButton logic, but as a fallback
                    MessageDialog.showError(dialog, "Pre-Booking Failed", "Failed to schedule pre-booking. Check balance or input.");
                }
            } catch (NumberFormatException ex) {
                MessageDialog.showError(dialog, "Input Error", "Please enter valid numbers for distance and estimated ride time.");
            }
        });

        dialog.setVisible(true);
    }

    /**
     * Displays a dialog for adding balance to the passenger's account.
     */
    private void showAddBalanceDialog() {
        JDialog dialog = new JDialog(mainFrame, "Add Balance", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(mainFrame);

        JTextField amountField = new JTextField(15);
        JButton addButton = new JButton("Add Funds");

        dialog.add(new JLabel("Amount to Add:"));
        dialog.add(amountField);
        dialog.add(addButton);

        addButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    MessageDialog.showError(dialog, "Input Error", "Amount must be positive.");
                    return;
                }
                currentUser = mainFrame.getCurrentPassengerUser();
                if (currentUser != null) {
                    currentUser.addBalance(amount);
                    MessageDialog.showInfo(dialog, "Balance Updated", CURRENCY_FORMAT.format(amount) + " added. New balance: " + CURRENCY_FORMAT.format(currentUser.getBalance()));
                    mainFrame.updatePassengerPanel(); // Refresh balance display on main panel
                    dialog.dispose();
                } else {
                    MessageDialog.showError(dialog, "Error", "No passenger logged in.");
                }
            } catch (NumberFormatException ex) {
                MessageDialog.showError(dialog, "Input Error", "Please enter a valid number for the amount.");
            }
        });
        dialog.setVisible(true);
    }

    /**
     * Displays a dialog for canceling a pre-scheduled ride.
     */
    private void showCancelPreBookingDialog() {
        currentUser = mainFrame.getCurrentPassengerUser();
        if (currentUser == null) {
            MessageDialog.showError(mainFrame, "Error", "No passenger logged in.");
            return;
        }

        List<PreBooking> pendingBookings = preBookingManager.getPassengerPendingPreBookings(currentUser);

        if (pendingBookings.isEmpty()) {
            MessageDialog.showInfo(mainFrame, "No Bookings", "You have no pending pre-scheduled rides to cancel.");
            return;
        }

        String[] bookingOptions = new String[pendingBookings.size()];
        for (int i = 0; i < pendingBookings.size(); i++) {
            PreBooking pb = pendingBookings.get(i);
            bookingOptions[i] = String.format("%d. From %s to %s at %s (Fare: %s)",
                    (i + 1), pb.getPickupLocation(), pb.getDropoffLocation(),
                    pb.getPickupTime(), CURRENCY_FORMAT.format(pb.getEstimatedFare()));
        }

        // Use JComboBox for selection
        JComboBox<String> bookingComboBox = new JComboBox<>(bookingOptions);

        int result = JOptionPane.showConfirmDialog(mainFrame,
                new Object[]{"Select a pre-booking to cancel:", bookingComboBox},
                "Cancel Pre-Scheduled Ride",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = bookingComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < pendingBookings.size()) {
                PreBooking bookingToCancel = pendingBookings.get(selectedIndex);
                boolean success = preBookingManager.cancelPreBooking(currentUser, bookingToCancel);
                if (success) {
                    MessageDialog.showInfo(mainFrame, "Cancellation Success",
                            "Pre-booking from " + bookingToCancel.getPickupLocation() + " to " +
                            bookingToCancel.getDropoffLocation() + " has been cancelled.\n" +
                            "Refunded: " + CURRENCY_FORMAT.format(bookingToCancel.getDeductedAmount()) +
                            ". New balance: " + CURRENCY_FORMAT.format(currentUser.getBalance()));
                    mainFrame.updatePassengerPanel(); // Refresh list and balance
                } else {
                    MessageDialog.showError(mainFrame, "Cancellation Failed",
                            "Could not cancel booking. It might be accepted or already cancelled.");
                }
            } else {
                MessageDialog.showError(mainFrame, "Selection Error", "Invalid selection.");
            }
        }
    }
}
