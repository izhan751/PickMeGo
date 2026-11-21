/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.termproject.pickmego;

/**
 *
 * @author SWH
 */

class Ride implements RideSubject {
    private Observer[] observers = new Observer[10];
    private int observerCount = 0;
    private String status;

    public void setStatus(String status) {
        this.status = status;
        notifyObservers(status);
    }

    @Override
    public void registerObserver(Observer o) {
        if (observerCount < observers.length) {
            observers[observerCount++] = o;
        }
    }

    @Override
    public void removeObserver(Observer o) {
        for (int i = 0; i < observerCount; i++) {
            if (observers[i] == o) {
                observers[i] = observers[--observerCount];
                observers[observerCount] = null;
                break;
            }
        }
    }

    @Override
    public void notifyObservers(String status) {
        for (int i = 0; i < observerCount; i++) {
            observers[i].update(status);
        }
    }
}
