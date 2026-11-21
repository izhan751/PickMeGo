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
import java.text.DecimalFormat; // For formatting double values

/**
 * GUI panel for Driver functionalities.
 * Provides options for viewing and accepting pre-bookings, and logging out.
 * Displays a list of available pre-bookings.
 */
public class DriverPanel extends JPanel {
    private MainFrame mainFrame;
    private PreBookingManager preBookingManager;
    private SessionManager sessionManager;
    private DriverUser currentUser;

    private JTextArea preBookingsTextArea;
    private JButton viewPreBookingsButton;
    private JButton acceptPreBookingButton;
    private JButton logoutButton;

    // DecimalFormat for currency display
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("Rs#,##0.00");

    /**
     * Constructs a DriverPanel.
     * @param mainFrame The main application frame for navigation.
     * @param preBookingManager The PreBookingManager to handle booking logic.
     * @param sessionManager The SessionManager to get current user info and handle logout.
     */
    public DriverPanel(MainFrame mainFrame, PreBookingManager preBookingManager, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.preBookingManager = preBookingManager;
        this.sessionManager = sessionManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel for User Info
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JLabel welcomeLabel = new JLabel("Welcome, Driver!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        viewPreBookingsButton = new JButton("View Available Pre-Bookings");
        acceptPreBookingButton = new JButton("Accept Pre-Booking");
        logoutButton = new JButton("Logout");

        // Set consistent button sizes/fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(250, 40);

        viewPreBookingsButton.setFont(buttonFont);
        viewPreBookingsButton.setPreferredSize(buttonSize);
        acceptPreBookingButton.setFont(buttonFont);
        acceptPreBookingButton.setPreferredSize(buttonSize);
        logoutButton.setFont(buttonFont);
        logoutButton.setPreferredSize(buttonSize);

        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(viewPreBookingsButton, gbc);
        gbc.gridy = 1; centerPanel.add(acceptPreBookingButton, gbc);
        gbc.gridy = 2; centerPanel.add(logoutButton, gbc);

        add(centerPanel, BorderLayout.WEST);

        // Right Panel for Pre-Bookings List
        JPanel preBookingsListPanel = new JPanel(new BorderLayout());
        preBookingsListPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Pending Pre-Bookings",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        preBookingsTextArea = new JTextArea();
        preBookingsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(preBookingsTextArea);
        preBookingsListPanel.add(scrollPane, BorderLayout.CENTER);

        add(preBookingsListPanel, BorderLayout.CENTER);

        addListeners(); // Attach event listeners
    }

    /**
     * Attaches action listeners to the buttons.
     */
    private void addListeners() {
        viewPreBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPreBookingsDisplay();
            }
        });

        acceptPreBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAcceptPreBookingDialog();
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
     * Refreshes the display of pending pre-bookings in the text area.
     */
    public void refreshPreBookingsDisplay() {
        preBookingsTextArea.setText(""); // Clear existing text
        List<PreBooking> pendingBookings = preBookingManager.getPendingPreBookings();

        if (pendingBookings.isEmpty()) {
            preBookingsTextArea.setText("No pending pre-bookings available.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Index | Pickup Location | Dropoff Location | Pickup Time | Est. Fare (for Passenger)\n");
            sb.append("--------------------------------------------------------------------------------------------------\n");
            for (int i = 0; i < pendingBookings.size(); i++) {
                PreBooking booking = pendingBookings.get(i);
                sb.append(String.format("%-5d | %-15s | %-16s | %-12s | %s\n",
                        (i + 1), booking.getPickupLocation(), booking.getDropoffLocation(),
                        booking.getPickupTime(), CURRENCY_FORMAT.format(booking.getEstimatedFare())));
            }
            preBookingsTextArea.setText(sb.toString());
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) { // When panel becomes visible, refresh data
            currentUser = mainFrame.getCurrentDriverUser(); // Ensure current user is set
            refreshPreBookingsDisplay();
        }
    }

    /**
     * Displays a dialog for accepting a pre-booking.
     */
    private void showAcceptPreBookingDialog() {
        List<PreBooking> pendingBookings = preBookingManager.getPendingPreBookings();

        if (pendingBookings.isEmpty()) {
            MessageDialog.showInfo(mainFrame, "No Bookings", "No pending pre-bookings to accept.");
            return;
        }

        String input = JOptionPane.showInputDialog(mainFrame,
                "Enter the index of the pre-booking to accept:",
                "Accept Pre-Booking",
                JOptionPane.PLAIN_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int index = Integer.parseInt(input.trim());
                if (index >= 1 && index <= pendingBookings.size()) {
                    PreBooking bookingToAccept = pendingBookings.get(index - 1);
                    currentUser = mainFrame.getCurrentDriverUser(); // Ensure current driver is set

                    if (currentUser != null) {
                        boolean success = preBookingManager.acceptPreBooking(currentUser, bookingToAccept);
                        if (success) {
                            MessageDialog.showInfo(mainFrame, "Acceptance Success", "Pre-booking accepted successfully!");
                            refreshPreBookingsDisplay(); // Refresh the list
                            // Optional: Notify passenger if observer pattern is fully integrated for GUI
                            // bookingToAccept.getPassenger().update("Your pre-booking from " + bookingToAccept.getPickupLocation() + " has been accepted by a driver.");
                        } else {
                            MessageDialog.showError(mainFrame, "Acceptance Failed", "Could not accept booking. It might already be accepted or invalid.");
                        }
                    } else {
                        MessageDialog.showError(mainFrame, "Error", "No driver logged in.");
                    }
                } else {
                    MessageDialog.showError(mainFrame, "Input Error", "Invalid index provided.");
                }
            } catch (NumberFormatException ex) {
                MessageDialog.showError(mainFrame, "Input Error", "Please enter a valid number for the index.");
            }
        }
    }
}
