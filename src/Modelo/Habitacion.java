package Modelo;

import java.io.Serializable;

public class Habitacion implements Serializable {
    private static final long  serialVersionUID=1L;

    private int numero;
    private String tipo;
    private double precio;
    private EstadoHabitacion estado;

    private Reserva reservaActual;

    // Constructor
    public Habitacion(int numero, String tipo, double precio, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = estado;
    }

    public Habitacion() {}

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public EstadoHabitacion getEstado() { return estado; }
    public void setEstado(EstadoHabitacion estado) { this.estado = estado; }

    public void cambiarEstado(EstadoHabitacion nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public Reserva getReservaActual() {
        return reservaActual;
    }
    public void setReservaActual(Reserva r) {
        this.reservaActual = r;
    }

}
