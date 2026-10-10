/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.NodoCola;
import modelo.Tiquete;

public class ColaPrioridad {
    
   private NodoCola frente;
    private int tamano;
    private int capacidadMax;
    
    public ColaPrioridad(int capacidad) {
        this.capacidadMax = capacidad;
        this.frente = null;
        this.tamano = 0;
    }
    
    public ColaPrioridad() {
        this.capacidadMax = Integer.MAX_VALUE;
        this.frente = null;
        this.tamano = 0;
    }

    public boolean estaVacia() { return frente == null; }
    public boolean estaLlena() { return tamano >= capacidadMax; }
    public int getTamano() { return tamano; }
    
    private int obtenerNivelPrioridad(String tipo) {
        switch (tipo.toUpperCase()) {
            case "VIP": return 3;
            case "EJECUTIVO": return 2;
            case "REGULAR": return 1;
            default: return 1;
        }
    }
    
    public void encolar(Tiquete t) {
        if (estaLlena()) return;
        int prio = obtenerNivelPrioridad(t.getTipoServicio());
        NodoCola nuevo = new NodoCola(t, prio);
        if (estaVacia() || prio > frente.getPrioridad()) {
            nuevo.setSiguiente(frente);
            frente = nuevo;
        } else {
            NodoCola actual = frente;
            while (actual.getSiguiente() != null &&
                   actual.getSiguiente().getPrioridad() >= prio) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        tamano++;
    }
    
    public Tiquete desencolar() {
        if (estaVacia()) return null;
        Tiquete t = frente.getTiquete();
        frente = frente.getSiguiente();
        tamano--;
        return t;
    }
    
    private NodoColaDijkstra frenteDijkstra;
    
    private static class NodoColaDijkstra {
        EntradaPrioridad dato;
        NodoColaDijkstra siguiente;
        NodoColaDijkstra(EntradaPrioridad d) {
            dato = d;
            siguiente = null;
        }
    }
    
    public void insertar(String nombre, double valor) {
        EntradaPrioridad entrada = new EntradaPrioridad(nombre, valor);
        NodoColaDijkstra nuevo = new NodoColaDijkstra(entrada);
        
        if (frenteDijkstra == null || entrada.getDistancia() < frenteDijkstra.dato.getDistancia()) {
            nuevo.siguiente = frenteDijkstra;
            frenteDijkstra = nuevo;
        } else {
            NodoColaDijkstra actual = frenteDijkstra;
            while (actual.siguiente != null && 
                   actual.siguiente.dato.getDistancia() <= entrada.getDistancia()) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }
    
    public EntradaPrioridad extraerMinimo() {
        if (frenteDijkstra == null) return null;
        EntradaPrioridad min = frenteDijkstra.dato;
        frenteDijkstra = frenteDijkstra.siguiente;
        return min;
    }
    
    public boolean estaVaciaDijkstra() {
        return frenteDijkstra == null;
    }
    
    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        NodoCola aux = frente;
        while (aux != null) {
            sb.append(aux.getTiquete().getId())
              .append(" - ").append(aux.getTiquete().getTipoServicio())
              .append("\n");
            aux = aux.getSiguiente();
        }
        return sb.toString();
    }
} 