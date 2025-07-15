package com.libreria.vista;

import com.libreria.modelo.Producto;
import com.libreria.servicio.ProductoServicio;

import javax.swing.table.AbstractTableModel;

public class ProductoTablaModelo extends AbstractTableModel {

    private final ProductoServicio servicio;
    private final String[] columnas = {"ID", "Nombre", "Precio", "Stock"};

    public ProductoTablaModelo(ProductoServicio servicio) {
        this.servicio = servicio;
    }

    @Override public int getRowCount()            { return servicio.tamaño(); }
    @Override public int getColumnCount()         { return columnas.length; }
    @Override public String getColumnName(int col){ return columnas[col]; }

    @Override
    public Object getValueAt(int fila, int col) {
        Producto p = servicio.listar()[fila];
        return switch (col) {
            case 0 -> p.getId();
            case 1 -> p.getNombre();
            case 2 -> p.getPrecio();
            case 3 -> p.getStock();
            default -> null;
        };
    }
}
