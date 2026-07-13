package vista;

import javax.swing.*;
import java.awt.event.*;

public class pag1 extends JDialog {
    private JPanel contentPane;
    private JButton CONSULTASButton;
    private JButton VENTASButton1;
    private JButton CREARButton;
    private JButton SISTEMAButton;
    private JButton SALIRButton;
    private JButton button1;
    private JLabel lblEstado;


    public pag1() {
        setContentPane(contentPane);
        setModal(true);
        configurarListeners();

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void configurarListeners() {
        SISTEMAButton.addActionListener(e -> {
            lblEstado.setText("Abriendo Menú Sistema...");
            MenuSistema menuSis = new MenuSistema();
            menuSis.pack();
            menuSis.setLocationRelativeTo(this);
            menuSis.setVisible(true);
        });
        VENTASButton1.addActionListener(e -> {
            lblEstado.setText("Abriendo Ventas...");
            MenuVentas menuVenta = new MenuVentas();
            menuVenta.pack();
            menuVenta.setLocationRelativeTo(this);
            menuVenta.setVisible(true);
        });

        CREARButton.addActionListener(e -> {
            lblEstado.setText("Abriendo Crear...");
            MenuCrear menuCrear = new MenuCrear();
            menuCrear.pack();
            menuCrear.setLocationRelativeTo(this);
            menuCrear.setVisible(true);
        });
        CONSULTASButton.addActionListener(e -> {
                    lblEstado.setText("Abriendo Consultas...");
                    MenuConsultas menuCon = new MenuConsultas();
                    menuCon.pack();
                    menuCon.setLocationRelativeTo(this);
                    menuCon.setVisible(true);
                });
        button1.addActionListener(e -> lblEstado.setText("Un computador :D"));

        SALIRButton.addActionListener(e -> {
            Object[] opcionesSiNo = {"Sí", "No"};
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea salir?",
                    "CONFIRMACIÓN", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
    }

    public static void main(String[] args) {
        pag1 dialog = new pag1();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}