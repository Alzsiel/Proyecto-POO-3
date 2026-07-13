//Juan Henríquez

package vista;
import controlador.SistemaVentaPasajes;
import javax.swing.*;

public class MenuConsultas extends JDialog {
    private JPanel contentPane;
    private JButton buttonVOLVER;
    private JButton LISTARLLEGADASYSALIDASButton;
    private JButton LISTARVENTASButton;
    private JButton LISTARVIAJESButton;

    public MenuConsultas() {
        setContentPane(contentPane);
        setModal(true);

        LISTARLLEGADASYSALIDASButton.addActionListener(e -> {

        });

        LISTARVENTASButton.addActionListener(e -> {
            try {
                String[][] datos = SistemaVentaPasajes.getInstancia().listVentas();
                JOptionPane.showMessageDialog(this, "Total ventas registradas: " + datos.length);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        LISTARVIAJESButton.addActionListener(e -> {
            try {
                String[][] datos = SistemaVentaPasajes.getInstancia().listViajes();
                JOptionPane.showMessageDialog(this, "Total viajes: " + datos.length);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        buttonVOLVER.addActionListener(e -> dispose());
    }


    private void onVOLVER() {
        dispose();
    }

    public static void main(String[] args) {
        MenuConsultas dialog = new MenuConsultas();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
