
 
package estructuras;
import modelo.NodoLista;

/**
 *
 * @author bayde
 */
public class ListaDinamica<T> {
    private NodoLista<T> cabeza;
    private int tamano;

    public ListaDinamica() {
        cabeza = null;
        tamano = 0;
    }

    public boolean estaVacia() { return cabeza == null; }
    public int getTamano() { return tamano; }

    public void agregar(T elemento) {
        NodoLista<T> nuevo = new NodoLista<>(elemento);
        if (estaVacia()) {
            cabeza = nuevo;
        } else {
            NodoLista<T> aux = cabeza;
            while (aux.getSiguiente() != null) aux = aux.getSiguiente();
            aux.setSiguiente(nuevo);
        }
        tamano++;
    }

    public T get(int indice) {
        if (indice < 0 || indice >= tamano) return null;
        NodoLista<T> aux = cabeza;
        for (int i = 0; i < indice; i++) aux = aux.getSiguiente();
        return aux.getDato();
    }

    public void eliminar(int indice) {
        if (indice < 0 || indice >= tamano) return;
        if (indice == 0) {
            cabeza = cabeza.getSiguiente();
        } else {
            NodoLista<T> aux = cabeza;
            for (int i = 0; i < indice - 1; i++) aux = aux.getSiguiente();
            aux.setSiguiente(aux.getSiguiente().getSiguiente());
        }
        tamano--;
    }
}

