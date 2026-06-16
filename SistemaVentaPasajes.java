//Juan Jose Henriquez Vergara

package controlador;

import excepciones.SVPException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import modelo.*;
import utilidades.*;

public class SistemaVentaPasajes {
    private static SistemaVentaPasajes instancia;

    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Viaje> viajes;
    private ArrayList<Venta> ventas;

    private SistemaVentaPasajes() {
        clientes = new ArrayList<Cliente>();
        pasajeros = new ArrayList<Pasajero>();
        viajes = new ArrayList<Viaje>();
        ventas = new ArrayList<Venta>();
    }

    public static SistemaVentaPasajes getInstancia() {
        if (instancia == null) {
            instancia = new SistemaVentaPasajes();
        }
        return instancia;
    }

    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id).isPresent()) {
            throw new SVPException("Ya existe cliente con el id indicado");
        }

        Cliente cliente = new Cliente(id, nom, email);
        cliente.setTelefono(fono);
        clientes.add(cliente);
    }

    public void createPasajero(IdPersona idPersona, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {
        if (findPasajero(idPersona).isPresent()) {
            throw new SVPException("Ya existe pasajero con el id indicado");
        }
        Pasajero pasajero = new Pasajero(idPersona, nom);
        pasajero.setTelefono(fono);
        pasajero.setNomContacto(nomContacto);
        pasajero.setFonoContacto(fonoContacto);
        pasajeros.add(pasajero);
    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patBus,
                            IdPersona[] idTripulantes, String[] nomComunas) {
        ControladorEmpresas contEmp = ControladorEmpresas.getInstancia();
        Optional<Bus> bus = contEmp.findBus(patBus);
        if (bus.isEmpty()) {
            throw new SVPException("No existe bus con la patente indicada");
        }
        if (findViaje(fecha, hora, patBus).isPresent()) {
            throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados");
        }

        Optional<Empresa> empresa = contEmp.findEmpresaDeBus(patBus);
        if (empresa.isEmpty()) {
            throw new SVPException("No existe empresa asociada al bus indicado");
        }

        Optional<Tripulante> auxiliar = contEmp.findTripulante(empresa.get().getRut(), idTripulantes[0]);
        if (auxiliar.isEmpty() || !(auxiliar.get() instanceof Auxiliar)) {
            throw new SVPException("No existe auxiliar con el id indicado en la empresa");
        }

        ArrayList<Conductor> conductores = new ArrayList<Conductor>();
        for (int i = 1; i < idTripulantes.length; i++) {
            Optional<Tripulante> trip = contEmp.findTripulante(empresa.get().getRut(), idTripulantes[i]);
            if (trip.isEmpty() || !(trip.get() instanceof Conductor)) {
                throw new SVPException("No existe conductor con el id indicado en la empresa");
            }
            conductores.add((Conductor) trip.get());
        }

        Optional<Terminal> salida = contEmp.findTerminalPorComuna(nomComunas[0]);
        if (salida.isEmpty()) {
            throw new SVPException("No existe terminal de salida en la comuna indicada");
        }
        Optional<Terminal> llegada = contEmp.findTerminalPorComuna(nomComunas[1]);
        if (llegada.isEmpty()) {
            throw new SVPException("No existe terminal de llegada en la comuna indicada");
        }

        Viaje viaje = new Viaje(fecha, hora, precio, bus.get(), duracion, (Auxiliar) auxiliar.get(),
                conductores.get(0), salida.get(), llegada.get());
        if (conductores.size() == 2) {
            viaje.addTripulante(conductores.get(1));
        }

        viajes.add(viaje);
        bus.get().addViaje(viaje);
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comSalida,
                            String comLlegada, int nroPasajes, IdPersona idCliente) {
        if (findVenta(idDoc, tipo).isPresent()) {
            throw new SVPException("Ya existe venta con el id y tipo de documento indicados");
        }

        Optional<Cliente> cliente = findCliente(idCliente);
        if (cliente.isEmpty()) {
            throw new SVPException("No existe cliente con id indicado");
        }
        if (getHorariosDisponibles(fechaViaje, comSalida, comLlegada, nroPasajes).length == 0) {
            throw new SVPException("No existen viajes disponibles para los datos indicados");
        }

        ventas.add(new Venta(idDoc, tipo, LocalDate.now(), cliente.get()));
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String comunaSalida, String comunaLlegada, int nroPasajes) {
        ArrayList<Viaje> lista = new ArrayList<Viaje>();
        for (Viaje viaje : viajes) {
            boolean mismaFecha = viaje.getFecha().equals(fechaViaje);
            boolean mismaSalida = viaje.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(comunaSalida);
            boolean mismaLlegada = viaje.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(comunaLlegada);
            if (mismaFecha && mismaSalida && mismaLlegada && viaje.existeDisponibilidad(nroPasajes)) {
                lista.add(viaje);
            }
        }

        String[][] resultado = new String[lista.size()][4];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i][0] = lista.get(i).getBus().getPatente();
            resultado[i][1] = lista.get(i).getHora().toString();
            resultado[i][2] = String.valueOf(lista.get(i).getPrecio());
            resultado[i][3] = String.valueOf(lista.get(i).getNroAsientosDisponibles());
        }
        return resultado;
    }

    public String[] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viaje = findViaje(fecha, hora, patBus);
        if (viaje.isEmpty()) {
            return new String[0];
        }
        return viaje.get().getAsientos();
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> venta = findVenta(idDocumento, tipo);
        if (venta.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(venta.get().getMonto());
    }

    public Optional<String> getNombrePasajero(IdPersona idPasajero) {
        Optional<Pasajero> pasajero = findPasajero(idPasajero);
        if (pasajero.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(pasajero.get().getNombreCompleto().toString());
    }

    public String[][] listPasajerosViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> viaje = findViaje(fecha, hora, patBus);
        if (viaje.isEmpty()) {
            throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        return viaje.get().getListaPasajeros();
    }

    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fecha, LocalTime hora, String patBus,
                            int asiento, IdPersona idPasajero) {
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if (venta.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }
        Optional<Viaje> viaje = findViaje(fecha, hora, patBus);
        if (viaje.isEmpty()) {
            throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        }
        Optional<Pasajero> pasajero = findPasajero(idPasajero);
        if (pasajero.isEmpty()) {
            throw new SVPException("No existe pasajero con el id indicado");
        }
        if (asiento < 1 || asiento > viaje.get().getBus().getNroAsientos()) {
            throw new SVPException("El asiento indicado no es valido");
        }
        if (viaje.get().getAsientos()[asiento - 1].equals("*")) {
            throw new SVPException("El asiento indicado ya esta ocupado");
        }

        venta.get().createPasaje(asiento, viaje.get(), pasajero.get());
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo) {
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if (venta.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }
        if (!venta.get().pagaMonto()) {
            throw new SVPException("La venta ya fue pagada");
        }
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo, long nroTarjeta) {
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if (venta.isEmpty()) {
            throw new SVPException("No existe venta con el id y tipo de documento indicados");
        }
        if (!venta.get().pagaMonto(nroTarjeta)) {
            throw new SVPException("La venta ya fue pagada");
        }
    }

    public String[][] listViajes() {
        String[][] resultado = new String[viajes.size()][8];
        for (int i = 0; i < viajes.size(); i++) {
            Viaje viaje = viajes.get(i);
            resultado[i][0] = viaje.getFecha().toString();
            resultado[i][1] = viaje.getHora().toString();
            resultado[i][2] = viaje.getFechaHoraTermino().toLocalTime().toString();
            resultado[i][3] = String.valueOf(viaje.getPrecio());
            resultado[i][4] = String.valueOf(viaje.getNroAsientosDisponibles());
            resultado[i][5] = viaje.getBus().getPatente();
            resultado[i][6] = viaje.getTerminalSalida().getDireccion().getComuna();
            resultado[i][7] = viaje.getTerminalLlegada().getDireccion().getComuna();
        }
        return resultado;
    }

    public String[][] listVentas() {
        String[][] resultado = new String[ventas.size()][7];
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            resultado[i][0] = venta.getIdDocumento();
            resultado[i][1] = venta.getTipo().toString();
            resultado[i][2] = venta.getFecha().toString();
            resultado[i][3] = venta.getCliente().getIdPersona().toString();
            resultado[i][4] = venta.getCliente().getNombreCompleto().toString();
            resultado[i][5] = String.valueOf(venta.getPasajes().length);
            resultado[i][6] = String.valueOf(venta.getMonto());
        }
        return resultado;
    }

    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdPersona().equals(id)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta venta : ventas) {
            if (venta.getIdDocumento().equals(idDocumento) && venta.getTipo() == tipoDocumento) {
                return Optional.of(venta);
            }
        }
        return Optional.empty();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().equals(fecha)
                    && viaje.getHora().equals(hora)
                    && viaje.getBus().getPatente().equalsIgnoreCase(patenteBus)) {
                return Optional.of(viaje);
            }
        }
        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero pasajero : pasajeros) {
            if (pasajero.getIdPersona().equals(idPersona)) {
                return Optional.of(pasajero);
            }
        }
        return Optional.empty();
    }
}