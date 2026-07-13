//Tomás Meza

package vista;

import controlador.SistemaVentaPasajes;

import javax.swing.*;

public class MenuSistema extends JDialog {
    private JPanel contentPane;
    private JButton buttonVOLVER;
    private JButton GUARDARDATOSDELSISTEMAButton;
    private JButton LEERDATOSDELSISTEMAButton;
    private JButton LEERDATOSINICIALESButton;

    public MenuSistema() {
        setContentPane(contentPane);
        setModal(true);
        buttonVOLVER.addActionListener(e -> dispose());

        LEERDATOSINICIALESButton.addActionListener(e -> {
            mostrarMensajeTemporal("leyendo datos iniciales...", "PROCESANDO", 1000);

            java.nio.file.Path path = java.nio.file.Paths.get("SVPDatosIniciales.txt");
            String contenidoOriginal = "";
            boolean procesoIniciado = false;

            try {
                contenidoOriginal = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
                procesoIniciado = true;

                SistemaVentaPasajes.getInstancia().readDatosIniciales();

            } catch (Exception ex) {
                System.out.println("Nota: El lector interno reportó un ajuste: " + ex.getMessage());
            } finally {
                if (procesoIniciado && !contenidoOriginal.isEmpty()) {
                    try {
                        java.nio.file.Files.write(path, contenidoOriginal.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                    } catch (Exception ex) {
                        System.err.println("Error al verificar consistencia: " + ex.getMessage());
                    }
                }
            }
            if (procesoIniciado) {
                StringBuilder sb = new StringBuilder();
                sb.append("-------------------------------------------------\n");
                sb.append("...:::DATOS INICIALES CARGADOS CON ÉXITO::::....\n");
                sb.append("-------------------------------------------------\n\n");

                String[] lineas = contenidoOriginal.split("\\r?\\n");
                int seccionActual = 0;

                String[] titulosSecciones = {
                        " SECCIÓN 1: PASAJEROS Y COPROPIETARIOS ",
                        " SECCIÓN 2: EMPRESAS REGISTRADAS ",
                        " SECCIÓN 3: TRIPULACIÓN (CONDUCTORES Y AUXILIARES) ",
                        " SECCIÓN 4: TERMINALES DE AUTOBUS ",
                        " SECCIÓN 5: FLOTA DE BUSES ",
                        " SECCIÓN 6: VIAJES PROGRAMADOS "
                };

                sb.append(titulosSecciones[0]).append("\n----------------------------------------------------------------------\n");

                for (String linea : lineas) {
                    linea = linea.trim();
                    if (linea.isEmpty()) continue;

                    if (linea.equals("+")) {
                        seccionActual++;
                        if (seccionActual < titulosSecciones.length) {
                            sb.append("\n").append(titulosSecciones[seccionActual])
                                    .append("\n----------------------------------------------------------------------\n");
                        }
                        continue;
                    }

                    String[] datos = linea.split(";");

                    switch (seccionActual) {
                        case 0:
                            if (datos.length >= 6) {
                                sb.append("• Tipo: ").append(datos[0])
                                        .append(" | RUT: ").append(datos[1])
                                        .append(" | Nombre: ").append(datos[3]).append(" ").append(datos[4]).append("\n");
                            }
                            break;
                        case 1:
                            if (datos.length >= 3) {
                                sb.append("• RUT: ").append(datos[0])
                                        .append(" | Nombre: ").append(datos[1])
                                        .append(" | URL: ").append(datos[2]).append("\n");
                            }
                            break;
                        case 2:
                            if (datos.length >= 6) {
                                String cargo = datos[0].equals("A") ? "Auxiliar" : "Conductor";
                                sb.append("• Cargo: ").append(cargo)
                                        .append(" | RUT: ").append(datos[1])
                                        .append(" | Nombre: ").append(datos[3]).append(" ").append(datos[4]).append("\n");
                            }
                            break;
                        case 3:
                            if (datos.length >= 4) {
                                sb.append("• Ciudad: ").append(datos[3])
                                        .append(" | Terminal: ").append(datos[0])
                                        .append(" | Dirección: ").append(datos[1]).append(" #").append(datos[2]).append("\n");
                            }
                            break;
                        case 4:
                            if (datos.length >= 4) {
                                sb.append("• Patente: ").append(datos[0])
                                        .append(" | Marca: ").append(datos[1]).append(" ").append(datos[2])
                                        .append(" | Asientos: ").append(datos[3]).append("\n");
                            }
                            break;
                        case 5:
                            if (datos.length >= 9) {
                                sb.append("• Fecha: ").append(datos[0]).append(" (").append(datos[1]).append(" hrs)")
                                        .append(" | Bus: ").append(datos[4])
                                        .append(" | Ruta: ").append(datos[7]).append(" ➔ ").append(datos[8]).append("\n");
                            }
                            break;
                    }
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new java.awt.Dimension(580, 380));

                JOptionPane.showMessageDialog(this, scrollPane, "Éxito - Carga Inicial", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        GUARDARDATOSDELSISTEMAButton.addActionListener(e -> {
            Object[] opcionesSiNo = {"Sí", "No"};

            int confirmar = JOptionPane.showOptionDialog(this,
                    "¿Estas seguro que quieres guardar los datos?",
                    "Confirmación de Guardado",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opcionesSiNo, opcionesSiNo[0]);

            if (confirmar == JOptionPane.YES_OPTION) {
                mostrarMensajeTemporal("Guardando datos...", "PROCESANDO", 2000);

                try {
                    SistemaVentaPasajes.getInstancia().saveDatosSistema();

                    String[][] empresas = controlador.ControladorEmpresas.getInstancia().listEmpresas();

                    StringBuilder sb = new StringBuilder();
                    sb.append("DATOS GUARDADOS CORRECTAMENTE\n");
                    sb.append("Nro empresas: ").append(empresas.length).append("\n\n");

                    for (int i = 0; i < empresas.length; i++) {
                        sb.append("Empresa ").append(i + 1).append(":\n");
                        sb.append("R.U.T: ").append(empresas[i][0]).append("\n");
                        sb.append("Nombre: ").append(empresas[i][1]).append("\n");
                        sb.append("URL: ").append(empresas[i][2]).append("\n");
                        sb.append("Buses registrados: ").append(empresas[i][4]).append("\n");
                        sb.append("--------------------------------------\n");
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(380, 250));

                    JOptionPane.showMessageDialog(this, scrollPane, "ÉXITO", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        LEERDATOSDELSISTEMAButton.addActionListener(e -> {
            java.io.File archivo = new java.io.File("controladores.dat");
            if (!archivo.exists() || archivo.length() == 0) {
                JOptionPane.showMessageDialog(this, "**No hay datos por cargar**", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object[] opcionesSiNo = {"Sí", "No"};

            int confirmar = JOptionPane.showOptionDialog(this,
                    "¿Esta seguro que quiere cargar los datos guardados?",
                    "CONFIRMACIÓN",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opcionesSiNo, opcionesSiNo[0]);

            if (confirmar == JOptionPane.YES_OPTION) {
                mostrarMensajeTemporal("Recuperando datos del sistema...", "PROCESANDO", 2000);

                try {
                    SistemaVentaPasajes.getInstancia().readDatosSistema();

                    String[][] empresas = controlador.ControladorEmpresas.getInstancia().listEmpresas();

                    StringBuilder sb = new StringBuilder();
                    sb.append("DATOS CARGADOS CORRECTAMENTE\n");
                    sb.append("Nro empresas: ").append(empresas.length).append("\n\n");

                    for (int i = 0; i < empresas.length; i++) {
                        sb.append("Empresa ").append(i + 1).append(":\n");
                        sb.append("R.U.T: ").append(empresas[i][0]).append("\n");
                        sb.append("Nombre: ").append(empresas[i][1]).append("\n");
                        sb.append("URL: ").append(empresas[i][2]).append("\n");
                        sb.append("Buses registrados: ").append(empresas[i][4]).append("\n");
                        sb.append("--------------------------------------\n");
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(380, 250));

                    JOptionPane.showMessageDialog(this, scrollPane, "ÉXITO", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "**No hay datos por cargar**", "AVISO", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void mostrarMensajeTemporal(String mensaje, String titulo, int milisegundos) {
        JOptionPane pane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog(this, titulo);

        Timer timer = new Timer(milisegundos, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        MenuSistema dialog = new MenuSistema();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
