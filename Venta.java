package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
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
}