package com.libreria.vista;

import com.libreria.modelo.Factura;
import com.libreria.servicio.FacturaServicio;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;

public class FacturaTablaModelo extends AbstractTableModel {
    private final FacturaServicio servicio;
    private final String[] columnas = {"ID", "Cliente", "Fecha", "Total", "Tipo"};
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FacturaTablaModelo(FacturaServicio servicio) {
        this.servicio = servicio;
    }

    @Override public int getRowCount()            { return servicio.tamaño(); }
    @Override public int getColumnCount()         { return columnas.length; }
    @Override public String getColumnName(int col){ return columnas[col]; }

    @Override public Object getValueAt(int fila, int col) {
        Factura factura = servicio.listar()[fila];
        return switch (col) {
            case 0 -> factura.getId();
            case 1 -> factura.getCliente().getNombre();
            case 2 -> factura.getFecha().format(formato);
            case 3 -> String.format("%.2f", factura.getTotal());
            case 4 -> factura.isProforma() ? "Proforma" : "Factura";
            default -> null;
        };
    }
}
