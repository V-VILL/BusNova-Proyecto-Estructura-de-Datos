/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author bayde
 */
public class Usuario {
    
    private String nombre;
    private String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }

    public String serializar() {
        return "{\"nombre\":\"" + nombre + "\",\"contrasena\":\"" + contrasena + "\"}";
    }

    public static Usuario deserializar(String json) {
        String n = extraerValor(json, "nombre");
        String c = extraerValor(json, "contrasena");
        return new Usuario(n, c);
    }

    private static String extraerValor(String json, String clave) {
        String buscar = "\"" + clave + "\":\"";
        int i = json.indexOf(buscar) + buscar.length();
        int f = json.indexOf("\"", i);
        return json.substring(i, f);
    }
}

