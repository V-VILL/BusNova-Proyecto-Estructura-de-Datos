/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Tiquete {
   
    private String nombre;
    private String id;
    private int edad;
    private double monto;
    private String moneda;
    private String horaCompra;
    private String horaAbordaje;
    private String tipoServicio;
    private String tipoBus;
    private int puerta;
    private double montoFinal;
    private double tipoCambio;
    private int prioridad;
    
    
    private String nombreTerminal;   
    private String nombreBus;       
    private String horaAtencion;    
    private String estado;         
    private double montoAdicional;  
    private boolean pagado;        

   
    public Tiquete(String nombre, String id, int edad, double monto, String moneda,
                   String horaCompra, String horaAbordaje, String tipoServicio, String tipoBus,
                   int puerta, double montoFinal, double tipoCambio, int prioridad) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.monto = monto;
        this.moneda = moneda;
        this.horaCompra = horaCompra;
        this.horaAbordaje = horaAbordaje;
        this.tipoServicio = tipoServicio;
        this.tipoBus = tipoBus;
        this.puerta = puerta;
        this.montoFinal = montoFinal;
        this.tipoCambio = tipoCambio;
        this.prioridad = prioridad;
        
      
        this.nombreTerminal = "";
        this.nombreBus = "";
        this.horaAtencion = "NA";
        this.estado = "Pendiente";
        this.montoAdicional = 0.0;
        this.pagado = false;
    }

  
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public int getEdad() { return edad; }
    public double getMonto() { return monto; }
    public String getMoneda() { return moneda; }
    public String getHoraCompra() { return horaCompra; }
    public String getHoraAbordaje() { return horaAbordaje; }
    public void setHoraAbordaje(String hora) { this.horaAbordaje = hora; }
    public String getTipoServicio() { return tipoServicio; }
    public String getTipoBus() { return tipoBus; }
    public int getPuerta() { return puerta; }
    public double getMontoFinal() { return montoFinal; }
    public double getTipoCambio() { return tipoCambio; }
    public int getPrioridad() { return prioridad; }

   
    public String getNombreTerminal() { return nombreTerminal; }
    public void setNombreTerminal(String nombreTerminal) { this.nombreTerminal = nombreTerminal; }
    public String getNombreBus() { return nombreBus; }
    public void setNombreBus(String nombreBus) { this.nombreBus = nombreBus; }
    public String getHoraAtencion() { return horaAtencion; }
    public void setHoraAtencion(String horaAtencion) { this.horaAtencion = horaAtencion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getMontoAdicional() { return montoAdicional; }
    public void setMontoAdicional(double montoAdicional) { this.montoAdicional = montoAdicional; }
    public boolean isPagado() { return pagado; }
    public void setPagado(boolean pagado) { this.pagado = pagado; }

  
    public String serializar() {
        return "{" +
                "\"nombre\":\"" + escaparJson(nombre) + "\"," +
                "\"id\":\"" + id + "\"," +
                "\"edad\":" + edad + "," +
                "\"monto\":" + monto + "," +
                "\"moneda\":\"" + moneda + "\"," +
                "\"horaCompra\":\"" + horaCompra + "\"," +
                "\"horaAbordaje\":\"" + horaAbordaje + "\"," +
                "\"tipoServicio\":\"" + tipoServicio + "\"," +
                "\"tipoBus\":\"" + tipoBus + "\"," +
                "\"puerta\":" + puerta + "," +
                "\"montoFinal\":" + montoFinal + "," +
                "\"tipoCambio\":" + tipoCambio + "," +
                "\"prioridad\":" + prioridad + "," +
                "\"nombreTerminal\":\"" + escaparJson(nombreTerminal) + "\"," +
                "\"nombreBus\":\"" + escaparJson(nombreBus) + "\"," +
                "\"horaAtencion\":\"" + horaAtencion + "\"," +
                "\"estado\":\"" + estado + "\"," +
                "\"montoAdicional\":" + montoAdicional + "," +
                "\"pagado\":" + pagado +
                "}";
    }

    // ✅ Deserializar actualizado con TODOS los campos
    public static Tiquete deserializar(String texto) {
        String nombre = extraerValor(texto, "nombre");
        String id = extraerValor(texto, "id");
        int edad = Integer.parseInt(extraerValor(texto, "edad"));
        double monto = Double.parseDouble(extraerValor(texto, "monto"));
        String moneda = extraerValor(texto, "moneda");
        String horaCompra = extraerValor(texto, "horaCompra");
        String horaAbordaje = extraerValor(texto, "horaAbordaje");
        String tipoServicio = extraerValor(texto, "tipoServicio");
        String tipoBus = extraerValor(texto, "tipoBus");
        int puerta = Integer.parseInt(extraerValor(texto, "puerta"));
        double montoFinal = Double.parseDouble(extraerValor(texto, "montoFinal"));
        double tipoCambio = Double.parseDouble(extraerValor(texto, "tipoCambio"));
        int prioridad = Integer.parseInt(extraerValor(texto, "prioridad"));
        
      
        String nombreTerminal = extraerValor(texto, "nombreTerminal");
        String nombreBus = extraerValor(texto, "nombreBus");
        String horaAtencion = extraerValor(texto, "horaAtencion");
        String estado = extraerValor(texto, "estado");
        String pagadoStr = extraerValor(texto, "pagado");
        double montoAdicional = 0.0;
        boolean pagado = false;
        
        try {
            String montoAdicStr = extraerValor(texto, "montoAdicional");
            if (!montoAdicStr.isEmpty()) montoAdicional = Double.parseDouble(montoAdicStr);
        } catch (Exception e) { montoAdicional = 0.0; }
        
        if (!pagadoStr.isEmpty()) pagado = Boolean.parseBoolean(pagadoStr);

        Tiquete t = new Tiquete(nombre, id, edad, monto, moneda, horaCompra, horaAbordaje,
                tipoServicio, tipoBus, puerta, montoFinal, tipoCambio, prioridad);
        
   
        t.setNombreTerminal(nombreTerminal);
        t.setNombreBus(nombreBus);
        t.setHoraAtencion(horaAtencion.isEmpty() ? "NA" : horaAtencion);
        t.setEstado(estado.isEmpty() ? "Pendiente" : estado);
        t.setMontoAdicional(montoAdicional);
        t.setPagado(pagado);
        
        return t;
    }

    private static String escaparJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
    }

    private static String extraerValor(String texto, String clave) {
        String busqueda = "\"" + clave + "\":";
        int inicio = texto.indexOf(busqueda);
        if (inicio == -1) return "";
        inicio += busqueda.length();

        if (texto.charAt(inicio) == '"') {
            inicio++;
            int fin = texto.indexOf("\"", inicio);
            return fin == -1 ? "" : texto.substring(inicio, fin);
        }

        int fin = texto.indexOf(",", inicio);
        if (fin == -1) fin = texto.indexOf("}", inicio);
        return fin == -1 ? "" : texto.substring(inicio, fin).trim();
    }
} 