package com.libreria.vista;

import com.libreria.modelo.CierreCaja;
import com.libreria.servicio.CajaServicio;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;

public class CajaTablaModelo extends AbstractTableModel {

    private final CajaServicio servicio;
    private final String[] columnas = {"ID", "Fecha", "Inicial", "Ingresos", "Egresos", "Final"};
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CajaTablaModelo(CajaServicio servicio) {
        this.servicio = servicio;
    }

    @Override public int getRowCount()            { return servicio.tamaño(); }
    @Override public int getColumnCount()         { return columnas.length; }
    @Override public String getColumnName(int col){ return columnas[col]; }

    @Override
    public Object getValueAt(int fila, int col) {
        CierreCaja cierre = servicio.listar()[fila];
        return switch (col) {
            case 0 -> cierre.getId();
            case 1 -> cierre.getFecha().format(formato);
            case 2 -> String.format("%.2f", cierre.getEfectivoInicial());
            case 3 -> String.format("%.2f", cierre.getIngresos());
            case 4 -> String.format("%.2f", cierre.getEgresos());
            case 5 -> String.format("%.2f", cierre.getEfectivoFinal());
            default -> null;
        };
    }
} 