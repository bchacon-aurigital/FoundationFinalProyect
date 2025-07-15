package com.libreria.modelo;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId()            { return id; }
    public String getNombre()     { return nombre; }
    public String getTelefono()   { return telefono; }
    public String getEmail()      { return email; }

    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email)       { this.email = email; }

    @Override
    public String toString() { return nombre; }
}
