package com.libreria.vista;

import com.libreria.modelo.Producto;
import com.libreria.servicio.ProductoServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProductoPanel extends JPanel {

    private final JTextField txtId     = new JTextField(5);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtPrecio = new JTextField(7);
    private final JTextField txtStock  = new JTextField(5);

    private final ProductoServicio servicioProducto = ProductoServicio.obtenerInstancia();
    private final ProductoTablaModelo modeloTabla   = new ProductoTablaModelo(servicioProducto);

    public ProductoPanel() {
        setLayout(new BorderLayout(10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),     BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("ID"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Stock"));
        panel.add(txtStock);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(this::accionAgregar);
        panel.add(btnAgregar);
        return panel;
    }

    private JScrollPane crearTabla() {
        JTable tabla = new JTable(modeloTabla);
        return new JScrollPane(tabla);
    }

    private void accionAgregar(ActionEvent e) {
        try {
            int    id     = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int    stock  = Integer.parseInt(txtStock.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            boolean ok = servicioProducto.agregar(new Producto(id, nombre, precio, stock));
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Se alcanzó el límite de 100 productos");
                return;
            }
            modeloTabla.fireTableDataChanged();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
}
