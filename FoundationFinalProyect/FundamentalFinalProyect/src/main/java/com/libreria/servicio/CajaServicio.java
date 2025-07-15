package com.libreria.servicio;

import com.libreria.modelo.CierreCaja;

public class CajaServicio {
    private static final int MAX = 100;
    private final CierreCaja[] cierres = new CierreCaja[MAX];
    private int contador = 0;

    private static final CajaServicio INSTANCIA = new CajaServicio();
    public static CajaServicio obtenerInstancia() { return INSTANCIA; }

    public boolean agregar(CierreCaja cierre) {
        if (contador >= MAX) return false;
        cierres[contador++] = cierre;
        return true;
    }
    public CierreCaja[] listar() { return cierres; }
    public int tamaño()          { return contador; }
}
