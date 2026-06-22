//Tomás Meza

package vista;
import controlador.*;
import excepciones.SVPException;
import modelo.TipoDocumento;
import utilidades.*;
import persistencia.IOSVP;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UISVP {
    private static UISVP instancia;

    private Scanner sc;
    private SistemaVentaPasajes sistema;
    private ControladorEmpresas controladorEmpresas;
    private DateTimeFormatter formatoFecha;
    private DateTimeFormatter formatoHora;
    private IOSVP ioSVP;

    private UISVP() {
        sc = new Scanner(System.in);
        sistema = SistemaVentaPasajes.getInstancia();
        controladorEmpresas = ControladorEmpresas.getInstancia();
        formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        ioSVP = IOSVP.getInstancia();
    }

    public static UISVP getInstancia() {
        if (instancia == null) {
            instancia = new UISVP();
        }
        return instancia;
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("");
            System.out.println("========================================");
            System.out.println(". . . : : : MENU PRINCIPAL : : : . . .");
            System.out.println("========================================");
            System.out.println("1) Crear empresa");
            System.out.println("2) Contratar tripulante");
            System.out.println("3) Crear terminal");
            System.out.println("4) Crear cliente");
            System.out.println("5) Crear bus");
            System.out.println("6) Crear viaje");
            System.out.println("7) Vender pasajes");
            System.out.println("8) Listar ventas");
            System.out.println("9) Pagar venta pasajes");
            System.out.println("10) Listar viajes");
            System.out.println("11) Listar pasajeros de viaje");
            System.out.println("12) Listar empresas");
            System.out.println("13) Listar llegadas/salidas terminal");
            System.out.println("14) Listar ventas empresa");
            System.out.println("15) Generar pasajes venta");
            System.out.println("16) Leer datos iniciales");
            System.out.println("17) Guardar datos del sistema");
            System.out.println("18) Leer datos del sistema");
            System.out.println("19) Salir");
            System.out.print("Ingrese opcion: ");

            opcion = leerEntero();
            try {
                switch (opcion) {
                    case 1:
                        createEmpresa();
                        break;
                    case 2:
                        contrataTripulante();
                        break;
                    case 3:
                        createTerminal();
                        break;
                    case 4:
                        createCliente();
                        break;
                    case 5:
                        createBus();
                        break;
                    case 6:
                        createViaje();
                        break;
                    case 7:
                        vendePasajes();
                        break;
                    case 8:
                        listVentas();
                        break;
                    case 9:
                        pagaVentaPasajes();
                        break;
                    case 10:
                        listViajes();
                        break;
                    case 11:
                        listPasajerosViaje();
                        break;
                    case 12:
                        listEmpresas();
                        break;
                    case 13:
                        listLlegadasSalidasTerminal();
                        break;
                    case 14:
                        listVentasEmpresa();
                        break;
                    case 15:
                        generatePasajesVenta();
                        break;
                    case 16:
                        readDatosIniciales();
                        break;
                    case 17:
                        saveDatosSistema();
                        break;
                    case 18:
                        readDatosSistema();
                        break;
                    case 19:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("ERROR! La opcion ingresada no es valida");
                }
            } catch (SVPException e) {
                System.out.println("* " + e.getMessage() + " *");
            } catch (Exception e) {
                System.out.println("* Dato ingresado no valido *");
            }
        } while (opcion != 19);
    }

    private void createEmpresa() {
        System.out.println("...:::: Creando empresa ::::...");
        Rut rut = leerRut("R.U.T [11111111-1]: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("URL: ");
        String url = sc.nextLine();

        controladorEmpresas.createEmpresa(rut, nombre, url);
        System.out.println("Empresa guardada exitosamente");
    }

    private void contrataTripulante() {
        System.out.println("...:::: Contratando tripulante ::::...");
        Rut rutEmpresa = leerRut("R.U.T empresa [11111111-1]: ");
        System.out.print("Auxiliar[1] o Conductor[2]: ");
        int tipo = leerEntero();
        IdPersona id = leerIdPersona();
        Nombre nombre = leerNombre("tripulante");
        Direccion direccion = leerDireccion();

        if (tipo == 1) {
            controladorEmpresas.hireAuxiliarForEmpresa(rutEmpresa, id, nombre, direccion);
            System.out.println("Auxiliar contratado exitosamente");
        } else {
            controladorEmpresas.hireConductorForEmpresa(rutEmpresa, id, nombre, direccion);
            System.out.println("Conductor contratado exitosamente");
        }
    }

    private void createTerminal() {
        System.out.println("...:::: Creando terminal ::::...");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        Direccion direccion = leerDireccion();
        controladorEmpresas.createTerminal(nombre, direccion);
        System.out.println("Terminal guardado exitosamente");
    }

    private void createCliente() {
        System.out.println("...:::: Creando cliente ::::...");
        IdPersona id = leerIdPersona();
        Nombre nombre = leerNombre("cliente");
        System.out.print("Telefono: ");
        String fono = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        sistema.createCliente(id, nombre, fono, email);
        System.out.println("Cliente creado exitosamente");
    }

    private void createBus() {
        System.out.println("...:::: Creando bus ::::...");
        System.out.print("Patente: ");
        String patente = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Numero asientos: ");
        int nroAsientos = leerEntero();
        Rut rutEmpresa = leerRut("R.U.T empresa [11111111-1]: ");

        controladorEmpresas.createBus(patente, marca, modelo, nroAsientos, rutEmpresa);
        System.out.println("Bus guardado exitosamente");
    }

    private void createViaje() {
        System.out.println("...:::: Creando viaje ::::...");
        LocalDate fecha = leerFecha("Fecha [dd/MM/yyyy]: ");
        LocalTime hora = leerHora("Hora [HH:mm]: ");
        System.out.print("Precio: ");
        int precio = leerEntero();
        System.out.print("Duracion en minutos: ");
        int duracion = leerEntero();
        System.out.print("Patente bus: ");
        String patente = sc.nextLine();

        System.out.print("Numero conductores [1 o 2]: ");
        int nroConductores = leerEntero();
        if (nroConductores < 1 || nroConductores > 2) {
            System.out.println("* Numero de conductores no valido *");
            return;
        }

        IdPersona[] tripulantes = new IdPersona[nroConductores + 1];
        System.out.println("Id auxiliar:");
        tripulantes[0] = leerIdPersona();
        for (int i = 1; i < tripulantes.length; i++) {
            System.out.println("Id conductor " + i + ":");
            tripulantes[i] = leerIdPersona();
        }

        String[] comunas = new String[2];
        System.out.print("Comuna salida: ");
        comunas[0] = sc.nextLine();
        System.out.print("Comuna llegada: ");
        comunas[1] = sc.nextLine();

        sistema.createViaje(fecha, hora, precio, duracion, patente, tripulantes, comunas);
        System.out.println("Viaje guardado exitosamente");
    }

    private void vendePasajes() {
        System.out.println("...:::: Venta de pasajes ::::...");
        System.out.print("ID documento: ");
        String idDoc = sc.nextLine();
        TipoDocumento tipo = leerTipoDocumento();
        LocalDate fechaViaje = leerFecha("Fecha viaje [dd/MM/yyyy]: ");
        System.out.print("Comuna salida: ");
        String comSalida = sc.nextLine();
        System.out.print("Comuna llegada: ");
        String comLlegada = sc.nextLine();
        IdPersona idCliente = leerIdPersona();
        System.out.print("Cantidad de pasajes: ");
        int nroPasajes = leerEntero();

        sistema.iniciaVenta(idDoc, tipo, fechaViaje, comSalida, comLlegada, nroPasajes, idCliente);
        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje, comSalida, comLlegada, nroPasajes);
        imprimirMatriz(horarios, new String[]{"BUS", "HORA", "PRECIO", "DISPONIBLES"});

        System.out.print("Seleccione viaje [1.." + horarios.length + "]: ");
        int opcionViaje = leerEntero();
        String patente = horarios[opcionViaje - 1][0];
        LocalTime hora = LocalTime.parse(horarios[opcionViaje - 1][1]);

        String[] asientos = sistema.listAsientosDeViaje(fechaViaje, hora, patente);
        imprimirAsientos(asientos);

        for (int i = 0; i < nroPasajes; i++) {
            System.out.print("Asiento pasajero " + (i + 1) + ": ");
            int asiento = leerEntero();
            IdPersona idPasajero = leerIdPersona();

            try {
                sistema.vendePasaje(idDoc, tipo, fechaViaje, hora, patente, asiento, idPasajero);
            } catch (SVPException e) {
                if (!e.getMessage().contains("pasajero")) {
                    throw e;
                }
                System.out.println("Pasajero no existe, se ingresaran sus datos");
                Nombre nombrePas = leerNombre("pasajero");
                System.out.print("Telefono pasajero: ");
                String fonoPas = sc.nextLine();
                Nombre contacto = leerNombre("contacto");
                System.out.print("Telefono contacto: ");
                String fonoContacto = sc.nextLine();
                sistema.createPasajero(idPasajero, nombrePas, fonoPas, contacto, fonoContacto);
                sistema.vendePasaje(idDoc, tipo, fechaViaje, hora, patente, asiento, idPasajero);
            }
        }

        pagaVenta(idDoc, tipo);
        System.out.println("Venta realizada exitosamente");
    }

    private void pagaVentaPasajes() {
        System.out.println("...:::: Pagar venta ::::...");
        System.out.print("ID documento: ");
        String idDoc = sc.nextLine();
        TipoDocumento tipo = leerTipoDocumento();
        pagaVenta(idDoc, tipo);
        System.out.println("Pago realizado exitosamente");
    }

    private void pagaVenta(String idDoc, TipoDocumento tipo) {
        System.out.print("Pago efectivo[1] o tarjeta[2]: ");
        int tipoPago = leerEntero();
        if (tipoPago == 1) {
            sistema.pagaVenta(idDoc, tipo);
        } else {
            System.out.print("Numero tarjeta: ");
            long nroTarjeta = Long.parseLong(sc.nextLine());
            sistema.pagaVenta(idDoc, tipo, nroTarjeta);
        }
    }

    private void listVentas() {
        imprimirMatriz(sistema.listVentas(), new String[]{"ID", "TIPO", "FECHA", "CLIENTE", "NOMBRE", "CANT", "TOTAL"});
    }

    private void listViajes() {
        imprimirMatriz(sistema.listViajes(), new String[]{"FECHA", "SALE", "LLEGA", "PRECIO", "DISP", "BUS", "ORIGEN", "DESTINO"});
    }

    private void listPasajerosViaje() {
        LocalDate fecha = leerFecha("Fecha viaje [dd/MM/yyyy]: ");
        LocalTime hora = leerHora("Hora viaje [HH:mm]: ");
        System.out.print("Patente bus: ");
        String patente = sc.nextLine();
        imprimirMatriz(sistema.listPasajerosViaje(fecha, hora, patente),
                new String[]{"ID", "PASAJERO", "CONTACTO", "FONO CONTACTO"});
    }

    private void listEmpresas() {
        imprimirMatriz(controladorEmpresas.listEmpresas(),
                new String[]{"RUT", "NOMBRE", "URL", "TRIP", "BUSES", "VENTAS"});
    }

    private void listLlegadasSalidasTerminal() {
        System.out.print("Nombre terminal: ");
        String terminal = sc.nextLine();
        LocalDate fecha = leerFecha("Fecha [dd/MM/yyyy]: ");
        imprimirMatriz(controladorEmpresas.listLlegadasSalidasTerminal(terminal, fecha),
                new String[]{"TIPO", "HORA", "BUS", "EMPRESA", "PASAJEROS"});
    }

    private void listVentasEmpresa() {
        Rut rut = leerRut("R.U.T empresa [11111111-1]: ");
        imprimirMatriz(controladorEmpresas.listVentasEmpresa(rut),
                new String[]{"FECHA", "TIPO", "MONTO", "TIPO PAGO"});
    }

    private int leerEntero() {
        return Integer.parseInt(sc.nextLine().trim());
    }

    private Rut leerRut(String mensaje) {
        System.out.print(mensaje);
        String rut = sc.nextLine().trim();
        if (!rut.matches("[0-9]+-[0-9kK]")) {
            throw new IllegalArgumentException("Rut invalido");
        }
        return Rut.of(rut);
    }

    private IdPersona leerIdPersona() {
        System.out.print("Rut[1] o Pasaporte[2]: ");
        int tipo = leerEntero();
        if (tipo == 1) {
            return leerRut("R.U.T [11111111-1]: ");
        }
        System.out.print("Pasaporte: ");
        String numero = sc.nextLine();
        System.out.print("Nacionalidad: ");
        String nacionalidad = sc.nextLine();
        return new Pasaporte(numero, nacionalidad);
    }

    private Nombre leerNombre(String texto) {
        System.out.println("Datos " + texto);
        System.out.print("Sr[1] o Sra[2]: ");
        int opcion = leerEntero();
        Tratamiento tratamiento = Tratamiento.SR;
        if (opcion == 2) {
            tratamiento = Tratamiento.SRA;
        }
        System.out.print("Nombres: ");
        String nombres = sc.nextLine();
        System.out.print("Apellido paterno: ");
        String apPat = sc.nextLine();
        System.out.print("Apellido materno: ");
        String apMat = sc.nextLine();
        return new Nombre(tratamiento, nombres, apPat, apMat);
    }

    private Direccion leerDireccion() {
        System.out.print("Calle: ");
        String calle = sc.nextLine();
        System.out.print("Numero: ");
        int numero = leerEntero();
        System.out.print("Comuna: ");
        String comuna = sc.nextLine();
        return new Direccion(calle, numero, comuna);
    }

    private LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        return LocalDate.parse(sc.nextLine(), formatoFecha);
    }

    private LocalTime leerHora(String mensaje) {
        System.out.print(mensaje);
        return LocalTime.parse(sc.nextLine(), formatoHora);
    }

    private TipoDocumento leerTipoDocumento() {
        System.out.print("Boleta[1] o Factura[2]: ");
        int tipo = leerEntero();
        if (tipo == 1) {
            return TipoDocumento.BOLETA;
        }
        return TipoDocumento.FACTURA;
    }

    private void imprimirMatriz(String[][] datos, String[] titulos) {
        if (datos.length == 0) {
            System.out.println("* No hay datos para mostrar *");
            return;
        }
        for (String titulo : titulos) {
            System.out.printf("| %-25s", titulo);
        }
        System.out.println("|");
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                System.out.printf("| %-25s", datos[i][j]);
            }
            System.out.println("|");
        }
    }

    private void imprimirAsientos(String[] asientos) {
        for (int i = 0; i < asientos.length; i++) {
            System.out.printf("[%3s]", asientos[i]);
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private void generatePasajesVenta() {

        System.out.println(" ");
        System.out.println("...::::Generando pasajes venta::::....");

        System.out.print("ID Documento : ");
        String idDoc = sc.nextLine();

        System.out.print("Tipo de documento : ");
        System.out.print("Boleta[1] o Factura[2] : ");
        int op = Integer.parseInt(sc.nextLine());
        TipoDocumento tipo;
        if (op == 1) {
            tipo = TipoDocumento.BOLETA;
        } else {
            tipo = TipoDocumento.FACTURA;
        }
        sistema.generatePasajesVenta(idDoc, tipo);
        System.out.println("...::::Pasajes generados correctamente::::....");
    }

    private void readDatosIniciales() {

        System.out.println(" ");
        System.out.println("...::::Leer datos iniciales::::....");
        try {
            sistema.readDatosIniciales();
            System.out.println("...::::Datos iniciales cargados con exito::::....");
        } catch (SVPException e) {
            System.out.println("No existe o no se puede abrir el archivo SVPDatosIniciales.txt");
        }
    }

    private void saveDatosSistema() {

        System.out.println(" ");
        System.out.println("...::::Guardar datos del sistema::::....");
        try {
            sistema.saveDatosSistema();
            System.out.println("....::::Datos guardados correctamente::::....");
        } catch (SVPException e) {
            System.out.println("No se puede abrir o crear el archivo SVPObjetos.obj");
        }
    }

    private void readDatosSistema() {
        System.out.println(" ");
        System.out.println("...::::Recuperando datos del sistema::::....");
        try {
            sistema.readDatosSistema();
            sistema = SistemaVentaPasajes.getInstancia();
            controladorEmpresas = ControladorEmpresas.getInstancia();
            System.out.println("...::::Datos recuperados correctamente::::....");
        } catch (SVPException e) {
            System.out.println("No existe o no se puede abrir el archivo");
        }
    }
}