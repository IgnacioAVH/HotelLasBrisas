package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Recepcionista extends Empleado implements Serializable {
    private static final long  serialVersionUID=1L;

    private Hotel hotel;

    public Recepcionista() {}

    public Recepcionista(int idEmpleado, String nombre, String rol, Hotel hotel) {
        super(idEmpleado, nombre, rol);
        this.hotel = hotel;
    }

    public ArrayList<Habitacion> verificarDisponibilidad() {
        return hotel.consultarDisponibilidad();
    }

    private int contadorReservas = 1;

    public Reserva registrarReserva(
            Huesped huesped,
            Habitacion habitacion,
            Date inicio,
            Date fin
    ) {
        if (habitacion == null) {
            System.out.println("⚠️ Habitacion no válida.");
            return null;
        }

        if (habitacion.getEstado() == EstadoHabitacion.OCUPADA) {
            System.out.println("❗ La habitación está ocupada.");
            return null;
        }

        Reserva r = new Reserva(contadorReservas++, huesped, habitacion, inicio, fin);

        habitacion.cambiarEstado(EstadoHabitacion.OCUPADA);

        habitacion.setReservaActual(r);

        System.out.println("✔️ Reserva creada correctamente!");

        return r;
    }

}


