//Tomás Meza

package vista;

import javax.swing.*;
import modelo.TipoDocumento;
import controlador.SistemaVentaPasajes;
import utilidades.IdPersona;

public class MenuVentas extends JDialog {
    private JPanel contentPane;
    private JButton GENERARPASAJESVENTAButton;
    private JButton buttonVOLVER;
    private JButton VENDERPASAJESButton;

    public MenuVentas() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Gestión de Ventas");
        setSize(400, 300);

        VENDERPASAJESButton.addActionListener(e -> {
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            controlador.SistemaVentaPasajes sistema = controlador.SistemaVentaPasajes.getInstancia();

            ventaLoop:
            while (true) {
                String idDocumento = "";
                while (true) {
                    javax.swing.JTextField txtIdDoc = new javax.swing.JTextField();
                    int res = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Ingrese ID Documento:", txtIdDoc}, "Ingrese ID...",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break ventaLoop;

                    idDocumento = txtIdDoc.getText().trim();
                    if (!idDocumento.isEmpty()) break;
                    javax.swing.JOptionPane.showMessageDialog(this, "**Debe ingresar un ID válido**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                }

                Object[] opcDoc = {"Boleta[1]", "Factura[2]", "Cancelar"};
                int resTipo = javax.swing.JOptionPane.showOptionDialog(this, "Seleccione el tipo de documento:", "Ingrese DOCUMENTO...",
                        javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opcDoc, opcDoc[0]);
                if (resTipo == 2 || resTipo == -1) break ventaLoop;
                modelo.TipoDocumento tipoDoc = (resTipo == 0) ? modelo.TipoDocumento.BOLETA : modelo.TipoDocumento.FACTURA;

                java.time.LocalDate fecha = null;
                while (true) {
                    javax.swing.JTextField txtFecha = new javax.swing.JTextField("dd/MM/yyyy");
                    int res = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Ingrese Fecha [dd/MM/yyyy]:", txtFecha}, "Ingrese FECHA...",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break ventaLoop;

                    try {
                        fecha = java.time.LocalDate.parse(txtFecha.getText().trim(), dateFormatter);
                        break;
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Fecha de viaje inválida**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }

                String comSalida = "";
                while (true) {
                    javax.swing.JTextField txtSalida = new javax.swing.JTextField();
                    int res = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Ingrese comuna de salida", txtSalida}, "Terminal de Salida",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break ventaLoop;

                    comSalida = txtSalida.getText().trim();
                    if (comSalida.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Comuna no puede estar vacía**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    } else { break; }
                }

                String comLlegada = "";
                while (true) {
                    javax.swing.JTextField txtLlegada = new javax.swing.JTextField();
                    int res = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Ingrese comuna de llegada", txtLlegada}, "Terminal de Llegada",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break ventaLoop;

                    comLlegada = txtLlegada.getText().trim();
                    if (comLlegada.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Comuna no puede estar vacía**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    } else if (comLlegada.equalsIgnoreCase(comSalida)) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Destino no puede ser igual al origen**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    } else { break; }
                }

                utilidades.IdPersona idCliente = null;
                clienteLoop:
                while (true) {
                    Object[] opcId = {"Rut[1]", "Pasaporte[2]", "Cancelar"};
                    int resId = javax.swing.JOptionPane.showOptionDialog(this, "Identificar Cliente (Comprador):", "Ingrese CLIENTE...",
                            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opcId, opcId[0]);

                    if (resId == 2 || resId == -1) break ventaLoop;

                    if (resId == 0) {
                        javax.swing.JTextField txtRut = new javax.swing.JTextField();
                        int r = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"R.U.T (Ej: 11111111-1):", txtRut}, "Ingrese R.U.T...",
                                javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                        if (r != 0) break ventaLoop;
                        try {
                            idCliente = new utilidades.Rut(txtRut.getText().replaceAll("\\s", "").toUpperCase());
                            break clienteLoop;
                        } catch (Exception ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, "**RUT inválido. Revise el formato ingresado**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        javax.swing.JTextField txtPas = new javax.swing.JTextField();
                        javax.swing.JTextField txtNac = new javax.swing.JTextField();
                        int p = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Pasaporte:", txtPas, "Nacionalidad:", txtNac}, "Ingrese PASAPORTE...",
                                javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                        if (p != 0) break ventaLoop;
                        idCliente = new utilidades.Pasaporte(txtPas.getText().trim(), txtNac.getText().trim().toUpperCase());
                        break clienteLoop;
                    }
                }

                int cantidadPasajes = 0;
                while (true) {
                    javax.swing.JTextField txtCant = new javax.swing.JTextField();
                    int res = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Ingrese cantidad de pasajes por pagar", txtCant}, "Ingrese CANTIDAD...",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                    if (res != 0) break ventaLoop;

                    try {
                        cantidadPasajes = Integer.parseInt(txtCant.getText().trim());
                        if (cantidadPasajes <= 0) throw new NumberFormatException();
                        break;
                    } catch (NumberFormatException ex) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Cantidad de pasajes inválida**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }

                String[][] viajesDisponibles = sistema.getHorariosDisponibles(fecha, comSalida, comLlegada, cantidadPasajes);

                if (viajesDisponibles == null || viajesDisponibles.length == 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "**No existe un viaje disponible para esa ruta, fecha, o no hay asientos suficientes**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    continue ventaLoop;
                }

                String[] opcionesViaje = new String[viajesDisponibles.length];
                for (int i = 0; i < viajesDisponibles.length; i++) {
                    opcionesViaje[i] = "Bus: " + viajesDisponibles[i][0] + " | Hora: " + viajesDisponibles[i][1] + " | Precio: $" + viajesDisponibles[i][2];
                }

                String seleccionViaje = (String) javax.swing.JOptionPane.showInputDialog(this, "Seleccione el viaje:", "Viajes Disponibles",
                        javax.swing.JOptionPane.QUESTION_MESSAGE, null, opcionesViaje, opcionesViaje[0]);

                if (seleccionViaje == null) break ventaLoop;

                int idxViaje = 0;
                for(int i = 0; i < opcionesViaje.length; i++) { if(opcionesViaje[i].equals(seleccionViaje)) idxViaje = i; }

                String patenteSeleccionada = viajesDisponibles[idxViaje][0];
                java.time.LocalTime horaSeleccionada = java.time.LocalTime.parse(viajesDisponibles[idxViaje][1]);
                String precioViaje = viajesDisponibles[idxViaje][2];
                String disponiblesViaje = viajesDisponibles[idxViaje][3];

                String formatoBus = String.format("%-20s %-20s %-20s %-20s\n", "BUS", "HORA", "PRECIO", "DISPONIBLES");
                formatoBus += String.format("%-20s %-20s %-20s %-20s", patenteSeleccionada, horaSeleccionada.toString(), precioViaje, disponiblesViaje);

                javax.swing.JTextArea areaInfo = new javax.swing.JTextArea(formatoBus);
                areaInfo.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
                areaInfo.setEditable(false);
                areaInfo.setOpaque(false);

                int resInfo = javax.swing.JOptionPane.showOptionDialog(this, areaInfo, "Información del Viaje",
                        javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");
                if (resInfo != 0) break ventaLoop;

                String[] estadoAsientos = sistema.listAsientosDeViaje(fecha, horaSeleccionada, patenteSeleccionada);

                if (estadoAsientos == null || estadoAsientos.length == 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "**Viaje sin asientos disponibles**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break ventaLoop;
                }

                java.util.List<Integer> asientosSeleccionados = new java.util.ArrayList<>();
                while (true) {
                    javax.swing.JPanel pnlAsientos = new javax.swing.JPanel(new java.awt.GridLayout(0, 4, 5, 5));
                    javax.swing.JCheckBox[] cajasAsientos = new javax.swing.JCheckBox[estadoAsientos.length];
                    javax.swing.JLabel lblInstruccion = new javax.swing.JLabel("Seleccione " + cantidadPasajes + " asiento(s):");

                    for (int i = 0; i < estadoAsientos.length; i++) {
                        boolean ocupado = estadoAsientos[i].equals("*");
                        String textoNum = String.format("[ %2d ]", (i + 1));
                        cajasAsientos[i] = new javax.swing.JCheckBox(textoNum);

                        if (ocupado) {
                            cajasAsientos[i].setForeground(java.awt.Color.RED);
                            cajasAsientos[i].setEnabled(false);
                        }

                        final int finalCantidad = cantidadPasajes;
                        cajasAsientos[i].addItemListener(ev -> {
                            long seleccionadosAhora = java.util.Arrays.stream(cajasAsientos).filter(c -> c != null && c.isSelected()).count();
                            if (seleccionadosAhora > finalCantidad) {
                                ((javax.swing.JCheckBox)ev.getItem()).setSelected(false);
                            }
                        });
                        pnlAsientos.add(cajasAsientos[i]);
                    }

                    javax.swing.JPanel panelContenedor = new javax.swing.JPanel(new java.awt.BorderLayout());
                    panelContenedor.add(lblInstruccion, java.awt.BorderLayout.NORTH);
                    panelContenedor.add(new javax.swing.JScrollPane(pnlAsientos), java.awt.BorderLayout.CENTER);
                    panelContenedor.setPreferredSize(new java.awt.Dimension(350, 400));

                    int resAsientos = javax.swing.JOptionPane.showOptionDialog(this, panelContenedor, "Seleccione asientos...",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Siguiente", "Cancelar"}, "Siguiente");

                    if (resAsientos != 0) break ventaLoop;

                    asientosSeleccionados.clear();
                    for (int i = 0; i < cajasAsientos.length; i++) {
                        if (cajasAsientos[i].isSelected()) {
                            asientosSeleccionados.add(i + 1);
                        }
                    }

                    if (asientosSeleccionados.size() < cantidadPasajes) {
                        javax.swing.JOptionPane.showMessageDialog(this, "**Falta seleccionar asientos. Debe elegir**" + cantidadPasajes, "ERROR", javax.swing.JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    break;
                }

                try {
                    sistema.iniciaVenta(idDocumento, tipoDoc, fecha, comSalida, comLlegada, cantidadPasajes, idCliente);

                    for (int asiento : asientosSeleccionados) {
                        utilidades.IdPersona idPasajero = null;

                        while (true) {
                            Object[] opcId = {"Rut[1]", "Pasaporte[2]", "Mismo Cliente[3]"};
                            int resId = javax.swing.JOptionPane.showOptionDialog(this,
                                    "Identificar PASAJERO para el asiento [ " + asiento + " ]:",
                                    "ASIGNAR PASAJERO",
                                    javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opcId, opcId[0]);
                            if (resId == -1) throw new Exception("Venta cancelada.");

                            if (resId == 2) {
                                idPasajero = idCliente;
                                break;
                            } else if (resId == 0) {
                                javax.swing.JTextField txtRut = new javax.swing.JTextField();
                                int r = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"R.U.T del pasajero que viajará:", txtRut}, "Asiento " + asiento,
                                        javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Aceptar");
                                if (r != 0) throw new Exception("Venta cancelada.");
                                try {
                                    idPasajero = new utilidades.Rut(txtRut.getText().replaceAll("\\s", "").toUpperCase());
                                    break;
                                } catch (Exception ex) {
                                    javax.swing.JOptionPane.showMessageDialog(this, "**RUT inválido**", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                                    continue;
                                }
                            } else {
                                javax.swing.JTextField txtPas = new javax.swing.JTextField();
                                javax.swing.JTextField txtNac = new javax.swing.JTextField();
                                int p = javax.swing.JOptionPane.showOptionDialog(this, new Object[]{"Pasaporte:", txtPas, "Nacionalidad:", txtNac}, "Asiento " + asiento,
                                        javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Aceptar");
                                if (p != 0) throw new Exception("Venta cancelada.");
                                idPasajero = new utilidades.Pasaporte(txtPas.getText().trim(), txtNac.getText().trim().toUpperCase());
                                break;
                            }
                        }

                        boolean asignadoExitosamente = false;
                        while (!asignadoExitosamente) {
                            try {
                                sistema.vendePasaje(idDocumento, tipoDoc, fecha, horaSeleccionada, patenteSeleccionada, asiento, idPasajero);
                                asignadoExitosamente = true;
                            } catch (excepciones.SVPException ex) {
                                if (ex.getMessage().contains("No existe pasajero con el id indicado")) {

                                    javax.swing.JOptionPane.showMessageDialog(this, "**Pasajero no existe, se ingresaran sus datos**", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);

                                    javax.swing.JRadioButton checkSr = new javax.swing.JRadioButton("SR");
                                    javax.swing.JRadioButton checkSra = new javax.swing.JRadioButton("SRA");
                                    javax.swing.ButtonGroup grupoTrato = new javax.swing.ButtonGroup();
                                    grupoTrato.add(checkSr); grupoTrato.add(checkSra);
                                    checkSr.setSelected(true);

                                    javax.swing.JPanel pnlTrato = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                                    pnlTrato.add(checkSr); pnlTrato.add(checkSra);

                                    javax.swing.JTextField txtNombre = new javax.swing.JTextField();
                                    javax.swing.JTextField txtApePat = new javax.swing.JTextField();
                                    javax.swing.JTextField txtApeMat = new javax.swing.JTextField();
                                    javax.swing.JTextField txtTelefono = new javax.swing.JTextField("[+11111111111]");

                                    Object[] camposFormulario = {
                                            "Trato:", pnlTrato,
                                            "Nombre:", txtNombre,
                                            "Apellido Paterno:", txtApePat,
                                            "Apellido Materno:", txtApeMat,
                                            "Teléfono del pasajero [+11111111111]:", txtTelefono
                                    };

                                    int resReg = javax.swing.JOptionPane.showConfirmDialog(this, camposFormulario, "Registrar Datos del Pasajero", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

                                    if (resReg == javax.swing.JOptionPane.OK_OPTION) {
                                        String nom = txtNombre.getText().trim();
                                        String apePat = txtApePat.getText().trim();
                                        String apeMat = txtApeMat.getText().trim();
                                        String tel = txtTelefono.getText().trim();

                                        utilidades.Tratamiento tratoSeleccionado = checkSr.isSelected() ? utilidades.Tratamiento.SR : utilidades.Tratamiento.SRA;

                                        utilidades.Nombre nombreCompleto = new utilidades.Nombre(tratoSeleccionado, nom, apePat, apeMat);

                                        utilidades.Nombre nombreContactoVacio = new utilidades.Nombre(utilidades.Tratamiento.SR, "", "", "");

                                        sistema.createPasajero(idPasajero, nombreCompleto, tel, nombreContactoVacio, "");

                                    } else {
                                        throw new Exception("**Venta cancelada. Es obligatorio registrar al pasajero para continuar**");
                                    }
                                } else {
                                    throw ex;
                                }
                            }
                        }
                    }

                    Object[] opcPago = {"Efectivo[1]", "Tarjeta[2]", "Cancelar"};
                    int resPago = javax.swing.JOptionPane.showOptionDialog(this, "Seleccione método de pago", "Método de Pago...",
                            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opcPago, opcPago[0]);

                    if (resPago == 2 || resPago == -1) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Venta cancelada.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                        break ventaLoop;
                    }

                    if (resPago == 0) {
                        sistema.pagaVenta(idDocumento, tipoDoc);
                    } else {
                        String strTarjeta = javax.swing.JOptionPane.showInputDialog(this, "Ingrese el número de la tarjeta:");
                        if(strTarjeta == null || strTarjeta.trim().isEmpty()) {
                            javax.swing.JOptionPane.showMessageDialog(this, "**Número inválido, venta cancelada**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                            break ventaLoop;
                        }
                        long nroTarjeta = Long.parseLong(strTarjeta.trim());
                        sistema.pagaVenta(idDocumento, tipoDoc, nroTarjeta);
                    }

                    sistema.generatePasajesVenta(idDocumento, tipoDoc);

                    sistema.saveDatosSistema();

                    javax.swing.JOptionPane.showMessageDialog(this, "**Venta realizada exitosamente**", "ÉXITO", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break ventaLoop;

                } catch (excepciones.SVPException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break ventaLoop;
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "**Número de tarjeta inválido**", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break ventaLoop;
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Aviso: " + ex.getMessage(), "AVISO", javax.swing.JOptionPane.WARNING_MESSAGE);
                    break ventaLoop;
                }
            }
        });

        GENERARPASAJESVENTAButton.addActionListener(e -> {
            try {
                String idDoc = JOptionPane.showInputDialog(this, "ID del documento:");
                if (idDoc == null) return;

                TipoDocumento tipo = seleccionarTipoDocumento();
                if (tipo != null) {
                    SistemaVentaPasajes.getInstancia().generatePasajesVenta(idDoc, tipo);
                    JOptionPane.showMessageDialog(this, "Pasajes generados exitosamente.");
                    String nombreArchivo = idDoc + tipo.toString().toLowerCase() + ".txt";
                    java.io.File archivo = new java.io.File(nombreArchivo);

                    if (archivo.exists()) {
                        StringBuilder contenido = new StringBuilder();
                        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
                            String linea;
                            while ((linea = br.readLine()) != null) {
                                contenido.append(linea).append("\n");
                            }
                        }
                        JTextArea textArea = new JTextArea(contenido.toString());
                        textArea.setEditable(false);
                        textArea.setOpaque(false);
                        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(650, 400));

                        JOptionPane.showMessageDialog(this, scrollPane, "Documento: " + nombreArchivo, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo encontrar el archivo generado: " + nombreArchivo, "Error de Lectura", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        buttonVOLVER.addActionListener(e -> dispose());
    }

    private TipoDocumento seleccionarTipoDocumento() {
        TipoDocumento[] opciones = TipoDocumento.values();
        return (TipoDocumento) JOptionPane.showInputDialog(this,
                "Seleccione tipo de documento:", "Tipo de Venta",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
    }
}
