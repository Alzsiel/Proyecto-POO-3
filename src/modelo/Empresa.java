//Tomás Meza

package modelo;

import java.io.Serializable;
import utilidades.*;
import java.util.ArrayList;
import java.util.List;

public class Empresa implements Serializable {
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
        return listaBuses.stream()
                .flatMap(bus -> java.util.Arrays.stream(bus.getViajes()))
                .flatMap(viaje -> java.util.Arrays.stream(viaje.getVentas()))
                .distinct()
                .toArray(Venta[]::new);
    }

    public Tripulante findTripulante(IdPersona id) {
        return listaTripulantes.stream()
                .filter(tripulante -> tripulante.getIdPersona().equals(id))
                .findFirst()
                .orElse(null);
    }
}