package Modelo;

import java.io.Serializable;

public class Huesped implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idHuesped;
    private String nombre;
    private String rut;
    private String telefono;
    private String email;

    public Huesped(int idHuesped, String nombre, String rut, String telefono, String email) {
        this.idHuesped = idHuesped;
        this.nombre = nombre;
        this.rut = rut;
        this.telefono = telefono;
        this.email = email;
    }

    public Huesped(String nombre) {
        this.nombre = nombre;
    }

    public int getIdHuesped() { return idHuesped; }
    public String getNombre() { return nombre; }
    public String getRut() { return rut; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
}