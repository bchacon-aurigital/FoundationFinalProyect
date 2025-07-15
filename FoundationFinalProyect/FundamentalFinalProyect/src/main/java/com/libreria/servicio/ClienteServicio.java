package com.libreria.servicio;

import com.libreria.modelo.Cliente;

public class ClienteServicio {
    private static final int MAX = 100;
    private final Cliente[] clientes = new Cliente[MAX];
    private int contador = 0;

    private static final ClienteServicio INSTANCIA = new ClienteServicio();
    public static ClienteServicio obtenerInstancia() { return INSTANCIA; }

    public boolean agregar(Cliente cliente) {
        if (contador >= MAX) return false;
        clientes[contador++] = cliente;
        return true;
    }
    public Cliente[] listar() { return clientes; }
    public int tamaño()       { return contador; }
}
