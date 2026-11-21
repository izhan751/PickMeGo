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
 * GUI panel for user login.
 * Allows users to enter their email and password to log into the system.
 * Provides navigation to the registration panel.
 */
public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private UserManager userManager;
    private SessionManager sessionManager;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    /**
     * Constructs a LoginPanel.
     * @param mainFrame The main application frame for navigation.
     * @param userManager The UserManager to handle user authentication.
     * @param sessionManager The SessionManager to manage user sessions.
     */
    public LoginPanel(MainFrame mainFrame, UserManager userManager, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;
        this.sessionManager = sessionManager;

        // Set layout and border for better visual organization
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Login to PickMeGo");
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

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Register Button
        registerButton = new JButton("Don't have an account? Register Here");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Add action listeners
        addListeners();
    }

    /**
     * Adds action listeners to the buttons.
     */
    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel(MainFrame.REGISTRATION_PANEL); // Switch to registration view
            }
        });
    }

    /**
     * Attempts to log in the user with the provided credentials.
     */
    private void attemptLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword()); // Get password as String

        if (email.isEmpty() || password.isEmpty()) {
            MessageDialog.showInfo(mainFrame, "Login Error", "Please enter both email and password.");
            return;
        }

        User user = userManager.authenticate(email, password);
        if (user == null) {
            MessageDialog.showError(mainFrame, "Login Failed", "Invalid credentials. Please try again.");
            // Clear password field for security
            passwordField.setText("");
            return;
        }

        sessionManager.login(user); // Establish session

        MessageDialog.showInfo(mainFrame, "Login Success", "Logged in as: " + user.getEmail());

        // Redirect based on user type
        if (user instanceof DriverUser) {
            mainFrame.updateDriverPanel(); // Refresh driver panel data
            mainFrame.showPanel(MainFrame.DRIVER_PANEL);
        } else if (user instanceof PassengerUser) {
            mainFrame.updatePassengerPanel(); // Refresh passenger panel data
            mainFrame.showPanel(MainFrame.PASSENGER_PANEL);
        } else {
            MessageDialog.showError(mainFrame, "Login Error", "Unknown user type.");
            sessionManager.logout(); // Logout if type is unknown
        }

        // Clear fields after successful login
        emailField.setText("");
        passwordField.setText("");
    }
}
