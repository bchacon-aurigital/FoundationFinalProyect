package com.libreria.modelo;

import java.time.LocalDate;

public class CierreCaja {
    private int id;
    private LocalDate fecha;
    private double efectivoInicial;
    private double ingresos;
    private double egresos;
    private double efectivoFinal;

    public CierreCaja(int id, double efectivoInicial,
                      double ingresos, double egresos) {
        this.id = id;
        this.fecha = LocalDate.now();
        this.efectivoInicial = efectivoInicial;
        this.ingresos = ingresos;
        this.egresos = egresos;
        this.efectivoFinal = efectivoInicial + ingresos - egresos;
    }

    public int getId()            { return id; }
    public LocalDate getFecha()   { return fecha; }
    public double getEfectivoInicial(){ return efectivoInicial; }
    public double getIngresos()   { return ingresos; }
    public double getEgresos()    { return egresos; }
    public double getEfectivoFinal(){ return efectivoFinal; }
}
