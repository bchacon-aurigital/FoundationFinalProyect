package com.libreria.servicio;

import com.libreria.modelo.Producto;

public class ProductoServicio {
    private static final int MAX = 100;
    private final Producto[] productos = new Producto[MAX];
    private int contador = 0;

    private static final ProductoServicio INSTANCIA = new ProductoServicio();
    public static ProductoServicio obtenerInstancia() { return INSTANCIA; }

    public boolean agregar(Producto producto) {
        if (contador >= MAX) return false;
        productos[contador++] = producto;
        return true;
    }
    public Producto[] listar() { return productos; }
    public int tamaño()       { return contador; }

    public boolean descontarStock(int idProducto, int cantidad) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].getId() == idProducto) {
                if (productos[i].getStock() < cantidad) return false;
                productos[i].setStock(productos[i].getStock() - cantidad);
                return true;
            }
        }
        return false;
    }
}
