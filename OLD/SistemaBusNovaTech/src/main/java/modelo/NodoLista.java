/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author bayde
 */
public class NodoLista<T> {
    private T dato;
    private NodoLista<T> siguiente;

    public NodoLista(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() { return dato; }
    public NodoLista<T> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoLista<T> s) { siguiente = s; }
}

