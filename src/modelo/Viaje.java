//Juan Jose Henriquez Vergara

package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.io.Serializable;

public class Viaje implements Serializable{
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;
    private ArrayList<Pasaje> pasajes;
    private ArrayList<Conductor> conductor;
    private ArrayList<Tripulante> tripulantes;
    private int duracion;
    private Auxiliar auxiliar;
    private Terminal sale;
    private Terminal llega;


    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus, int duracion, Auxiliar auxiliar, Conductor conductor, Terminal sale, Terminal llega) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        this.duracion = duracion;
        this.auxiliar = auxiliar;
        this.sale = sale;
        this.llega = llega;
        this.pasajes = new ArrayList<Pasaje>();
        this.tripulantes = new ArrayList<Tripulante>();
        this.conductor = new ArrayList<Conductor>();
        if (auxiliar != null) {
            addTripulante(auxiliar);
        }
        if (conductor != null) {
            addTripulante(conductor);
        }
        if (sale != null) {
            sale.addSalida(this);
        }
        if (llega != null) {
            llega.addLLegada(this);
        }
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LocalDateTime getFechaHoraTermino() {
        return LocalDateTime.of(fecha, hora).plusMinutes(duracion);
    }

    public Bus getBus() {
        return bus;
    }

    public String[] getAsientos() {
        int capacidad = bus.getNroAsientos();
        String[] asientos = new String[capacidad];

        for (int i = 0; i < capacidad; i++) {
            int numAsiento = i + 1;
            asientos[i] = String.valueOf(numAsiento);

            for (Pasaje p : pasajes) {
                if (p.getAsiento() == numAsiento) {
                    asientos[i] = "*";
                    break;
                }
            }
        }
        return asientos;
    }

    public void addPasaje(Pasaje pasaje) {
        pasajes.add(pasaje);
    }

    public String[][] getListaPasajeros() {
        return pasajes.stream()
                .map(p -> {
                    Pasajero pas = p.getPasajero();
                    return new String[]{
                            pas.getIdPersona().toString(),
                            pas.getNombreCompleto().toString(),
                            pas.getNomContacto().toString(),
                            pas.getFonoContacto()
                    };
                })
                .toArray(String[][]::new);
    }

    public boolean existeDisponibilidad(int nroAsientos) {
        return getNroAsientosDisponibles() >= nroAsientos;
    }

    public int getNroAsientosDisponibles() {
        return bus.getNroAsientos() - pasajes.size();
    }

    public Venta[] getVentas() {
        return pasajes.stream()
                .map(Pasaje::getVenta)
                .distinct()
                .toArray(Venta[]::new);
    }

    public void addConductor(Conductor conductor) {
        if (this.conductor.size() < 2) {
            this.conductor.add(conductor);
        } else {
            System.out.println("ERROR! Un viaje no puede tener mas de dos conductores");
        }
    }

    public void addTripulante(Tripulante tripulante) {
        if (tripulante == null || tripulantes.contains(tripulante)) {
            return;
        }
        tripulantes.add(tripulante);
        tripulante.addViaje(this);
        if (tripulante instanceof Conductor) {
            addConductor((Conductor) tripulante);
        } else if (tripulante instanceof Auxiliar) {
            auxiliar = (Auxiliar) tripulante;
        }
    }

    public int getDuracion() {
        return duracion;
    }

    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Terminal getTerminalLlegada() {
        return llega;
    }

    public Terminal getTerminalSalida() {
        return sale;
    }
}