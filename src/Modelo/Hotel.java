package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Reserva> reservas;
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
        habitaciones.add(new Habitacion(302, "Suite Premium", 90000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(401, "Penthouse", 150000, EstadoHabitacion.DISPONIBLE));
    }

    public int generarIdReserva() {
        return contadorReservas++;
    }

    // --- Métodos de Gestión ---

    public void registrarReserva(Reserva reserva) {
        if (reservas == null) reservas = new ArrayList<>(); // Protección extra
        reservas.add(reserva);
    }

    public void registrarHuespedSiNoExiste(Huesped nuevo) {
        // Protección: Si la lista es null (por archivo viejo), la creamos ahora
        if (historialHuespedes == null) {
            historialHuespedes = new ArrayList<>();
        }

        boolean existe = historialHuespedes.stream()
                .anyMatch(h -> h.getRut().equalsIgnoreCase(nuevo.getRut()));
        if (!existe) {
            historialHuespedes.add(nuevo);
        }
    }

    public Habitacion buscarHabitacion(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }

    // --- Getters Inteligentes (Lazy Initialization) ---

    public ArrayList<Habitacion> getHabitaciones() { return habitaciones; }

    public ArrayList<Reserva> getReservas() { return reservas; }

    // AQUÍ ESTÁ EL ARREGLO PRINCIPAL
    public ArrayList<Huesped> getHistorialHuespedes() {
        if (this.historialHuespedes == null) {
            this.historialHuespedes = new ArrayList<>();
        }
        return historialHuespedes;
    }
}
