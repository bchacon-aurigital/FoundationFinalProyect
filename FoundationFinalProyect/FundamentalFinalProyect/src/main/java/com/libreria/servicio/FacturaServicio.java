package com.libreria.servicio;

import com.libreria.modelo.Factura;

public class FacturaServicio {
    private static final int MAX = 100;
    private final Factura[] facturas = new Factura[MAX];
    private int contador = 0;

    private static final FacturaServicio INSTANCIA = new FacturaServicio();
    public static FacturaServicio obtenerInstancia() { return INSTANCIA; }

    public boolean agregar(Factura factura) {
        if (contador >= MAX) return false;
        facturas[contador++] = factura;
        return true;
    }
    public Factura[] listar() { return facturas; }
    public int tamaño()       { return contador; }
}
