/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.ColaPrioridad;

public class Bus {
    private String nombre;
    private int capacidad;
    private String tipoBus;
    private ColaPrioridad cola;

    public Bus(String nombre, int capacidad, String tipoBus) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipoBus = tipoBus;
        this.cola = new ColaPrioridad(capacidad);
    }

    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public String getTipoBus() { return tipoBus; }
    public ColaPrioridad getCola() { return cola; }

    public boolean agregarTiquete(Tiquete t) {  
        if (cola.estaLlena()) return false;
        cola.encolar(t);
        return true;
    }

    public Tiquete atenderSiguiente() {
        return cola.desencolar();
    }

    public boolean tienePasajeros() {
        return !cola.estaVacia();
    }
} 