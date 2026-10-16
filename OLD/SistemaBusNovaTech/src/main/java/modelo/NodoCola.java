/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author bayde
 */
public class NodoCola {
    private Tiquete tiquete;
    private NodoCola siguiente;
    private int prioridad; 

    public NodoCola(Tiquete t, int prioridad) {
        this.tiquete = t;
        this.prioridad = prioridad;
        this.siguiente = null;
    }

    public Tiquete getTiquete() { return tiquete; }
    public NodoCola getSiguiente() { return siguiente; }
    public void setSiguiente(NodoCola s) { this.siguiente = s; }
    public int getPrioridad() { return prioridad; } 
    
}
