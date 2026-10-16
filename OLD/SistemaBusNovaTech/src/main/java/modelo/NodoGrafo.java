/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.ListaDinamica;

public class NodoGrafo {
    private String nombre;
    private ListaDinamica<Arista> adyacentes;

    public NodoGrafo(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ListaDinamica<>();
    }

    public String getNombre() { return nombre; }
    public ListaDinamica<Arista> getAdyacentes() { return adyacentes; }

    public void agregarArista(Arista a) {
        adyacentes.agregar(a);
    }
} 

