/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HOTEL FENIX
 */
public class Configurar {
    private String checkIn;
    private String checkOut;
    
    public Configurar(String checkIn, String checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    
    // Getters e Setters
    public String getCheckIn() {
        return checkIn;
    }
    
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }
    
    public String getCheckOut() {
        return checkOut;
    }
    
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}

