package modelo;

import utilidades.*;
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private List<Bus> listaBuses;
    private List<Tripulante> listaTripulantes;
    private List<Venta> listaVentas;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.url= "";
        this.listaBuses = new ArrayList<>();
        this.listaTripulantes = new ArrayList<>();
        this.listaVentas = new ArrayList<>();

    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(Bus bus) {
        if (bus != null) {
            this.listaBuses.add(bus);
        }
    }
    public Bus[] getBuses() {
        return listaBuses.toArray(new Bus[0]);
    }


    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir){
        if (id != null && nom != null && findTripulante(id) == null) {
            Conductor nuevoConductor = new Conductor(id, nom, dir);
            this.listaTripulantes.add(nuevoConductor);
            return true;
        }
        return false;
    }

    public boolean addAuxiliar (IdPersona id, Nombre nom, Direccion dir){
        if (id != null && nom != null && findTripulante(id) == null) {
            Auxiliar nuevoAuxiliar = new Auxiliar(id, nom, dir);
            this.listaTripulantes.add(nuevoAuxiliar);
            return true;
        }
        return false;
    }


    public Tripulante[] getTripulantes() {
        return listaTripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas() {
        ArrayList<Venta> ventas = new ArrayList<>();
        for (Bus bus : listaBuses) {
            for (Viaje viaje : bus.getViajes()) {
                for (Venta venta : viaje.getVentas()) {
                    if (!ventas.contains(venta)) {
                        ventas.add(venta);
                    }
                }
            }
        }
        return ventas.toArray(new Venta[0]);
    }

    public Tripulante findTripulante(IdPersona id) {
        for (Tripulante tripulante : listaTripulantes) {
            if (tripulante.getIdPersona().equals(id)) {
                return tripulante;
            }
        }
        return null;
    }
}