package persistencia;

import java.io.*;
import java.util.List;
import java.util.Optional;

import modelo.*;
import utilidades.IdPersona;
import utilidades.Rut;

public class IOSVP {

    private static IOSVP instancia;

    private IOSVP() {
    }

    public static IOSVP getInstancia() {
        if (instancia == null) {
            instancia = new IOSVP();
        }
        return instancia;
    }

    public Object readDatosIniciales() {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("datosIniciales.dat"))) {

            return in.readObject();

        } catch (Exception e) {
            return null;
        }
    }

    public void saveControladores(Object[] controladores) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("controladores.dat"))) {

            out.writeObject(controladores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object readControladores() {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("controladores.dat"))) {

            return in.readObject();

        } catch (Exception e) {
            return null;
        }
    }

    public void savePasajesDeVenta(
            Pasaje[] pasajes,
            String nombreArchivo) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(nombreArchivo))) {

            out.writeObject(pasajes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Empresa> findEmpresa(
            List<Empresa> empresas,
            Rut rut) {

        for (Empresa emp : empresas) {

            if (emp.getRut().equals(rut)) {
                return Optional.of(emp);
            }
        }

        return Optional.empty();
    }

    public Optional<Tripulante> findTripulante(
            Empresa empresa,
            IdPersona id) {

        for (Tripulante t : empresa.getTripulantes()) {

            if (t.getIdPersona().equals(id)) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    public Optional<Bus> findBus(
            List<Bus> buses,
            String patente) {

        for (Bus bus : buses) {

            if (bus.getPatente().equalsIgnoreCase(patente)) {
                return Optional.of(bus);
            }
        }

        return Optional.empty();
    }

    public Optional<Terminal> findTerminal(
            List<Terminal> terminales,
            String nombre) {

        for (Terminal t : terminales) {

            if (t.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }
}