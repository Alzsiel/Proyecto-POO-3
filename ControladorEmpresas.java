//Juan Jose Henriquez Vergara

package controlador;

import excepciones.SVPException;
import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.io.Serializable;

public class ControladorEmpresas implements Serializable {

    private static ControladorEmpresas instancia;

    private static final long serialVersionUID = 1L;

    public static void setInstanciaPersistente(ControladorEmpresas instanciaPersistente) {
        instancia = instanciaPersistente;
    }

    public void setDatosIniciales(Object[] datos) {
        empresas.clear();
        buses.clear();
        terminales.clear();

        for (Object obj : datos) {
            if (obj instanceof Empresa) {
                empresas.add((Empresa) obj);
            } else if (obj instanceof Bus) {
                buses.add((Bus) obj);
            } else if (obj instanceof Terminal) {
                terminales.add((Terminal) obj);
            }
        }
    }

    private ArrayList<Empresa> empresas;
    private ArrayList<Bus> buses;
    private ArrayList<Terminal> terminales;

    private ControladorEmpresas() {
        empresas = new ArrayList<Empresa>();
        buses = new ArrayList<Bus>();
        terminales = new ArrayList<Terminal>();
    }

    public static ControladorEmpresas getInstancia() {
        if (instancia == null) {
            instancia = new ControladorEmpresas();
        }
        return instancia;
    }

    public static void setInstancia(
            ControladorEmpresas nuevaInstancia) {

        instancia = nuevaInstancia;
    }

    public void createEmpresa(Rut rut, String nombre, String url) {
        if (findEmpresa(rut).isPresent()) {
            throw new SVPException("Ya existe empresa con el rut indicado");
        }

        Empresa emp = new Empresa(rut, nombre);
        emp.setUrl(url);
        empresas.add(emp);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }
        if (findBus(patente).isPresent()) {
            throw new SVPException("Ya existe bus con la patente indicada");
        }

        Bus bus = new Bus(patente, nroAsientos, emp.get());
        bus.setMarca(marca);
        bus.setModelo(modelo);
        emp.get().addBus(bus);
        buses.add(bus);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        if (findTerminal(nombre).isPresent()) {
            throw new SVPException("Ya existe terminal con el nombre indicado");
        }
        if (findTerminalPorComuna(direccion.getComuna()).isPresent()) {
            throw new SVPException("Ya existe terminal en la comuna indicada");
        }

        terminales.add(new Terminal(nombre, direccion));
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }
        if (!emp.get().addConductor(id, nom, dir)) {
            throw new SVPException("Ya esta contratado un tripulante con el id indicado");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }
        if (!emp.get().addAuxiliar(id, nom, dir)) {
            throw new SVPException("Ya esta contratado un tripulante con el id indicado");
        }
    }

    public String[][] listEmpresas() {
        System.out.println("DEBUG -> empresas.size() = " + empresas.size());
        String[][] datos = new String[empresas.size()][6];
        for (int i = 0; i < empresas.size(); i++) {
            Empresa emp = empresas.get(i);
            datos[i][0] = emp.getRut().toString();
            datos[i][1] = emp.getNombre();
            datos[i][2] = emp.getUrl();
            datos[i][3] = String.valueOf(emp.getTripulantes().length);
            datos[i][4] = String.valueOf(emp.getBuses().length);
            datos[i][5] = String.valueOf(emp.getVentas().length);
        }
        return datos;
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, LocalDate fecha) {
        Optional<Terminal> terminal = findTerminal(nombre);
        if (terminal.isEmpty()) {
            throw new SVPException("No existe terminal con el nombre indicado");
        }

        ArrayList<String[]> filas = new ArrayList<String[]>();
        for (Viaje viaje : terminal.get().getSalidas()) {
            if (viaje.getFecha().equals(fecha)) {
                filas.add(filaTerminal("Salida", viaje, viaje.getHora().toString()));
            }
        }
        for (Viaje viaje : terminal.get().getLlegadas()) {
            if (viaje.getFechaHoraTermino().toLocalDate().equals(fecha)) {
                filas.add(filaTerminal("Llegada", viaje, viaje.getFechaHoraTermino().toLocalTime().toString()));
            }
        }
        return filas.toArray(new String[0][0]);
    }

    public String[][] listVentasEmpresa(Rut rut) {
        Optional<Empresa> emp = findEmpresa(rut);
        if (emp.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }

        Venta[] ventas = emp.get().getVentas();
        String[][] datos = new String[ventas.length][4];
        for (int i = 0; i < ventas.length; i++) {
            datos[i][0] = ventas[i].getFecha().toString();
            datos[i][1] = ventas[i].getTipo().toString();
            datos[i][2] = String.valueOf(ventas[i].getMontoPagado());
            datos[i][3] = ventas[i].getTipoPago();
        }
        return datos;
    }

    protected Optional<Empresa> findEmpresa(Rut rut) {
        return empresas.stream().filter(emp-> emp.getRut().equals(rut))
                .findFirst();

    }

    protected Optional<Terminal> findTerminal(String nombre) {
        return terminales.stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {
        return terminales.stream()
                .filter(t -> t.getDireccion().getComuna().equalsIgnoreCase(comuna))
                .findFirst();
    }

    protected Optional<Bus> findBus(String patente) {
        return buses.stream()
                .filter(b -> b.getPatente().equalsIgnoreCase(patente))
                .findFirst();
    }

    protected Optional<Tripulante> findTripulante(Rut rutEmp, IdPersona id) {
        return findEmpresa(rutEmp)
                .map(emp -> emp.findTripulante(id))
                .map(Optional::ofNullable)
                .orElse(Optional.empty());
    }

    protected Optional<Empresa> findEmpresaDeBus(String patente) {
        return empresas.stream()
                .filter(emp -> java.util.Arrays.stream(emp.getBuses())
                        .anyMatch(bus -> bus.getPatente().equalsIgnoreCase(patente)))
                .findFirst();
    }

    protected ArrayList<Viaje> getViajes() {
        return buses.stream()
                .flatMap(bus -> java.util.Arrays.stream(bus.getViajes()))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    private String[] filaTerminal(String tipo, Viaje viaje, String hora) {
        Optional<Empresa> emp = findEmpresaDeBus(viaje.getBus().getPatente());
        String nombreEmpresa = emp.map(Empresa::getNombre).orElse("");
        return new String[]{
                tipo,
                hora,
                viaje.getBus().getPatente(),
                nombreEmpresa,
                String.valueOf(viaje.getBus().getNroAsientos() - viaje.getNroAsientosDisponibles())
        };
    }
}