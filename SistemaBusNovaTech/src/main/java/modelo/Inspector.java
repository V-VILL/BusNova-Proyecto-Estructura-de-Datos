/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author bayde
 */
public class Inspector {
    private String nombre;
    private boolean turnoActivo;
    private boolean ocupado;  

    public Inspector(String nombre) {
        this.nombre = nombre;
        this.turnoActivo = false;
        this.ocupado = false;  
    }

    public String getNombre() { return nombre; }
    
    public boolean isTurnoActivo() { return turnoActivo; }
    public void setTurnoActivo(boolean t) { this.turnoActivo = t; }

   
    public boolean estaOcupado() { return ocupado; }
    public void setOcupado(boolean valor) { this.ocupado = valor; }
    
    public boolean estaLibre() { return !ocupado; } 

    public String serializar() {
        return "{" +
                "\"nombre\":\"" + nombre + "\"," +
                "\"turnoActivo\":" + turnoActivo + "," +
                "\"ocupado\":" + ocupado + 
                "}";
    }

    public static Inspector deserializar(String json) {
        String n = extraerStr(json, "nombre");
        boolean t = extraerBool(json, "turnoActivo");
        boolean o = extraerBool(json, "ocupado"); 
        Inspector ins = new Inspector(n);
        ins.setTurnoActivo(t);
        ins.setOcupado(o);
        return ins;
    }

    private static String extraerStr(String j, String c) {
        String b = "\"" + c + "\":\"";
        int inicio = j.indexOf(b) + b.length();
        int fin = j.indexOf("\"", inicio);
        return j.substring(inicio, fin);
    }
    
    private static boolean extraerBool(String j, String c) {
        String b = "\"" + c + "\":";
        int i = j.indexOf(b) + b.length();
        int f = j.indexOf(",", i);
        if (f == -1) f = j.indexOf("}", i);
        return Boolean.parseBoolean(j.substring(i, f).trim());
    }
} 
