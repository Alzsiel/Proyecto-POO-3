//Tomás Meza

package vista;

import utilidades.Rut;

import javax.swing.*;
import java.awt.event.*;

public class MenuCrear extends JDialog {
    private JPanel contentPane;
    private JButton buttonVolver;
    private JButton CREARCLIENTEButton;
    private JButton CREARTERMINALButton;
    private JButton CREAREMPRESAButton;
    private JButton CREARVIAJEButton;
    private JButton CREARBUSButton;
    private JButton CREARTRIPULANTEButton;

    public MenuCrear() {
        setContentPane(contentPane);
        setModal(true);


        buttonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        CREAREMPRESAButton.addActionListener(e -> {
            boolean ejecutandoAsistente = true;
            int pasoActual = 1;

            String rut = "";
            String nombre = "";
            String url = "";

            Object[] opcionesSiguienteCancelar = {"Siguiente", "Cancelar"};
            Object[] opcionesSiNo = {"Sí", "No"};

            while (ejecutandoAsistente) {
                switch (pasoActual) {
                    case 1:
                        JTextField txtRut = new JTextField(rut);
                        Object[] cuerpoRut = {"Ingrese R.U.T de empresa (11111111-1):", txtRut};

                        int seleccionRut = JOptionPane.showOptionDialog(this, cuerpoRut, "Creando EMPRESA...",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                opcionesSiguienteCancelar, opcionesSiguienteCancelar[0]);

                        if (seleccionRut == JOptionPane.YES_OPTION) {
                            String rutIngresado = txtRut.getText().trim();

                            if (rutIngresado.matches("\\d{7,8}-[0-9kK]")) {
                                rut = rutIngresado;
                                pasoActual = 2;
                            } else {
                                JOptionPane.showMessageDialog(this, "Rut invalido. Intente de nuevo", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            ejecutandoAsistente = false;
                        }
                        break;
                    case 2:
                        JTextField txtNombre = new JTextField(nombre);
                        Object[] cuerpoNombre = {"Ingrese nombre de empresa:", txtNombre};

                        int seleccionNombre = JOptionPane.showOptionDialog(this, cuerpoNombre, "Creando EMPRESA...",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                opcionesSiguienteCancelar, opcionesSiguienteCancelar[0]);

                        if (seleccionNombre == JOptionPane.YES_OPTION) {
                            nombre = txtNombre.getText().trim();
                            if (!nombre.isEmpty()) {
                                pasoActual = 3;
                            } else {
                                JOptionPane.showMessageDialog(this, "**El nombre no puede estar vacío**", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            ejecutandoAsistente = false;
                        }
                        break;
                    case 3:
                        JTextField txtUrl = new JTextField(url);
                        Object[] cuerpoUrl = {"Ingrese URL de la empresa:", txtUrl};

                        int seleccionUrl = JOptionPane.showOptionDialog(this, cuerpoUrl, "Creando EMPRESA...",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                opcionesSiguienteCancelar, opcionesSiguienteCancelar[0]);

                        if (seleccionUrl == JOptionPane.YES_OPTION) {
                            url = txtUrl.getText().trim();
                            if (!url.isEmpty()) {
                                pasoActual = 4;
                            } else {
                                JOptionPane.showMessageDialog(this, "La URL no puede estar vacía.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            ejecutandoAsistente = false;
                        }
                        break;
                    case 4:
                        String mensajeConfirmacion = "¿Esta seguro de crear esta empresa?\n\n" +
                                "RUT: " + rut + "\n" +
                                "Nombre: " + nombre + "\n" +
                                "URL: " + url;

                        int seleccionConfirmar = JOptionPane.showOptionDialog(this, mensajeConfirmacion, "CONFIRMACIÓN",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                opcionesSiNo, opcionesSiNo[0]);

                        if (seleccionConfirmar == JOptionPane.YES_OPTION) {

                            try {
                                utilidades.Rut rutObjeto = utilidades.Rut.of(rut);

                                controlador.ControladorEmpresas.getInstancia().createEmpresa(rutObjeto, nombre, url);

                                JOptionPane.showMessageDialog(this, "**Empresa guardada exitosamente**", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                                ejecutandoAsistente = false;

                            } catch (excepciones.SVPException ex) {
                                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

                                pasoActual = 1;
                            }
                        } else {
                            pasoActual = 1;
                        }
                        break;
                }
            }
        });

        CREARBUSButton.addActionListener(e -> {
            Object[] opcionesSiguiente = {"Siguiente", "Cancelar"};

            outerLoop:
            while (true) {
                JTextField txtPatente = new JTextField();
                Object[] panelPatente = {"Crear patente del bus:", txtPatente};
                int resPatente = JOptionPane.showOptionDialog(this, panelPatente, "Creando BUS...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resPatente != 0 || txtPatente.getText().trim().isEmpty()) break;
                String patente = txtPatente.getText().trim();

                JTextField txtMarca = new JTextField();
                Object[] panelMarca = {"Marca del bus:", txtMarca};
                int resMarca = JOptionPane.showOptionDialog(this, panelMarca, "Creando BUS...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resMarca != 0) break;
                String marca = txtMarca.getText().trim();

                JTextField txtModelo = new JTextField();
                Object[] panelModelo = {"Modelo del bus:", txtModelo};
                int resModelo = JOptionPane.showOptionDialog(this, panelModelo, "Creando BUS...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resModelo != 0) break;
                String modelo = txtModelo.getText().trim();

                JTextField txtAsientos = new JTextField();
                Object[] panelAsientos = {"Cantidad de asientos:", txtAsientos};
                int resAsientos = JOptionPane.showOptionDialog(this, panelAsientos, "Creando BUS...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resAsientos != 0) break;

                int asientos = 40;
                try {
                    if (!txtAsientos.getText().trim().isEmpty()) {
                        asientos = Integer.parseInt(txtAsientos.getText().trim());
                    }
                } catch (NumberFormatException ex) {

                }

                while (true) {
                    JTextField txtRut = new JTextField();
                    Object[] panelRut = {"R.U.T de empresa:", txtRut};
                    int resRut = JOptionPane.showOptionDialog(this, panelRut, "Creando BUS...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                    if (resRut != 0) break outerLoop;

                    String rutInput = txtRut.getText().trim();

                    String rutInputLimpio = rutInput.replaceAll("[^0-9Kk]", "");

                    boolean empresaExiste = false;
                    String[][] matrizEmpresas = controlador.ControladorEmpresas.getInstancia().listEmpresas();
                    for (String[] fila : matrizEmpresas) {
                        String rutFilaLimpio = fila[0].replaceAll("[^0-9Kk]", "");
                        if (rutFilaLimpio.equals(rutInputLimpio)) {
                            empresaExiste = true;
                            break;
                        }
                    }

                    if (empresaExiste) {
                        Object[] opcionesSiNo = {"Sí", "No"};
                        int confirmacion = JOptionPane.showOptionDialog(this,
                                "¿Está seguro que quiere registrar este bus?",
                                "CONFIRMACIÓN",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesSiNo, opcionesSiNo[0]);

                        if (confirmacion == 0) {
                            try {
                                java.lang.reflect.Field campoEmpresas = controlador.ControladorEmpresas.class.getDeclaredField("empresas");
                                campoEmpresas.setAccessible(true);
                                java.util.ArrayList<?> listaReal = (java.util.ArrayList<?>) campoEmpresas.get(controlador.ControladorEmpresas.getInstancia());

                                utilidades.Rut rutIdenticoDeMemoria = null;
                                for (Object emp : listaReal) {
                                    java.lang.reflect.Method metodoGetRut = emp.getClass().getMethod("getRut");
                                    Object rutExtraido = metodoGetRut.invoke(emp);

                                    if (rutExtraido.toString().replaceAll("[^0-9Kk]", "").equals(rutInputLimpio)) {
                                        rutIdenticoDeMemoria = (utilidades.Rut) rutExtraido;
                                        break;
                                    }
                                }

                                utilidades.Rut rutFinalParaEnviar = (rutIdenticoDeMemoria != null) ? rutIdenticoDeMemoria : new utilidades.Rut(rutInput);

                                controlador.ControladorEmpresas.getInstancia().createBus(patente, marca, modelo, asientos, rutFinalParaEnviar);
                                JOptionPane.showMessageDialog(this, "**Bus guardado exitosamente**", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                                return;

                            } catch (excepciones.SVPException exSVP) {
                                JOptionPane.showMessageDialog(this, exSVP.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception exFatal) {
                                JOptionPane.showMessageDialog(this, "Fallo interno al crear el objeto: " + exFatal.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            continue outerLoop;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "**No existe empresa con el rut indicado**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        CREARVIAJEButton.addActionListener(e -> {
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

            viajeLoop:
            while (true) {
                java.time.LocalDate fecha = null;
                String fechaStr = "";
                while (true) {
                    JTextField txtFecha = new JTextField("dd/MM/yyyy");
                    Object[] panelFecha = {"Ingrese Fecha:\n(rellenar con día(dd)/rellenar con mes(MM)/rellenar con año(yyyy))", txtFecha};
                    int res = JOptionPane.showOptionDialog(this, panelFecha, "Creando VIAJE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    try {
                        fechaStr = txtFecha.getText().trim();
                        fecha = java.time.LocalDate.parse(fechaStr, dateFormatter);
                        break;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "**fecha de viaje invalida**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                java.time.LocalTime hora = null;
                String horaStr = "";
                while (true) {
                    JTextField txtHora = new JTextField("HH:mm");
                    Object[] panelHora = {"Ingrese Hora:\n(rellenar con hora(HH):rellenar con minutos(mm))", txtHora};
                    int res = JOptionPane.showOptionDialog(this, panelHora, "Creando VIAJE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    try {
                        horaStr = txtHora.getText().trim();
                        hora = java.time.LocalTime.parse(horaStr, timeFormatter);
                        break;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "**Hora de viaje invalida**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                int precio = 0;
                while (true) {
                    JTextField txtPrecio = new JTextField();
                    Object[] panelPrecio = {"Precio:", txtPrecio};
                    int res = JOptionPane.showOptionDialog(this, panelPrecio, "Creando VIAJE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    try {
                        precio = Integer.parseInt(txtPrecio.getText().trim());
                        break;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "**precio de viaje invalido**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                int duracion = 0;
                while (true) {
                    JTextField txtDuracion = new JTextField();
                    Object[] panelDuracion = {"Duración de viaje en minutos:", txtDuracion};
                    int res = JOptionPane.showOptionDialog(this, panelDuracion, "Creando VIAJE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    try {
                        duracion = Integer.parseInt(txtDuracion.getText().trim());
                        break;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "**Duración de viaje invalido**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                String patente = "";
                while (true) {
                    JTextField txtPatente = new JTextField();
                    Object[] panelPatente = {"Patente del bus:", txtPatente};
                    int res = JOptionPane.showOptionDialog(this, panelPatente, "Creando VIAJE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    patente = txtPatente.getText().trim();
                    if (patente.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "**Bus invalido o Bus inexistente**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        break;
                    }
                }

                Object[] opcionesConductores = {"un conductor", "dos conductores", "Cancelar"};
                int numCond = JOptionPane.showOptionDialog(this, "Numero de conductores en el viaje", "Creando VIAJE...",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesConductores, opcionesConductores[0]);
                if (numCond == 2 || numCond == -1) break viajeLoop;
                int cantidadConductores = (numCond == 0) ? 1 : 2;

                class LectorID {
                    utilidades.IdPersona pedir(String titulo) {
                        Object[] opcDoc = {"R.U.T[1]", "Pasaporte[2]", "Cancelar"};
                        int resDoc = JOptionPane.showOptionDialog(null, titulo, titulo,
                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcDoc, opcDoc[0]);
                        if (resDoc == 2 || resDoc == -1) return null; // Canceló

                        if (resDoc == 0) {
                            JTextField txtRut = new JTextField();
                            int resR = JOptionPane.showOptionDialog(null, new Object[]{"R.U.T:", txtRut}, "Creando VIAJE...",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                            if (resR != 0) return null;
                            try { return new utilidades.Rut(txtRut.getText().trim()); } catch (Exception e) { return new utilidades.Rut("0-0"); }
                        } else {
                            JTextField txtPas = new JTextField();
                            JTextField txtNac = new JTextField();
                            int resP = JOptionPane.showOptionDialog(null, new Object[]{"Pasaporte:", txtPas, "Nacionalidad:", txtNac}, "Creando VIAJE...",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                            if (resP != 0) return null;
                            return new utilidades.Pasaporte(txtPas.getText().trim(), txtNac.getText().trim());
                        }
                    }
                }
                LectorID lector = new LectorID();

                utilidades.IdPersona idAux = lector.pedir("Primero: Ingresar ID del Auxiliar");
                if (idAux == null) break viajeLoop;

                utilidades.IdPersona idCond1 = null;
                utilidades.IdPersona idCond2 = null;

                if (cantidadConductores == 1) {
                    idCond1 = lector.pedir("Ingrese ID del Conductor");
                    if (idCond1 == null) break viajeLoop;
                } else {
                    idCond1 = lector.pedir("Ingrese ID del Conductor Nro1");
                    if (idCond1 == null) break viajeLoop;

                    idCond2 = lector.pedir("Ingrese ID del Conductor Nro2");
                    if (idCond2 == null) break viajeLoop;
                }

                String comSalida = "";
                while (true) {
                    JTextField txtSalida = new JTextField();
                    int res = JOptionPane.showOptionDialog(this, new Object[]{"Ingrese comuna de salida", txtSalida}, "Terminal de Salida",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    comSalida = txtSalida.getText().trim();
                    if (comSalida.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "**Terminal inexistente**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else { break; }
                }

                String comLlegada = "";
                while (true) {
                    JTextField txtLlegada = new JTextField();
                    int res = JOptionPane.showOptionDialog(this, new Object[]{"Ingrese comuna de llegada", txtLlegada}, "Terminal de Llegada",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break viajeLoop;

                    comLlegada = txtLlegada.getText().trim();
                    if (comLlegada.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "**Terminal inexistente**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else if (comLlegada.equalsIgnoreCase(comSalida)) {
                        JOptionPane.showMessageDialog(this, "**Terminal ya registrado**", "AVISO", JOptionPane.ERROR_MESSAGE);
                    } else { break; }
                }

                String resumen = "¿Esta seguro de querer crear un viaje?\n" +
                        "Fecha: " + fechaStr + "\n" +
                        "Hora: " + horaStr + "\n" +
                        "Precio: " + precio + "\n" +
                        "Patente bus: " + patente + "\n" +
                        "Comuna de salida: " + comSalida + "\n" +
                        "Comuna de llegada: " + comLlegada;

                int confirmacion = JOptionPane.showOptionDialog(this, resumen, "CONFIRMACIÓN",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Sí", "No"}, "Sí");

                if (confirmacion != 0) {
                    continue;
                }

                try {
                    utilidades.IdPersona[] arregloTripulantes = (cantidadConductores == 1)
                            ? new utilidades.IdPersona[]{idAux, idCond1}
                            : new utilidades.IdPersona[]{idAux, idCond1, idCond2};

                    String[] arregloComunas = new String[]{comSalida, comLlegada};

                    controlador.SistemaVentaPasajes.getInstancia().createViaje(
                            fecha, hora, precio, duracion, patente, arregloTripulantes, arregloComunas);

                    JOptionPane.showMessageDialog(this, "**Viaje guardado exitosamente**", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                    break viajeLoop;

                } catch (excepciones.SVPException ex) {
                    String errorMsg = ex.getMessage();
                    if (errorMsg != null && errorMsg.contains("**Ya existe viaje**")) {
                        JOptionPane.showMessageDialog(this, "**Este viaje ya fue creado antes**", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue viajeLoop;
                    } else {
                        JOptionPane.showMessageDialog(this, errorMsg, "**Error del Sistema**", JOptionPane.ERROR_MESSAGE);
                        break viajeLoop;
                    }
                }
            }
        });

        CREARTERMINALButton.addActionListener(e -> {
            Object[] opcionesSiguiente = {"Siguiente", "Cancelar"};

            outerLoop:
            while (true) {
                JTextField txtNombre = new JTextField();
                Object[] panelNombre = {"Nombre del terminal:", txtNombre};
                int resNombre = JOptionPane.showOptionDialog(this, panelNombre, "Creando TERMINAL...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resNombre != 0 || txtNombre.getText().trim().isEmpty()) break;
                String nombre = txtNombre.getText().trim();

                JTextField txtCalle = new JTextField();
                Object[] panelCalle = {"Calle del terminal:", txtCalle};
                int resCalle = JOptionPane.showOptionDialog(this, panelCalle, "Creando TERMINAL...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resCalle != 0) break;
                String calle = txtCalle.getText().trim();

                JTextField txtNum = new JTextField();
                Object[] panelNum = {"Numero del terminal #:", txtNum};
                int resNum = JOptionPane.showOptionDialog(this, panelNum, "Creando TERMINAL...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resNum != 0) break;
                int numero = 0;
                try {
                    numero = Integer.parseInt(txtNum.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "**El número debe ser un valor entero**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    continue outerLoop;
                }

                JTextField txtComuna = new JTextField();
                Object[] panelComuna = {"Comuna del terminal:", txtComuna};
                int resComuna = JOptionPane.showOptionDialog(this, panelComuna, "Creando TERMINAL...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resComuna != 0) break;
                String comuna = txtComuna.getText().trim();

                Object[] opcionesSiNo = {"Sí", "No"};
                int confirmacion = JOptionPane.showOptionDialog(this,
                        "¿Seguro de crear este terminal?",
                        "CONFIRMACIÓN",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesSiNo, opcionesSiNo[0]);

                if (confirmacion == 0) {
                    try {
                        utilidades.Direccion dir = new utilidades.Direccion(calle, numero, comuna);

                        controlador.ControladorEmpresas.getInstancia().createTerminal(nombre, dir);

                        JOptionPane.showMessageDialog(this, "**Terminal guardado exitosamente**", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                        return;

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    continue outerLoop;
                }
            }
        });

        CREARCLIENTEButton.addActionListener(e -> {
            Object[] opcionesSiguiente = {"Siguiente", "Cancelar"};

            clienteLoop:
            while (true) {
                Object[] opcionesDoc = {"R.U.T [1]", "Pasaporte [2]", "Cancelar"};
                int resDoc = JOptionPane.showOptionDialog(this, "Seleccione identificación:", "Creando CLIENTE...",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesDoc, opcionesDoc[0]);

                if (resDoc == 2 || resDoc == -1) break clienteLoop;
                boolean esRut = (resDoc == 0);

                utilidades.IdPersona idCliente = null;

                if (esRut) {
                    JTextField txtRut = new JTextField();
                    Object[] panelRut = {"R.U.T del cliente:", txtRut};
                    int resRut = JOptionPane.showOptionDialog(this, panelRut, "Creando CLIENTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                    if (resRut != 0) continue clienteLoop;
                    try {
                        idCliente = new utilidades.Rut(txtRut.getText().trim());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "**Formato de RUT inválido**", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue clienteLoop;
                    }
                } else {
                    JTextField txtPasaporte = new JTextField();
                    JTextField txtNacionalidad = new JTextField();
                    Object[] panelPasaporte = {"Pasaporte:", txtPasaporte, "Nacionalidad:", txtNacionalidad};
                    int resPasaporte = JOptionPane.showOptionDialog(this, panelPasaporte, "Creando CLIENTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                    if (resPasaporte != 0) continue clienteLoop;

                    idCliente = new utilidades.Pasaporte(txtPasaporte.getText().trim(), txtNacionalidad.getText().trim());
                }

                boolean clienteYaExiste = false;
                try {
                    java.lang.reflect.Method metodoFind = controlador.SistemaVentaPasajes.class.getDeclaredMethod("findCliente", utilidades.IdPersona.class);
                    metodoFind.setAccessible(true);
                    java.util.Optional<?> optCliente = (java.util.Optional<?>) metodoFind.invoke(controlador.SistemaVentaPasajes.getInstancia(), idCliente);

                    if (optCliente.isPresent()) {
                        clienteYaExiste = true;
                    }
                } catch (Exception ex) {

                }

                if (clienteYaExiste) {
                    JOptionPane.showMessageDialog(this, "**Este cliente ya esta registrado**", "AVISO", JOptionPane.WARNING_MESSAGE);
                    continue;
                }

                Object[] opcionesTitulo = {"Sr [1]", "Sra [2]", "Cancelar"};
                int resTitulo = JOptionPane.showOptionDialog(this, "Trato:", "Creando CLIENTE...",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesTitulo, opcionesTitulo[0]);
                if (resTitulo == 2 || resTitulo == -1) continue clienteLoop;

                utilidades.Tratamiento tratamiento = (resTitulo == 0) ? utilidades.Tratamiento.SR : utilidades.Tratamiento.SRA;

                String nombre = "", apPaterno = "", apMaterno = "";
                while(true) {
                    JTextField txtNombre = new JTextField();
                    JTextField txtApPat = new JTextField();
                    JTextField txtApMat = new JTextField();
                    Object[] panelNombres = {
                            "Nombre:", txtNombre,
                            "Apellido Paterno:", txtApPat,
                            "Apellido Materno:", txtApMat
                    };
                    int resNombres = JOptionPane.showOptionDialog(this, panelNombres, "Creando CLIENTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                    if (resNombres != 0) break;

                    nombre = txtNombre.getText().trim();
                    apPaterno = txtApPat.getText().trim();
                    apMaterno = txtApMat.getText().trim();

                    if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "**La casilla no puede estar vacía**", "AVISO", JOptionPane.WARNING_MESSAGE);
                    } else {
                        break;
                    }
                }
                if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) continue clienteLoop;

                JTextField txtCalle = new JTextField();
                Object[] panelCalle = {"Calle de vivienda:", txtCalle};
                int resCalle = JOptionPane.showOptionDialog(this, panelCalle, "Creando CLIENTE...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                if (resCalle != 0) continue clienteLoop;
                String calle = txtCalle.getText().trim();

                JTextField txtTelefono = new JTextField();
                Object[] panelTelefono = {"Nro telefonico [+11111111111]:", txtTelefono};
                int resTelefono = JOptionPane.showOptionDialog(this, panelTelefono, "Creando CLIENTE...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                if (resTelefono != 0) continue clienteLoop;
                String telefono = txtTelefono.getText().trim();

                JTextField txtCorreoUsuario = new JTextField(10);
                JTextField txtCorreoDominio = new JTextField(10);

                javax.swing.JPanel panelCorreoVisual = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));
                panelCorreoVisual.add(txtCorreoUsuario);
                panelCorreoVisual.add(new javax.swing.JLabel("@"));
                panelCorreoVisual.add(txtCorreoDominio);

                Object[] panelCorreo = {
                        "Ingrese su correo electronico:",
                        panelCorreoVisual
                };
                int resCorreo = JOptionPane.showOptionDialog(this, panelCorreo, "Creando CLIENTE...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                if (resCorreo != 0) continue clienteLoop;

                String correoCompleto = txtCorreoUsuario.getText().trim() + "@" + txtCorreoDominio.getText().trim();

                Object[] opcionesSiNo = {"Sí", "No"};
                int confirmacion = JOptionPane.showOptionDialog(this,
                        "¿Desea registrar como cliente?",
                        "CONFIRMACIÓN",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesSiNo, opcionesSiNo[0]);

                if (confirmacion == 0) {
                    try {
                        utilidades.Nombre nomObj = new utilidades.Nombre(tratamiento, nombre, apPaterno, apMaterno);

                        controlador.SistemaVentaPasajes.getInstancia().createCliente(idCliente, nomObj, telefono, correoCompleto);

                        JOptionPane.showMessageDialog(this, "**Cliente creado exitosamente**", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                        break clienteLoop;

                    } catch (Exception ex) {
                        String msg = ex.getMessage();
                        if (msg != null && msg.contains("**Ya existe cliente**")) {
                            JOptionPane.showMessageDialog(this, "**Este cliente ya esta registrado**", "AVISO", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + msg, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        continue clienteLoop;
                    }
                } else {
                    continue clienteLoop;
                }
            }
        });

        CREARTRIPULANTEButton.addActionListener(e -> {
            Object[] opcionesSiguiente = {"Siguiente", "Cancelar"};

            empresaLoop:
            while (true) {
                JTextField txtRutEmpresa = new JTextField();
                Object[] panelRutEmp = {"R.U.T empresa [11111111-1]:", txtRutEmpresa};
                int resRutEmp = JOptionPane.showOptionDialog(this, panelRutEmp, "Contratar TRIPULANTE...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                if (resRutEmp != 0) break empresaLoop;

                String rutEmpresaInput = txtRutEmpresa.getText().trim();
                String rutEmpresaLimpio = rutEmpresaInput.replaceAll("[^0-9Kk]", "");

                boolean empresaExiste = false;
                String[][] matrizEmpresas = controlador.ControladorEmpresas.getInstancia().listEmpresas();
                for (String[] fila : matrizEmpresas) {
                    if (fila[0].replaceAll("[^0-9Kk]", "").equals(rutEmpresaLimpio)) {
                        empresaExiste = true;
                        break;
                    }
                }

                if (!empresaExiste) {
                    JOptionPane.showMessageDialog(this, "**No existe empresa con el rut indicado**", "ERROR", JOptionPane.ERROR_MESSAGE);
                    continue empresaLoop;
                }
                utilidades.Rut rutEmpresaReal = null;
                try {
                    java.lang.reflect.Field campoEmpresas = controlador.ControladorEmpresas.class.getDeclaredField("empresas");
                    campoEmpresas.setAccessible(true);
                    java.util.ArrayList<?> listaReal = (java.util.ArrayList<?>) campoEmpresas.get(controlador.ControladorEmpresas.getInstancia());
                    for (Object emp : listaReal) {
                        java.lang.reflect.Method metodoGetRut = emp.getClass().getMethod("getRut");
                        Object rutExtraido = metodoGetRut.invoke(emp);
                        if (rutExtraido.toString().replaceAll("[^0-9Kk]", "").equals(rutEmpresaLimpio)) {
                            rutEmpresaReal = (utilidades.Rut) rutExtraido;
                            break;
                        }
                    }
                } catch (Exception ex) {

                }

                utilidades.Rut rutEmpresaFinal = (rutEmpresaReal != null) ? rutEmpresaReal : new utilidades.Rut(rutEmpresaInput);

                datosTripulanteLoop:
                while(true) {
                    Object[] opcionesTipo = {"Contratar Auxiliar [1]", "Contratar Conductor [2]", "Cancelar"};
                    int resTipo = JOptionPane.showOptionDialog(this, "¿Qué desea registrar?", "Contratar TRIPULANTE...",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesTipo, opcionesTipo[0]);

                    if (resTipo == 2 || resTipo == -1) break empresaLoop;
                    String tipoCargo = (resTipo == 0) ? "Auxiliar" : "Conductor";

                    Object[] opcionesDoc = {"R.U.T [1]", "Pasaporte [2]", "Cancelar"};
                    int resDoc = JOptionPane.showOptionDialog(this, "Seleccione identificación:", "Contratar TRIPULANTE...",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesDoc, opcionesDoc[0]);

                    if (resDoc == 2 || resDoc == -1) continue datosTripulanteLoop;
                    boolean esRut = (resDoc == 0);

                    utilidades.IdPersona idTripulante = null;

                    if (esRut) {
                        JTextField txtRutTrip = new JTextField();
                        Object[] panelRutTrip = {"R.U.T del tripulante:", txtRutTrip};
                        int resRutTrip = JOptionPane.showOptionDialog(this, panelRutTrip, "Contratar TRIPULANTE...",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                        if (resRutTrip != 0) continue datosTripulanteLoop;
                        try {
                            idTripulante = new utilidades.Rut(txtRutTrip.getText().trim());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Formato de RUT inválido", "Error", JOptionPane.ERROR_MESSAGE);
                            continue datosTripulanteLoop;
                        }
                    } else {
                        JTextField txtPasaporte = new JTextField();
                        JTextField txtNacionalidad = new JTextField();
                        Object[] panelPasaporte = {"Pasaporte:", txtPasaporte, "Nacionalidad:", txtNacionalidad};
                        int resPasaporte = JOptionPane.showOptionDialog(this, panelPasaporte, "Contratar TRIPULANTE...",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                        if (resPasaporte != 0) continue datosTripulanteLoop;

                        idTripulante = new utilidades.Pasaporte(txtPasaporte.getText().trim(), txtNacionalidad.getText().trim());
                    }

                    Object[] opcionesTitulo = {"Sr [1]", "Sra [2]", "Cancelar"};
                    int resTitulo = JOptionPane.showOptionDialog(this, "Trato:", "Contratar TRIPULANTE...",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesTitulo, opcionesTitulo[0]);
                    if (resTitulo == 2 || resTitulo == -1) continue datosTripulanteLoop;

                    utilidades.Tratamiento tratamiento = (resTitulo == 0) ? utilidades.Tratamiento.SR : utilidades.Tratamiento.SRA;
                    String sufijo = (resTitulo == 0) ? "o" : "a";

                    String nombre = "", apPaterno = "", apMaterno = "";
                    while(true) {
                        JTextField txtNombre = new JTextField();
                        JTextField txtApPat = new JTextField();
                        JTextField txtApMat = new JTextField();
                        Object[] panelNombres = {
                                "Nombre:", txtNombre,
                                "Apellido Paterno:", txtApPat,
                                "Apellido Materno:", txtApMat
                        };
                        int resNombres = JOptionPane.showOptionDialog(this, panelNombres, "Contratar TRIPULANTE...",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);

                        if (resNombres != 0) break;

                        nombre = txtNombre.getText().trim();
                        apPaterno = txtApPat.getText().trim();
                        apMaterno = txtApMat.getText().trim();

                        if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "**Las casillas no pueden estar vacías**", "AVISO", JOptionPane.WARNING_MESSAGE);
                        } else {
                            break;
                        }
                    }
                    if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) continue datosTripulanteLoop;

                    JTextField txtCalle = new JTextField();
                    Object[] panelCalle = {"Calle de vivienda:", txtCalle};
                    int resCalle = JOptionPane.showOptionDialog(this, panelCalle, "Contratar TRIPULANTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                    if (resCalle != 0) continue datosTripulanteLoop;
                    String calle = txtCalle.getText().trim();

                    JTextField txtNum = new JTextField();
                    Object[] panelNum = {"Numero de vivienda #:", txtNum};
                    int resNum = JOptionPane.showOptionDialog(this, panelNum, "Contratar TRIPULANTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                    if (resNum != 0) continue datosTripulanteLoop;
                    int numeroVivienda = 0;
                    try {
                        numeroVivienda = Integer.parseInt(txtNum.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "**El número debe ser un valor entero**", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue datosTripulanteLoop;
                    }

                    JTextField txtComuna = new JTextField();
                    Object[] panelComuna = {"Comuna de vivienda:", txtComuna};
                    int resComuna = JOptionPane.showOptionDialog(this, panelComuna, "Contratar TRIPULANTE...",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesSiguiente, opcionesSiguiente[0]);
                    if (resComuna != 0) continue datosTripulanteLoop;
                    String comuna = txtComuna.getText().trim();

                    Object[] opcionesSiNo = {"Sí", "No"};
                    int confirmacion = JOptionPane.showOptionDialog(this,
                            "¿Esta seguro de contratar a este tripulante?",
                            "Confirmar",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesSiNo, opcionesSiNo[0]);

                    if (confirmacion == 0) {
                        try {
                            utilidades.Direccion dir = new utilidades.Direccion(calle, numeroVivienda, comuna);

                            utilidades.Nombre nomObj = new utilidades.Nombre(tratamiento, nombre, apPaterno, apMaterno);

                            if (tipoCargo.equals("Auxiliar")) {
                                controlador.ControladorEmpresas.getInstancia().hireAuxiliarForEmpresa(rutEmpresaFinal, idTripulante, nomObj, dir);
                            } else {
                                controlador.ControladorEmpresas.getInstancia().hireConductorForEmpresa(rutEmpresaFinal, idTripulante, nomObj, dir);
                            }

                            String mensajeExito = tipoCargo + " **contratad" + sufijo + " exitosamente**";
                            JOptionPane.showMessageDialog(this, mensajeExito, "ÉXITO", JOptionPane.INFORMATION_MESSAGE);

                            break empresaLoop;

                        } catch (Exception ex) {
                            String errorMsg = ex.getMessage();
                            if (errorMsg != null && errorMsg.contains("**Este tripulante Ya esta contratado**")) {
                                JOptionPane.showMessageDialog(this,
                                        "**No se pudo registrar** CAUSAS POSIBLES:\n" +
                                                "1. Este tripulante ya existe.\n" +
                                                "2. La empresa alcanzó su límite máximo de tripulantes.",
                                        "AVISO", JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this, "Error al guardar: " + errorMsg, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            continue datosTripulanteLoop;
                        }
                    } else {
                        continue datosTripulanteLoop;
                    }
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {

        dispose();
    }

    public static void main(String[] args) {
        MenuCrear dialog = new MenuCrear();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
