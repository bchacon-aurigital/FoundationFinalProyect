package com.libreria.vista;

import com.libreria.modelo.CierreCaja;
import com.libreria.servicio.CajaServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CajaPanel extends JPanel {

    private final JTextField txtEfectivoInicial = new JTextField(10);
    private final JTextField txtIngresos = new JTextField(10);
    private final JTextField txtEgresos = new JTextField(10);
    private final JLabel lblEfectivoFinal = new JLabel("0.00");

    private final CajaServicio servicioCaja = CajaServicio.obtenerInstancia();
    private final CajaTablaModelo modeloTabla = new CajaTablaModelo(servicioCaja);

    public CajaPanel() {
        setLayout(new BorderLayout(10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Efectivo inicial:"));
        panel.add(txtEfectivoInicial);
        panel.add(new JLabel("Ingresos:"));
        panel.add(txtIngresos);
        panel.add(new JLabel("Egresos:"));
        panel.add(txtEgresos);
        panel.add(new JLabel("Efectivo final:"));
        panel.add(lblEfectivoFinal);

        JButton btnRegistrar = new JButton("Registrar cierre");
        btnRegistrar.addActionListener(this::accionRegistrar);
        panel.add(btnRegistrar);
        return panel;
    }

    private JScrollPane crearTabla() {
        JTable tabla = new JTable(modeloTabla);
        return new JScrollPane(tabla);
    }

    private void accionRegistrar(ActionEvent e) {
        try {
            double inicial = Double.parseDouble(txtEfectivoInicial.getText().trim());
            double ingresos = Double.parseDouble(txtIngresos.getText().trim());
            double egresos = Double.parseDouble(txtEgresos.getText().trim());

            if (inicial < 0 || ingresos < 0 || egresos < 0) {
                JOptionPane.showMessageDialog(this, "Los montos no pueden ser negativos");
                return;
            }

            int nuevoId = servicioCaja.tamaño() + 1;
            CierreCaja nuevoCierre = new CierreCaja(nuevoId, inicial, ingresos, egresos);

            boolean ok = servicioCaja.agregar(nuevoCierre);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Se alcanzó el límite de 100 cierres");
                return;
            }
            modeloTabla.fireTableDataChanged();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
        }
    }

    private void limpiarCampos() {
        txtEfectivoInicial.setText("");
        txtIngresos.setText("");
        txtEgresos.setText("");
        lblEfectivoFinal.setText("0.00");
    }
}
