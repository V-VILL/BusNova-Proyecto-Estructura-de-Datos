
package servicios;
import java.io.*;
import java.net.*;

/**
 *
 * @author bayde
 */
public class ServicioBCCR {
    private String token;

    public ServicioBCCR(String token) {
        this.token = token;
    }

    public double obtenerTipoCambioCompra() {
        return llamarServicio("317");
    }

    public double obtenerTipoCambioVenta() {
        return llamarServicio("318");
    }

    private double llamarServicio(String codigo) {
        try {
            String fecha = obtenerFechaHoy();
            String direccion = "https://gee.bccr.fi.cr/IndicadoresEconomicos/WS/Indicadores.asmx/ObtenerIndicadoresEconomicos?"
                    + "indicador=" + codigo
                    + "&fechaInicio=" + fecha
                    + "&fechaFinal=" + fecha
                    + "&nombre=BusNovaTech"
                    + "&subNivel=N"
                    + "&correo=tu@correo.com"
                    + "&token=" + token;

            URL url = new URL (direccion);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String linea;
            StringBuilder resp = new StringBuilder();
            while ((linea = r.readLine()) != null) resp.append(linea);
            r.close();

            return extraerValor(resp.toString());
        } catch (Exception e) {
            System.err.println("Error BCCR: " + e.getMessage());
            return 515.75; 
        }
    }

    private String obtenerFechaHoy() {
        return "07/10/2026";
    }

    private double extraerValor(String xml) {
        try {
            String buscar = "<NUM_VALOR>";
            int i = xml.indexOf(buscar) + buscar.length();
            int f = xml.indexOf("</NUM_VALOR>", i);
            return Double.parseDouble(xml.substring(i, f));
        } catch (Exception e) {
            return 515.75;
        }
    }
}

