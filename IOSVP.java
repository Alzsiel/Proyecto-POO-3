//Marisol Yañez Borquez
package persistencia;

import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.List;
import java.util.Optional;
import modelo.*;
import controlador.ControladorEmpresas;
import controlador.SistemaVentaPasajes;
import utilidades.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;

public class IOSVP implements Serializable{

    private static IOSVP instancia;

    private IOSVP() {
    }

    public static IOSVP getInstancia() {
        if (instancia == null) {
            instancia = new IOSVP();
        }
        return instancia;
    }

    public void readDatosIniciales() {

        try (BufferedReader br = new BufferedReader(new FileReader("SVPDatosIniciales.txt"))) {
            String linea;
            int seccion = 0;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                if (linea.equals("+")) {
                    seccion++;
                    continue;
                }

                switch (seccion) {
                    case 0:
                        parseClientePasajero(linea);
                        break;
                    case 1:
                        parseEmpresa(linea);
                        break;
                    case 2:
                        parseTripulante(linea);
                        break;
                    case 3:
                        parseTerminal(linea);
                        break;
                    case 4:
                        parseBus(linea);
                        break;
                    case 5:
                        parseViaje(linea);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseEmpresa(String linea) {

        String[] datos = linea.split(";");

        Rut rut = Rut.of(datos[0].replace(".", ""));
        String nombre = datos[1];
        String url = datos[2];

        ControladorEmpresas.getInstancia().createEmpresa(rut, nombre, url);
    }

    private void parseTripulante(String linea) {

        String[] datos = linea.split(";");

        String tipo = datos[0];

        Rut id = Rut.of(datos[1].replace(".", ""));

        Tratamiento tratamiento = Tratamiento.valueOf(datos[2]);

        Nombre nombre = new Nombre(tratamiento, datos[3], datos[4], datos[5]);

        Direccion direccion = new Direccion(datos[7], Integer.parseInt(datos[8]), datos[9]);

        Rut rutEmpresa = Rut.of(datos[10].replace(".", ""));

        if (tipo.equalsIgnoreCase("C")) {

            ControladorEmpresas.getInstancia().hireConductorForEmpresa(rutEmpresa, id, nombre, direccion);

        } else if (tipo.equalsIgnoreCase("A")){
            ControladorEmpresas.getInstancia().hireAuxiliarForEmpresa(rutEmpresa, id, nombre, direccion);
        }
    }

    private void parseTerminal(String linea) {

        String[] datos = linea.split(";");

        String nombre = datos[0];

        Direccion direccion = new Direccion(datos[1], Integer.parseInt(datos[2]), datos[3]);

        ControladorEmpresas.getInstancia().createTerminal(nombre, direccion);
    }

    private void parseBus(String linea) {

        String[] datos = linea.split(";");

        Rut.of(datos[4].replace(".", ""));

        ControladorEmpresas.getInstancia().createBus(
                datos[0],
                datos[1],
                datos[2],
                Integer.parseInt(datos[3]),
                Rut.of(datos[4].replace(".","")));
    }

    private void parseViaje(String linea) {

        String[] datos = linea.split(";");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate fecha = LocalDate.parse(datos[0], formato);

        LocalTime hora = LocalTime.parse(datos[1]);

        int precio = Integer.parseInt(datos[2]);

        int duracion = Integer.parseInt(datos[3]);

        String patente = datos[4];

        IdPersona[] tripulantes = new IdPersona[2];

        tripulantes[0] = Rut.of(datos[5].replace(".", ""));

        tripulantes[1] = Rut.of(datos[6].replace(".", ""));

        String[] comunas =
                {
                        datos[7],
                        datos[8]
                };

        SistemaVentaPasajes.getInstancia().createViaje(
                fecha,
                hora,
                precio,
                duracion,
                patente,
                tripulantes,
                comunas);
    }

    private void parseClientePasajero(String linea) {

        System.out.println("Leyendo linea: " + linea);

        String[] datos = linea.split(";");

        System.out.println("Tipo = " + datos[0]);

        String tipo = datos[0];

        Rut rut = Rut.of(datos[1].replace(".", ""));

        Nombre nombre =
                new Nombre(
                        Tratamiento.valueOf(datos[2]),
                        datos[3],
                        datos[4],
                        datos[5]);

        String telefono = datos[6];

        SistemaVentaPasajes svp =
                SistemaVentaPasajes.getInstancia();

        if (tipo.equals("C")) {

            svp.createCliente(
                    rut,
                    nombre,
                    telefono,
                    datos[8]);

        } else if (tipo.equals("P")) {

            System.out.println("datos.length = " + datos.length);

            for(int i=0;i<datos.length;i++){
                System.out.println(i + " -> " + datos[i]);
            }

            System.out.println("Antes de crear contacto");

            Nombre contacto =
                    new Nombre(
                            Tratamiento.valueOf(datos[7]),
                            datos[8],
                            datos[9],
                            datos[10]);

            System.out.println("Contacto creado");

            System.out.println("Antes de createPasajero");

            svp.createPasajero(
                    rut,
                    nombre,
                    telefono,
                    contacto,
                    datos[11]);

            System.out.println("Pasajero creado");

        } else if (tipo.equals("CP")) {

            svp.createCliente(
                    rut,
                    nombre,
                    telefono,
                    datos[8]);

            Nombre contacto =
                    new Nombre(
                            Tratamiento.valueOf(datos[9]),
                            datos[10],
                            datos[11],
                            datos[12]);

            svp.createPasajero(
                    rut,
                    nombre,
                    telefono,
                    contacto,
                    datos[14]);
        }
    }

    public void saveControladores(Object[] controladores) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("controladores.dat"))) {

            out.writeObject(controladores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object readControladores() {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("controladores.dat"))) {

            return in.readObject();

        } catch (Exception e) {
            return null;
        }
    }

    public void savePasajesDeVenta(
            Pasaje[] pasajes,
            String nombreArchivo) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {

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