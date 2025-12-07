package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private static final long  serialVersionUID=1L;

    private int idReserva;
    private Huesped huesped;
    private Habitacion habitacion;
    private Date fechaInicio;
    private Date fechaFin;
    private EstadoReserva estado;

    public enum EstadoReserva {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA
    }

    public Reserva(int idReserva, Huesped huesped, Habitacion habitacion,
                   Date fechaInicio, Date fechaFin) {
        this.idReserva = idReserva;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoReserva.PENDIENTE;
    }
    public void confirmarReserva() {
        if (estado == EstadoReserva.CONFIRMADA) {
            System.out.println("⚠️ La reserva ya está confirmada.");
            return;
        }

        this.estado = EstadoReserva.CONFIRMADA;
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADA);

        habitacion.setReservaActual(this);
    }

    public void cancelarReserva() {
        if (estado == EstadoReserva.CANCELADA) {
            System.out.println("⚠️ La reserva ya está cancelada.");
            return;
        }

        this.estado = EstadoReserva.CANCELADA;
        habitacion.cambiarEstado(EstadoHabitacion.DISPONIBLE);

        habitacion.setReservaActual(null);
    }

    public int getIdReserva() { return idReserva; }
    public Huesped getHuesped() { return huesped; }
    public Habitacion getHabitacion() { return habitacion; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public EstadoReserva getEstado() { return estado; }
}
