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
 * A utility class to display custom message dialogs (info and error).
 * Replaces System.out.println for user feedback and avoids JOptionPane.showMessageDialog
 * for potentially richer control if needed (though JOptionPane is used internally for simplicity).
 */
public class MessageDialog {

    /**
     * Displays an informational message dialog.
     * @param parentComponent The parent component for the dialog (e.g., the JFrame).
     * @param title The title of the dialog window.
     * @param message The message to display.
     */
    public static void showInfo(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an error message dialog.
     * @param parentComponent The parent component for the dialog (e.g., the JFrame).
     * @param title The title of the dialog window.
     * @param message The error message to display.
     */
    public static void showError(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
