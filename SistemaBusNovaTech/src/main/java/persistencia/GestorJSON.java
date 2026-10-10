/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.*;
import estructuras.ListaDinamica;
import java.io.*;

public class GestorJSON {

    public static void guardarTexto(String ruta, String contenido) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ruta))) {
            w.write(contenido);
        } catch (IOException e) {
            System.err.println("Error guardando: " + e.getMessage());
        }
    }

    public static String leerTexto(String ruta) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = r.readLine()) != null) {
                sb.append(linea);
            }
        } catch (IOException e) {
            return "";
        }
        return sb.toString();
    }

    public static void guardarConfig(String nombreTerminal, int cantBuses, String p1, String p2) {
        String json = "{" +
            "\"nombreTerminal\":\"" + escaparJson(nombreTerminal) + "\"," +
            "\"cantidadBuses\":" + cantBuses + "," +
            "\"prioridad1\":\"" + escaparJson(p1) + "\"," +
            "\"prioridad2\":\"" + escaparJson(p2) + "\"" +
        "}";
        guardarTexto("config.json", json);
    }

    public static String leerValorConfig(String clave) {
        String json = leerTexto("config.json");
        if (json.isEmpty()) return null;
        
        String busqueda = "\"" + clave + "\":\"";
        int inicio = json.indexOf(busqueda);
        if (inicio == -1) {
            busqueda = "\"" + clave + "\":";
            inicio = json.indexOf(busqueda);
            if (inicio == -1) return null;
            inicio += busqueda.length();
            int fin = json.indexOf(",", inicio);
            if (fin == -1) fin = json.indexOf("}", inicio);
            return json.substring(inicio, fin).trim();
        }
        inicio += busqueda.length();
        int fin = json.indexOf("\"", inicio);
        if (fin == -1) return null;
        return json.substring(inicio, fin);
    }

    public static void guardarUsuarios(ListaDinamica<Usuario> lista) {
        StringBuilder json = new StringBuilder("[");
        if (lista != null && lista.getTamano() > 0) {
            for (int i = 0; i < lista.getTamano(); i++) {
                if (i > 0) json.append(",");
                json.append(lista.get(i).serializar());
            }
        }
        json.append("]");
        guardarTexto("usuarios.json", json.toString());
    }

    public static ListaDinamica<Usuario> cargarUsuarios() {
        ListaDinamica<Usuario> lista = new ListaDinamica<>();
        String json = leerTexto("usuarios.json");
        if (json.isEmpty() || json.equals("[]")) return lista;

        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");
        if (inicio == -1 || fin == -1) return lista;

        String contenido = json.substring(inicio + 1, fin);
        int pos = 0;
        while (pos < contenido.length()) {
            int inicioObj = contenido.indexOf("{", pos);
            if (inicioObj == -1) break;
            int finObj = contenido.indexOf("}", inicioObj);
            if (finObj == -1) break;

            String objJson = contenido.substring(inicioObj, finObj + 1);
            lista.agregar(Usuario.deserializar(objJson));
            pos = finObj + 1;
        }
        return lista;
    }

    public static void guardarTiquetes(ListaDinamica<Tiquete> lista) {
        StringBuilder json = new StringBuilder("[");
        if (lista != null && lista.getTamano() > 0) {
            for (int i = 0; i < lista.getTamano(); i++) {
                if (i > 0) json.append(",");
                json.append(lista.get(i).serializar());
            }
        }
        json.append("]");
        guardarTexto("tiquetes.json", json.toString());
    }

    public static ListaDinamica<Tiquete> cargarTiquetes() {
        ListaDinamica<Tiquete> lista = new ListaDinamica<>();
        String json = leerTexto("tiquetes.json");
        if (json.isEmpty() || json.equals("[]")) return lista;

        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");
        if (inicio == -1 || fin == -1) return lista;

        String contenido = json.substring(inicio + 1, fin);
        int pos = 0;
        while (pos < contenido.length()) {
            int inicioObj = contenido.indexOf("{", pos);
            if (inicioObj == -1) break;
            int finObj = contenido.indexOf("}", inicioObj);
            if (finObj == -1) break;

            String objJson = contenido.substring(inicioObj, finObj + 1);
            lista.agregar(Tiquete.deserializar(objJson));
            pos = finObj + 1;
        }
        return lista;
    }

    public static void guardarAtendidos(ListaDinamica<Tiquete> lista) {
        StringBuilder json = new StringBuilder("[");
        if (lista != null && lista.getTamano() > 0) {
            for (int i = 0; i < lista.getTamano(); i++) {
                if (i > 0) json.append(",");
                json.append(lista.get(i).serializar());
            }
        }
        json.append("]");
        guardarTexto("atendidos.json", json.toString());
    }

    public static ListaDinamica<Tiquete> cargarAtendidos() {
        ListaDinamica<Tiquete> lista = new ListaDinamica<>();
        String json = leerTexto("atendidos.json");
        if (json.isEmpty() || json.equals("[]")) return lista;

        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");
        if (inicio == -1 || fin == -1) return lista;

        String contenido = json.substring(inicio + 1, fin);
        int pos = 0;
        while (pos < contenido.length()) {
            int inicioObj = contenido.indexOf("{", pos);
            if (inicioObj == -1) break;
            int finObj = contenido.indexOf("}", inicioObj);
            if (finObj == -1) break;

            String objJson = contenido.substring(inicioObj, finObj + 1);
            lista.agregar(Tiquete.deserializar(objJson));
            pos = finObj + 1;
        }
        return lista;
    }

    private static String escaparJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
    }
} 