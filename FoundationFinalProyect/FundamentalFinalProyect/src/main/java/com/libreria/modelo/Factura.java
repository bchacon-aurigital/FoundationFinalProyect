package com.libreria.modelo;

import java.time.LocalDate;

public class Factura {
    private int id;
    private Cliente cliente;
    private DetalleFactura[] detalles = new DetalleFactura[50];
    private int contadorDet = 0;
    private boolean proforma;
    private LocalDate fecha;
    private double total;

    public Factura(int id, Cliente cliente, boolean proforma) {
        this.id = id;
        this.cliente = cliente;
        this.proforma = proforma;
        this.fecha = LocalDate.now();
    }

    public boolean agregarDetalle(DetalleFactura det) {
        if (contadorDet >= detalles.length) return false;
        detalles[contadorDet++] = det;
        total += det.getSubtotal();
        return true;
    }

    public int getId()              { return id; }
    public Cliente getCliente()     { return cliente; }
    public DetalleFactura[] getDetalles(){ return detalles; }
    public int sizeDetalles()       { return contadorDet; }
    public boolean isProforma()     { return proforma; }
    public LocalDate getFecha()     { return fecha; }
    public double getTotal()        { return total; }
}
