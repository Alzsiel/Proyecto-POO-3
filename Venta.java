//Tomás Meza

package modelo;
import excepciones.SVPException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Venta implements Serializable {
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private ArrayList<Pasaje> pasajes;
    private Pago pago;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.pasajes = new ArrayList<Pasaje>();
        cliente.addVenta(this);
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje pasaje = new Pasaje(asiento, viaje, pasajero, this);
        pasajes.add(pasaje);
        viaje.addPasaje(pasaje);
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int total = 0;
        for (Pasaje pasaje : pasajes) {
            total += pasaje.getViaje().getPrecio();
        }
        return total;
    }
    public int getMontoPagado() {
        if (pago == null) {
            return 0;
        }
        return pago.getMonto();
    }

    public boolean pagaMonto(long nroTarjeta) {
        if (pago != null) {
            return false;
        }

        int monto = getMonto();

        if (nroTarjeta > 0) {
            pago = new PagoTarjeta(monto, nroTarjeta);
        } else {
            pago = new PagoEfectivo(monto);
        }

        return true;
    }

    public boolean pagaMonto() {
        if (pago != null) {
            return false;
        }
        pago = new PagoEfectivo(getMonto());
        return true;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null ||
                getClass() != otro.getClass()) {

            return false;
        }

        Venta venta = (Venta) otro;
        return idDocumento.equals(venta.idDocumento);
    }
    public String getTipoPago() {
        if (pago == null) {
            return null;
        }
        return pago.getClass().getSimpleName();
    }

    public void generarPasajeElectronico() {
        String nombreArchivo = this.idDocumento + this.tipo.toString().toLowerCase() + ".txt";
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(nombreArchivo))) {
            for (Pasaje p : this.getPasajes()) {
                Viaje viaje = p.getViaje();
                Pasajero pasajero = p.getPasajero();
                Empresa empresa = viaje.getBus().getEmpresa();
                String tratamiento = pasajero.getNombreCompleto().getTratamiento().toString();

                writer.write("---------------------- PASAJE ELECTRÓNICO ----------------------");
                writer.newLine();
                writer.write(String.format("%-20s %-20s", "Nombre Empresa", "Número de pasaje"));
                writer.newLine();
                writer.write(String.format("%-20s %-20d", empresa.getNombre().toUpperCase(), p.getNumero()));
                writer.newLine();
                writer.write(String.format("%-30s %-20s", "Nombre Pasajero", "RUT/Pasaporte"));
                writer.newLine();
                writer.write(String.format("%-30s %-20s", (tratamiento + ". " + pasajero.getNombreCompleto().toString()).toUpperCase(), pasajero.getIdPersona().toString()));
                writer.newLine();
                writer.write(String.format("%-15s %-10s %-15s", "Patente bus", "Asiento", "Valor Pagado"));
                writer.newLine();
                writer.write(String.format("%-15s %-10d %-15d", viaje.getBus().getPatente().toUpperCase(), p.getAsiento(), viaje.getPrecio()));
                writer.newLine();
                writer.write(String.format("%-20s %-20s %-15s %-10s", "Terminal origen", "Terminal destino", "Fecha", "Hora"));
                writer.newLine();
                writer.write(String.format("%-20s %-20s %-15s %-10s",
                        viaje.getTerminalSalida().getNombre().toUpperCase(),
                        viaje.getTerminalLlegada().getNombre().toUpperCase(),
                        viaje.getFecha().toString(),
                        viaje.getHora().toString()));
                writer.newLine();
                writer.write("----------------------------------------------------------------");
                writer.newLine();
                writer.newLine();
            }
        } catch (java.io.IOException e) {
            throw new SVPException("Error al generar el archivo de pasajes: " + e.getMessage());
        }
    }
}