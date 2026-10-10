/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Arista {
    
    private NodoGrafo destino;
    private double distancia;

    public Arista(NodoGrafo destino, double distancia) {
        this.destino = destino;
        this.distancia = distancia;
    }

    public NodoGrafo getDestino() { return destino; }
    public double getDistancia() { return distancia; }
} 
