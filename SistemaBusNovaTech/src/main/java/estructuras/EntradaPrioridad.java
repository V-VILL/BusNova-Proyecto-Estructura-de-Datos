/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author bayde
 */
public class EntradaPrioridad implements Comparable<EntradaPrioridad> {
    private String nombre;
    private double distancia;

    public EntradaPrioridad(String nombre, double distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }

    public String getNombre() { return nombre; }
    public double getDistancia() { return distancia; }

   
    public int compareTo(EntradaPrioridad otra) {
        return Double.compare(this.distancia, otra.distancia);
    }
} 
