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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI panel for user registration.
 * Allows new users to register as either a Driver or a Passenger.
 */
public class RegistrationPanel extends JPanel {
    private MainFrame mainFrame;
    private UserManager userManager;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton driverRadio;
    private JRadioButton passengerRadio;
    private ButtonGroup userTypeGroup;
    private JButton registerButton;
    private JButton backToLoginButton;

    /**
     * Constructs a RegistrationPanel.
     * @param mainFrame The main application frame for navigation.
     * @param userManager The UserManager to handle user registration.
     */
    public RegistrationPanel(MainFrame mainFrame, UserManager userManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Register for PickMeGo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Email Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email:"), gbc);

        // Email Field
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Password:"), gbc);

        // Password Field
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // User Type Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Register As:"), gbc);

        // Radio buttons for user type
        driverRadio = new JRadioButton("Driver");
        passengerRadio = new JRadioButton("Passenger");
        passengerRadio.setSelected(true); // Default selection

        userTypeGroup = new ButtonGroup();
        userTypeGroup.add(driverRadio);
        userTypeGroup.add(passengerRadio);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(driverRadio);
        radioPanel.add(passengerRadio);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(radioPanel, gbc);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Back to Login Button
        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backToLoginButton, gbc);

        // Add action listeners
        addListeners();
    }

    /**
     * Adds action listeners to the buttons.
     */
    private void addListeners() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptRegistration();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel(MainFrame.LOGIN_PANEL); // Switch back to login view
            }
        });
    }

    /**
     * Attempts to register a new user based on provided input.
     */
    private void attemptRegistration() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            MessageDialog.showInfo(mainFrame, "Registration Error", "Please enter both email and password.");
            return;
        }

        // Check if user already exists (simple check for demonstration)
        if (userManager.authenticate(email, password) != null) {
            MessageDialog.showError(mainFrame, "Registration Failed", "A user with this email already exists.");
            return;
        }

        if (driverRadio.isSelected()) {
            DriverUser driver = new DriverUser(email, password);
            userManager.addDriver(driver);
            MessageDialog.showInfo(mainFrame, "Registration Success", "Driver registered successfully!");
        } else if (passengerRadio.isSelected()) {
            PassengerUser passenger = new PassengerUser(email, password);
            userManager.addPassenger(passenger);
            MessageDialog.showInfo(mainFrame, "Registration Success", "Passenger registered successfully!");
        } else {
            MessageDialog.showError(mainFrame, "Registration Failed", "Please select a user type (Driver/Passenger).");
            return;
        }

        // Clear fields after successful registration
        emailField.setText("");
        passwordField.setText("");
        passengerRadio.setSelected(true); // Reset to default

        // Redirect to login after successful registration
        mainFrame.showPanel(MainFrame.LOGIN_PANEL);
    }
}
