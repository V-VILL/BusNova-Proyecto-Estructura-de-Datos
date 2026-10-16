/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemabusnovatech;

import modelo.*;
import estructuras.*;
import persistencia.GestorJSON;
import servicios.ServicioBCCR;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SistemaBusNovaTech {
    private static String nombreTerminal;
    private static ListaDinamica<Bus> buses;
    private static ListaDinamica<Usuario> usuarios;
    private static ListaDinamica<Tiquete> tiquetesPendientes;
    private static ListaDinamica<Tiquete> tiquetesAtendidos;
    private static Inspector inspector;
    private static Grafo rutas;
    private static ServicioBCCR servicioCambio;
    private static Usuario usuarioActivo;
    private static double tipoCambioActual;
    private static String prioridad1;
    private static String prioridad2;

    private static final double CAMBIO_RESPALDO = 515.75;

    public static void main(String[] args) {
        buses = new ListaDinamica<>();
        usuarios = new ListaDinamica<>();
        tiquetesPendientes = new ListaDinamica<>();
        tiquetesAtendidos = new ListaDinamica<>();
        rutas = new Grafo();
        servicioCambio = new ServicioBCCR("INGRESA_TU_TOKEN_AQUI");
        tipoCambioActual = CAMBIO_RESPALDO;

        if (!cargarConfiguracion()) {
            configurarSistema();
        }
        cargarDatosGuardados();

        if (!iniciarSesion()) {
            JOptionPane.showMessageDialog(null, "Acceso denegado");
            return;
        }

        menuPrincipal();
        guardarTodo();
    }

    private static boolean cargarConfiguracion() {
        nombreTerminal = GestorJSON.leerValorConfig("nombreTerminal");
        String cantBusesTexto = GestorJSON.leerValorConfig("cantidadBuses");
        prioridad1 = GestorJSON.leerValorConfig("prioridad1");
        prioridad2 = GestorJSON.leerValorConfig("prioridad2");
        
        if (nombreTerminal == null || cantBusesTexto == null || nombreTerminal.isEmpty()) {
            return false;
        }

        int cantidadBuses = Integer.parseInt(cantBusesTexto);
        for (int i = 1; i <= cantidadBuses; i++) {
            String tipoBus;
            if (i == 1) {
                tipoBus = "preferencial";
            } else if (i == 2) {
                tipoBus = "directo";
            } else {
                tipoBus = "normal";
            }
            int capacidad = calcularCapacidadBus(i);
            buses.agregar(new Bus("Bus " + i, capacidad, tipoBus));
        }
        return true;
    }

    private static void configurarSistema() {
        nombreTerminal = JOptionPane.showInputDialog("Nombre de la terminal:");
        if (nombreTerminal == null) System.exit(0);

        int cantidadBuses;
        do {
            try {
                String entrada = JOptionPane.showInputDialog("Cantidad de buses (mínimo 2):");
                cantidadBuses = Integer.parseInt(entrada.trim());
            } catch (Exception e) {
                cantidadBuses = -1;
            }
        } while (cantidadBuses < 2);

        prioridad1 = JOptionPane.showInputDialog("Grupo prioritario 1:\n(discapacidad / adultos_mayores / embarazo / ninguno)");
        prioridad2 = JOptionPane.showInputDialog("Grupo prioritario 2:\n(discapacidad / adultos_mayores / embarazo / ninguno)");

        for (int i = 1; i <= cantidadBuses; i++) {
            String tipoBus;
            if (i == 1) {
                tipoBus = "preferencial";
            } else if (i == 2) {
                tipoBus = "directo";
            } else {
                tipoBus = "normal";
            }
            int capacidad = calcularCapacidadBus(i);
            buses.agregar(new Bus("Bus " + i, capacidad, tipoBus));
        }

        GestorJSON.guardarConfig(nombreTerminal, cantidadBuses, prioridad1, prioridad2);
        JOptionPane.showMessageDialog(null,
                "Configuracion guardada:\n" +
                "Terminal: " + nombreTerminal + "\n" +
                "Buses totales: " + cantidadBuses + "\n" +
                "Bus 1: PREFERENCIAL\n" +
                "Bus 2: DIRECTO\n" +
                "Resto: NORMALES\n" +
                "Prioridad 1: " + prioridad1 + "\n" +
                "Prioridad 2: " + prioridad2);
    }

    private static int calcularCapacidadBus(int numeroBus) {
        if (numeroBus == 1) return 5;
        if (numeroBus == 5) return 3;
        return 4;
    }

    private static void cargarDatosGuardados() {
        usuarios = GestorJSON.cargarUsuarios();
        tiquetesPendientes = GestorJSON.cargarTiquetes();
        tiquetesAtendidos = GestorJSON.cargarAtendidos();

        for (int i = 0; i < tiquetesPendientes.getTamano(); i++) {
            Tiquete t = tiquetesPendientes.get(i);
            String codigoBus = t.getTipoBus();
            
            for (int j = 0; j < buses.getTamano(); j++) {
                Bus b = buses.get(j);
                String codigoBusActual;
                if (b.getTipoBus().equals("preferencial")) codigoBusActual = "P";
                else if (b.getTipoBus().equals("directo")) codigoBusActual = "D";
                else codigoBusActual = "N";
                
                if (codigoBus.equals(codigoBusActual) && b.getCola().getTamano() < b.getCapacidad()) {
                    b.getCola().encolar(t);
                    break;
                }
            }
        }

        if (usuarios.getTamano() == 0) {
            usuarios.agregar(new Usuario("Victor", "192"));
            GestorJSON.guardarUsuarios(usuarios);
        }

        inicializarGrafoRutas();
        inspector = new Inspector("Inspector 1");
    }

    private static void inicializarGrafoRutas() {
        rutas.agregarNodo(nombreTerminal);
        rutas.agregarNodo("San Jose");
        rutas.agregarNodo("Alajuela");
        rutas.agregarNodo("Cartago");
        rutas.agregarNodo("Heredia");

        rutas.agregarRuta(nombreTerminal, "San Jose", 15);
        rutas.agregarRuta(nombreTerminal, "Heredia", 10);
        rutas.agregarRuta("San Jose", "Alajuela", 20);
        rutas.agregarRuta("San Jose", "Cartago", 25);

        rutas.agregarRuta("San Jose", nombreTerminal, 15);
        rutas.agregarRuta("Heredia", nombreTerminal, 10);
        rutas.agregarRuta("Alajuela", "San Jose", 20);
        rutas.agregarRuta("Cartago", "San Jose", 25);

        rutas.agregarRuta("Alajuela", "Heredia", 12);
        rutas.agregarRuta("Heredia", "Alajuela", 12);
        rutas.agregarRuta("Cartago", "Heredia", 30);
        rutas.agregarRuta("Heredia", "Cartago", 30);
    }

    private static boolean iniciarSesion() {
        for (int intento = 0; intento < 3; intento++) {
            String nombre = JOptionPane.showInputDialog("Usuario:");
            if (nombre == null) return false;

            String clave = JOptionPane.showInputDialog("Contrasena:");
            if (clave == null) return false;

            for (int i = 0; i < usuarios.getTamano(); i++) {
                Usuario u = usuarios.get(i);
                if (u.getNombre().equals(nombre) && u.getContrasena().equals(clave)) {
                    usuarioActivo = u;
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas\nIntento " + (intento + 1) + " de 3");
        }
        return false;
    }

    private static void menuPrincipal() {
        int opcion;
        do {
            String listaBuses = "\n--- Buses ---\n";
            for (int i = 0; i < buses.getTamano(); i++) {
                Bus b = buses.get(i);
                listaBuses += b.getNombre() + " - " + b.getTipoBus().toUpperCase() + "\n";
            }
            
            String menu = "=== " + nombreTerminal + " ===\n" +
                          "Usuario: " + usuarioActivo.getNombre() + "\n" +
                          listaBuses + "\n" +
                          "1. Crear tiquete\n" +
                          "2. Atender tiquetes (Inspector)\n" +
                          "3. Ver colas de buses\n" +
                          "4. Consultar rutas y grafos\n" +
                          "5. Tipo de cambio BCCR\n" +
                          "6. Ver preferencias de la terminal\n" +
                          "7. Ver tiquetes atendidos\n" +
                          "8. Salir";
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));
            } catch (Exception e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1: crearTiquete(); break;
                case 2: atenderTiquetes(); break;
                case 3: verColas(); break;
                case 4: menuGrafos(); break;
                case 5: consultarBCCR(); break;
                case 6: verPreferencias(); break;
                case 7: verTiquetesAtendidos(); break;
                case 8: return;
                default: JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (true);
    }

    private static void verPreferencias() {
        JOptionPane.showMessageDialog(null,
            "=== Preferencias de la Terminal ===\n" +
            "Prioridad 1: " + prioridad1 + "\n" +
            "Prioridad 2: " + prioridad2);
    }

    private static void verTiquetesAtendidos() {
        if (tiquetesAtendidos.getTamano() == 0) {
            JOptionPane.showMessageDialog(null, "No hay tiquetes atendidos registrados");
            return;
        }
        
        StringBuilder lista = new StringBuilder("=== HISTORIAL - TIQUETES ATENDIDOS ===\n\n");
        for (int i = 0; i < tiquetesAtendidos.getTamano(); i++) {
            Tiquete t = tiquetesAtendidos.get(i);
            lista.append("ID: ").append(t.getId())
                 .append("\nCliente: ").append(t.getNombre())
                 .append("\nBus: ").append(t.getNombreBus())
                 .append("\nTerminal: ").append(t.getNombreTerminal())
                 .append("\nHora atencion: ").append(t.getHoraAtencion())
                 .append("\nServicio: ").append(t.getTipoServicio())
                 .append("\n----------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString());
    }

    private static void crearTiquete() {
        try {
            String nombreCliente = JOptionPane.showInputDialog("Nombre del cliente:");
            if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre es requerido");
                return;
            }
            
            String id = "T" + (tiquetesPendientes.getTamano() + 1);
            int edad = Integer.parseInt(JOptionPane.showInputDialog("Edad:"));
            double monto = Double.parseDouble(JOptionPane.showInputDialog("Monto a pagar:"));
            String moneda = JOptionPane.showInputDialog("Moneda (COL/DOL):");
            String horaCompra = new SimpleDateFormat("HH:mm").format(new Date());
            String horaAbordaje = "NA";
            String tipoServicio = JOptionPane.showInputDialog("Tipo servicio:\nVIP / Ejecutivo / Regular / Carga");
            
            String paradasTexto = JOptionPane.showInputDialog("Cuantas paradas tiene el recorrido?\nEscribe 1 = una sola parada\nEscribe 2 o mas = varias paradas");
            int cantidadParadas = Integer.parseInt(paradasTexto.trim());
            
            Bus busAsignado = null;
            String mensajeBus = "";
            String tipoBusCodigo = "";
            
            Bus busPreferencial = null;
            Bus busDirecto = null;
            
            for (int i = 0; i < buses.getTamano(); i++) {
                Bus b = buses.get(i);
                if (b.getTipoBus().equals("preferencial")) busPreferencial = b;
                else if (b.getTipoBus().equals("directo")) busDirecto = b;
            }
            
            boolean esGrupoPrioritario = (edad >= 65 || edad <= 10 || "VIP".equalsIgnoreCase(tipoServicio));
            boolean esViajeUnaParada = (cantidadParadas == 1);
            
            if (esGrupoPrioritario && busPreferencial != null && busPreferencial.getCola().getTamano() < busPreferencial.getCapacidad()) {
                busAsignado = busPreferencial;
                tipoBusCodigo = "P";
                mensajeBus = "Persona prioritaria -> Asignado al BUS PREFERENCIAL (P)";
            } 
            else if (esViajeUnaParada && !esGrupoPrioritario && busDirecto != null && busDirecto.getCola().getTamano() < busDirecto.getCapacidad()) {
                busAsignado = busDirecto;
                tipoBusCodigo = "D";
                mensajeBus = "Viaje rapido (1 parada) -> Asignado al BUS DIRECTO (D)";
            }
            else {
                busAsignado = buscarBusConMenosGente();
                if (busAsignado != null) {
                    if (busAsignado.getTipoBus().equals("preferencial")) tipoBusCodigo = "P";
                    else if (busAsignado.getTipoBus().equals("directo")) tipoBusCodigo = "D";
                    else tipoBusCodigo = "N";
                    mensajeBus = "Bus preferido lleno -> Asignado a: " + busAsignado.getNombre() + " (" + tipoBusCodigo + ")";
                }
            }
            
            if (busAsignado == null) {
                JOptionPane.showMessageDialog(null, "Todos los buses estan llenos");
                return;
            }
            
            int puerta = Integer.parseInt(JOptionPane.showInputDialog("Puerta de embarque (1-4):"));
            int prioridad = calcularPrioridad(edad, tipoServicio);
            double montoFinal = monto;
            if ("DOL".equalsIgnoreCase(moneda)) {
                montoFinal = monto * tipoCambioActual;
            }
            
            String confirmacion = mensajeBus + "\n\n" +
                    "Nombre: " + nombreCliente + "\n" +
                    "ID Tiquete: " + id + "\n" +
                    "Edad: " + edad + "\n" +
                    "Paradas: " + cantidadParadas + "\n" +
                    "Tipo Bus: " + tipoBusCodigo + "\n" +
                    "Monto final: " + montoFinal + "\n\n" +
                    "Confirmar tiquete?";
            
            int confirmar = JOptionPane.showConfirmDialog(null, confirmacion, "Confirmar tiquete", JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Operacion cancelada");
                return;
            }
            
            Tiquete tiquete = new Tiquete(
                nombreCliente,
                id,
                edad,
                monto,
                moneda,
                horaCompra,
                horaAbordaje,
                tipoServicio,
                tipoBusCodigo,
                puerta,
                montoFinal,
                tipoCambioActual,
                prioridad
            );
            tiquete.setNombreTerminal(nombreTerminal);
            
            tiquetesPendientes.agregar(tiquete);
            busAsignado.agregarTiquete(tiquete);

            JOptionPane.showMessageDialog(null,
                    "Tiquete creado exitosamente:\n" +
                    "Nombre: " + nombreCliente + "\n" +
                    "ID: " + id + "\n" +
                    "Terminal: " + nombreTerminal + "\n" +
                    "Hora compra: " + horaCompra + "\n" +
                    "Hora abordaje: " + horaAbordaje + "\n" +
                    "Bus: " + busAsignado.getNombre() + " (" + tipoBusCodigo + ")\n" +
                    "Monto final: " + montoFinal);
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos invalidos: " + e.getMessage());
        }
    } 

    private static int calcularPrioridad(int edad, String tipoServicio) {
        if ("VIP".equalsIgnoreCase(tipoServicio) || edad >= 65 || edad <= 10) {
            return 1;
        }
        if ("Ejecutivo".equalsIgnoreCase(tipoServicio)) {
            return 2;
        }
        return 3;
    }

    private static Bus buscarBusConMenosGente() {
        Bus mejorOpcion = null;
        int menorCantidad = 999;

        for (int i = 0; i < buses.getTamano(); i++) {
            Bus bus = buses.get(i);
            int ocupacion = bus.getCola().getTamano();
            if (ocupacion < bus.getCapacidad() && ocupacion < menorCantidad) {
                menorCantidad = ocupacion;
                mejorOpcion = bus;
            }
        }
        return mejorOpcion;
    }

    private static void atenderTiquetes() {
        StringBuilder estado = new StringBuilder("=== ESTADO DE COLAS ===\n");
        for (int i = 0; i < buses.getTamano(); i++) {
            Bus b = buses.get(i);
            estado.append(b.getNombre() + " (" + b.getTipoBus().toUpperCase() + "): ")
                  .append(b.getCola().getTamano())
                  .append(" pasajeros\n");
        }

        String respuesta = JOptionPane.showInputDialog(estado + "\nAtender siguiente? (s/n)");
        if (!"s".equalsIgnoreCase(respuesta)) {
            return;
        }

        Bus busConPasajeros = null;
        Tiquete tiqueteActual = null;
        
        for (int i = 0; i < buses.getTamano(); i++) {
            Bus b = buses.get(i);
            if (b.tienePasajeros()) {
                busConPasajeros = b;
                tiqueteActual = b.atenderSiguiente();
                break;
            }
        }

        if (tiqueteActual == null) {
            JOptionPane.showMessageDialog(null, "No hay pasajeros pendientes por atender");
            return;
        }

        int abordar = JOptionPane.showConfirmDialog(null,
            "=== ABORDAR TIQUETE ===\n" +
            "Cliente: " + tiqueteActual.getNombre() + "\n" +
            "ID: " + tiqueteActual.getId() + "\n" +
            "Bus: " + busConPasajeros.getNombre() + "\n" +
            "Servicio: " + tiqueteActual.getTipoServicio() + "\n\n" +
            "Abordar pasajero?",
            "Confirmar abordaje",
            JOptionPane.YES_NO_OPTION);
        
        if (abordar != JOptionPane.YES_OPTION) {
            busConPasajeros.getCola().encolar(tiqueteActual);
            JOptionPane.showMessageDialog(null, "El pasajero regreso a la fila");
            return;
        }

        double adicional = 0;
        String servicio = tiqueteActual.getTipoServicio().toUpperCase();
        String detalleCarga = "";
        
        switch (servicio) {
            case "VIP":
                adicional = 100.00;
                break;
            case "REGULAR":
                adicional = 20.00;
                break;
            case "CARGA":
                try {
                    double libras = Double.parseDouble(JOptionPane.showInputDialog(
                        "Ingrese el peso en libras de la carga:"));
                    adicional = 20.00 + (10.00 * libras);
                    detalleCarga = "\nPeso: " + libras + " lb";
                } catch (Exception e) {
                    adicional = 20.00;
                    detalleCarga = "\nPeso no especificado";
                }
                break;
            case "EJECUTIVO":
                adicional = 1000.00;
                break;
        }
        tiqueteActual.setMontoAdicional(adicional);

        int paga = JOptionPane.showConfirmDialog(null,
            "=== COBRO DE SERVICIO ===\n" +
            "Cliente: " + tiqueteActual.getNombre() + "\n" +
            "Tipo de servicio: " + servicio + detalleCarga + "\n" +
            "Monto adicional a pagar: " + String.format("%.2f", adicional) + "\n\n" +
            "El cliente paga el servicio?",
            "Verificar pago",
            JOptionPane.YES_NO_OPTION);
        
        if (paga != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                "El cliente se nego a pagar.\n" +
                "Se retira de la fila del " + busConPasajeros.getNombre() + "\n" +
                "Inicie de nuevo el proceso de atencion.");
            return;
        }

        String horaAtencion = new SimpleDateFormat("HH:mm").format(new Date());
        tiqueteActual.setHoraAtencion(horaAtencion);
        tiqueteActual.setHoraAbordaje(horaAtencion);
        tiqueteActual.setNombreBus(busConPasajeros.getNombre());
        tiqueteActual.setNombreTerminal(nombreTerminal);
        tiqueteActual.setEstado("Atendido");
        tiqueteActual.setPagado(true);
        
        int posicion = -1;
        for (int i = 0; i < tiquetesPendientes.getTamano(); i++) {
            if (tiquetesPendientes.get(i).getId().equals(tiqueteActual.getId())) {
                posicion = i;
                break;
            }
        }
        if (posicion != -1) {
            tiquetesPendientes.eliminar(posicion);
        }
        tiquetesAtendidos.agregar(tiqueteActual);
        
        GestorJSON.guardarTiquetes(tiquetesPendientes);
        GestorJSON.guardarAtendidos(tiquetesAtendidos);
        
        JOptionPane.showMessageDialog(null,
            "TIQUETE ATENDIDO Y GUARDADO\n\n" +
            "Cliente: " + tiqueteActual.getNombre() + "\n" +
            "ID: " + tiqueteActual.getId() + "\n" +
            "Terminal: " + tiqueteActual.getNombreTerminal() + "\n" +
            "Bus: " + tiqueteActual.getNombreBus() + "\n" +
            "Hora de atencion: " + tiqueteActual.getHoraAtencion() + "\n" +
            "Servicio: " + servicio + "\n" +
            "Monto adicional pagado: " + String.format("%.2f", adicional) + "\n\n" +
            "Guardado en: atendidos.json");
    }

    private static void verColas() {
        StringBuilder listado = new StringBuilder("=== COLAS DE BUSES ===\n");
        for (int i = 0; i < buses.getTamano(); i++) {
            Bus b = buses.get(i);
            listado.append("\n" + b.getNombre() + " (" + b.getTipoBus().toUpperCase() + "): ")
                   .append(b.getCola().getTamano())
                   .append("/").append(b.getCapacidad()).append("\n")
                   .append(b.getCola().listarTodos());
        }
        JOptionPane.showMessageDialog(null, listado.toString());
    }

    private static void menuGrafos() {
        String opcionTexto = JOptionPane.showInputDialog(
                "=== RUTAS ===\n" +
                "1. Ver todas las rutas\n" +
                "2. Buscar ruta mas corta\n\nIngresa opcion:");
        if (opcionTexto == null) return;

        try {
            int opcion = Integer.parseInt(opcionTexto.trim());
            if (opcion == 1) {
                JOptionPane.showMessageDialog(null, rutas.listarTodo());
            } else if (opcion == 2) {
                String origen = JOptionPane.showInputDialog("Origen:");
                String destino = JOptionPane.showInputDialog("Destino:");
                String resultado = rutas.buscarRutaMasCorta(origen, destino);
                JOptionPane.showMessageDialog(null, resultado);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opcion invalida");
        }
    }

    private static void consultarBCCR() {
        String mensaje = "Tipo de cambio actual:\n" +
                         "Venta: " + tipoCambioActual + "\n\n" +
                         "Ingresa token del BCCR para valor real (opcional):";
        String token = JOptionPane.showInputDialog(mensaje);

        if (token != null && !token.trim().isEmpty()) {
            servicioCambio = new ServicioBCCR(token.trim());
            double valorReal = servicioCambio.obtenerTipoCambioVenta();
            if (valorReal != CAMBIO_RESPALDO) {
                tipoCambioActual = valorReal;
                JOptionPane.showMessageDialog(null,
                        "Actualizado:\n" +
                        "Dolar venta: " + tipoCambioActual);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Usando valor de respaldo: " + CAMBIO_RESPALDO);
            }
        }
    }

    private static void guardarTodo() {
        GestorJSON.guardarTiquetes(tiquetesPendientes);
        GestorJSON.guardarAtendidos(tiquetesAtendidos);
        GestorJSON.guardarUsuarios(usuarios);
        JOptionPane.showMessageDialog(null,
            "Datos guardados correctamente:\n" +
            "tiquetes.json (pendientes)\n" +
            "atendidos.json (historial)");
    }
} 