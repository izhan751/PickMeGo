package com.termproject.pickmego;

import javax.swing.SwingUtilities;

public class PickMeGo {
    public static void main(String[] args) {
        // Ensure GUI updates are performed on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Create instances of managers
            UserManager userManager = new UserManager();
            PreBookingManager preBookingManager = new PreBookingManager();
            SessionManager sessionManager = SessionManager.getInstance();

            // Create the main application frame
            MainFrame mainFrame = new MainFrame("PickMeGo");

            // Initialize and set panels
            mainFrame.setLoginPanel(new LoginPanel(mainFrame, userManager, sessionManager));
            mainFrame.setRegistrationPanel(new RegistrationPanel(mainFrame, userManager));
            mainFrame.setPassengerPanel(new PassengerPanel(mainFrame, preBookingManager, sessionManager));
            mainFrame.setDriverPanel(new DriverPanel(mainFrame, preBookingManager, sessionManager));

            // Set the initial view to the login panel
            mainFrame.showPanel(MainFrame.LOGIN_PANEL);

            // Make the main frame visible
            mainFrame.setVisible(true);
        });
    }
}
