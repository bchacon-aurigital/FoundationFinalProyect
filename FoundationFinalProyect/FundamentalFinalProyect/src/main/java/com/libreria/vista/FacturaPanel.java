package com.libreria.vista;

import com.libreria.modelo.*;
import com.libreria.servicio.*;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class FacturaPanel extends JPanel {

    private final ProductoServicio servicioProducto = ProductoServicio.obtenerInstancia();
    private final ClienteServicio  servicioCliente  = ClienteServicio.obtenerInstancia();
    private final FacturaServicio  servicioFactura  = FacturaServicio.obtenerInstancia();

    private final JComboBox<Cliente> comboCliente =
            new JComboBox<>(new DefaultComboBoxModel<>(servicioCliente.listar()));
    private final JCheckBox chkProforma = new JCheckBox("Es proforma");

    private final JComboBox<Producto> comboProducto =
            new JComboBox<>(new DefaultComboBoxModel<>(servicioProducto.listar()));
    private final JTextField txtCantidad = new JTextField(4);
    private final DefaultTableModel modeloDetalle =
            new DefaultTableModel(new String[]{"Producto", "Cant.", "Subtotal"}, 0);
    private final JTable tablaDetalle = new JTable(modeloDetalle);

    private final List<DetalleFactura> listaDetalles = new ArrayList<>();
    private double totalActual = 0.0;
    private final JLabel etiquetaTotal = new JLabel("Total: 0.00");

    private final FacturaTablaModelo modeloFactura = new FacturaTablaModelo(servicioFactura);
    private final JTable tablaFactura = new JTable(modeloFactura);

    public FacturaPanel() {
        setLayout(new BorderLayout(10, 10));
        add(crearFormulario(),      BorderLayout.NORTH);
        add(crearDetalleYLista(),   BorderLayout.CENTER);

        addAncestorListener(new AncestorListener() {
            @Override public void ancestorAdded (AncestorEvent e){ refrescarCombos(); }
            @Override public void ancestorMoved (AncestorEvent e){}
            @Override public void ancestorRemoved(AncestorEvent e){}
        });
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Cliente:"));
        panel.add(comboCliente);
        panel.add(chkProforma);

        panel.add(new JLabel("Producto:"));
        panel.add(comboProducto);
        panel.add(new JLabel("Cant:"));
        panel.add(txtCantidad);

        JButton btnAñadir = new JButton("Añadir línea");
        btnAñadir.addActionListener(this::accionAgregarLinea);
        panel.add(btnAñadir);

        JButton btnGuardar = new JButton("Guardar factura");
        btnGuardar.addActionListener(this::accionGuardarFactura);
        panel.add(btnGuardar);

        panel.add(etiquetaTotal);
        return panel;
    }

    private JSplitPane crearDetalleYLista() {
        JScrollPane spDetalle = new JScrollPane(tablaDetalle);
        JScrollPane spFactura = new JScrollPane(tablaFactura);
        JSplitPane divisor = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spDetalle, spFactura);
        divisor.setResizeWeight(0.3);
        return divisor;
    }


    private void accionAgregarLinea(ActionEvent evento) {

        Producto producto = (Producto) comboProducto.getSelectedItem();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }

        if (producto.getStock() < cantidad) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente para " + producto.getNombre());
            return;
        }

        DetalleFactura detalle = new DetalleFactura(producto, cantidad);
        listaDetalles.add(detalle);
        totalActual += detalle.getSubtotal();

        modeloDetalle.addRow(new Object[]{
                producto.getNombre(),
                cantidad,
                String.format("%.2f", detalle.getSubtotal())
        });
        etiquetaTotal.setText(String.format("Total: %.2f", totalActual));
        txtCantidad.setText("");
    }

    private void accionGuardarFactura(ActionEvent evento) {

        if (listaDetalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay líneas de detalle");
            return;
        }
        Cliente cliente = (Cliente) comboCliente.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente");
            return;
        }

        for (DetalleFactura det : listaDetalles) {
            boolean ok = servicioProducto.descontarStock(
                    det.getProducto().getId(), det.getCantidad());
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Error al descontar stock; operación cancelada");
                return;
            }
        }

        int nuevoId = servicioFactura.tamaño() + 1;
        Factura factura = new Factura(nuevoId, cliente, chkProforma.isSelected());
        for (DetalleFactura det : listaDetalles) factura.agregarDetalle(det);

        servicioFactura.agregar(factura);

        listaDetalles.clear();
        modeloDetalle.setRowCount(0);
        totalActual = 0.0;
        etiquetaTotal.setText("Total: 0.00");
        modeloFactura.fireTableDataChanged();

        JOptionPane.showMessageDialog(this, "Factura guardada con éxito");
    }

    private void refrescarCombos() {
        DefaultComboBoxModel<Cliente> modeloClientes =
                (DefaultComboBoxModel<Cliente>) comboCliente.getModel();
        modeloClientes.removeAllElements();
        for (int i = 0; i < servicioCliente.tamaño(); i++)
            modeloClientes.addElement(servicioCliente.listar()[i]);

        DefaultComboBoxModel<Producto> modeloProductos =
                (DefaultComboBoxModel<Producto>) comboProducto.getModel();
        modeloProductos.removeAllElements();
        for (int i = 0; i < servicioProducto.tamaño(); i++)
            modeloProductos.addElement(servicioProducto.listar()[i]);
    }
}
