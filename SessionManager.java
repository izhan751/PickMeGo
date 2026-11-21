package com.termproject.pickmego;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Login method
    public void login(User user) {
        this.currentUser = user;
        System.out.println("Logged in as: " + user.getEmail());
    }

    // Logout method
    public void logout() {
        if (currentUser != null) {
            System.out.println("Logged out successfully: " + currentUser.getEmail());
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // Get the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Check if a user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
