package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Reserva> reservas;
    // NUEVO: Lista para guardar clientes históricos (como en el Casino)
    private ArrayList<Huesped> historialHuespedes;

    private int contadorReservas = 1;

    public Hotel() {
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.historialHuespedes = new ArrayList<>();
        cargarHabitacionesIniciales();
    }

    private void cargarHabitacionesIniciales() {
        habitaciones.add(new Habitacion(101, "Simple", 35000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(102, "Simple", 35000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(201, "Doble", 50000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(202, "Doble", 50000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(301, "Suite", 75000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(401, "Penthouse", 150000, EstadoHabitacion.DISPONIBLE));
    }

    public int generarIdReserva() { return contadorReservas++; }

    // Métodos para Huespedes
    public void registrarHuespedSiNoExiste(Huesped nuevo) {
        boolean existe = historialHuespedes.stream()
                .anyMatch(h -> h.getRut().equals(nuevo.getRut()));
        if (!existe) {
            historialHuespedes.add(nuevo);
        }
    }

    public ArrayList<Huesped> getHistorialHuespedes() { return historialHuespedes; }

    // Getters generales
    public ArrayList<Habitacion> getHabitaciones() { return habitaciones; }
    public ArrayList<Reserva> getReservas() { return reservas; }

    public Habitacion buscarHabitacion(int numero) {
        return habitaciones.stream()
                .filter(h -> h.getNumero() == numero)
                .findFirst().orElse(null);
    }
}
