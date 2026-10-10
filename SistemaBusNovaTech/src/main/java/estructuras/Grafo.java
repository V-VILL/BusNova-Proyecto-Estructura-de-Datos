/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.Arista;
import modelo.NodoGrafo;

public class Grafo {
    private ListaDinamica<NodoGrafo> nodos;

    public Grafo() {
        nodos = new ListaDinamica<>();
    }

    public void agregarNodo(String nombre) {
        if (buscarNodo(nombre) == null) {
            nodos.agregar(new NodoGrafo(nombre));
        }
    }

    private NodoGrafo buscarNodo(String nombre) {
        for (int i = 0; i < nodos.getTamano(); i++) {
            if (nodos.get(i).getNombre().equals(nombre)) {
                return nodos.get(i);
            }
        }
        return null;
    }

    public void agregarRuta(String origen, String destino, double distancia) {
        NodoGrafo o = buscarNodo(origen);
        NodoGrafo d = buscarNodo(destino);
        if (o != null && d != null) {
            o.agregarArista(new Arista(d, distancia));
        }
    }

    private double buscarDistancia(ListaDinamica<ParClaveValor<String, Double>> lista, String clave) {
        for (int i = 0; i < lista.getTamano(); i++) {
            if (lista.get(i).getClave().equals(clave)) {
                return lista.get(i).getValor();
            }
        }
        return Double.MAX_VALUE;
    }

    private void guardarDistancia(ListaDinamica<ParClaveValor<String, Double>> lista, String clave, double valor) {
        for (int i = 0; i < lista.getTamano(); i++) {
            if (lista.get(i).getClave().equals(clave)) {
                lista.get(i).setValor(valor);
                return;
            }
        }
        lista.agregar(new ParClaveValor<>(clave, valor));
    }

    private String buscarPredecesor(ListaDinamica<ParClaveValor<String, String>> lista, String clave) {
        for (int i = 0; i < lista.getTamano(); i++) {
            if (lista.get(i).getClave().equals(clave)) {
                return lista.get(i).getValor();
            }
        }
        return null;
    }

    private boolean yaVisitado(ListaDinamica<String> visitados, String nombre) {
        for (int i = 0; i < visitados.getTamano(); i++) {
            if (visitados.get(i).equals(nombre)) {
                return true;
            }
        }
        return false;
    }
 
    public String buscarRutaMasCorta(String origen, String destino) {
        NodoGrafo ini = buscarNodo(origen);
        NodoGrafo fin = buscarNodo(destino);
        if (ini == null || fin == null) return "No existe origen o destino";

        ListaDinamica<ParClaveValor<String, Double>> dist = new ListaDinamica<>();
        ListaDinamica<ParClaveValor<String, String>> ant = new ListaDinamica<>();
        ListaDinamica<String> visitados = new ListaDinamica<>();
        ColaPrioridad colaPrioridad = new ColaPrioridad();

        for (int i = 0; i < nodos.getTamano(); i++) {
            guardarDistancia(dist, nodos.get(i).getNombre(), Double.MAX_VALUE);
        }
        guardarDistancia(dist, origen, 0.0);
        colaPrioridad.insertar(origen, 0.0);

        while (!colaPrioridad.estaVaciaDijkstra()) {
            EntradaPrioridad actual = colaPrioridad.extraerMinimo();
            String actNombre = actual.getNombre();

            if (yaVisitado(visitados, actNombre)) continue;
            visitados.agregar(actNombre);

            if (actNombre.equals(destino)) break;

            NodoGrafo nAct = buscarNodo(actNombre);
            for (int i = 0; i < nAct.getAdyacentes().getTamano(); i++) {
                Arista a = nAct.getAdyacentes().get(i);
                String sig = a.getDestino().getNombre();
                double distActual = buscarDistancia(dist, actNombre);
                double distSig = buscarDistancia(dist, sig);
                double nuevaDist = distActual + a.getDistancia();

                if (nuevaDist < distSig) {
                    guardarDistancia(dist, sig, nuevaDist);
                    
                    boolean encontrado = false;
                    for (int j = 0; j < ant.getTamano(); j++) {
                        if (ant.get(j).getClave().equals(sig)) {
                            ant.get(j).setValor(actNombre);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        ant.agregar(new ParClaveValor<>(sig, actNombre));
                    }
                    
                    colaPrioridad.insertar(sig, nuevaDist);
                }
            }
        }

        if (buscarDistancia(dist, destino) == Double.MAX_VALUE) {
            return "No hay ruta disponible";
        }

        StringBuilder camino = new StringBuilder(destino);
        String paso = destino;
        String anterior;
        while ((anterior = buscarPredecesor(ant, paso)) != null) {
            paso = anterior;
            camino.insert(0, paso + " -> ");
        }
        camino.append(" | Distancia: ").append(buscarDistancia(dist, destino));
        return camino.toString();
    }

    public String listarTodo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodos.getTamano(); i++) {
            NodoGrafo n = nodos.get(i);
            sb.append(n.getNombre()).append(" conecta con: ");
            for (int j = 0; j < n.getAdyacentes().getTamano(); j++) {
                Arista a = n.getAdyacentes().get(j);
                sb.append(a.getDestino().getNombre())
                  .append("(").append(a.getDistancia()).append("km) ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String serializar() {
        return listarTodo();
    }
} 
