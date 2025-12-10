package Controller;

import Modelo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HotelController {
    private static final String ARCHIVO_DATOS = "hotel.dat";
    private Hotel hotel;

    public HotelController() {
        cargarDatos();
    }

    // --- PERSISTENCIA ---
    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                hotel = (Hotel) ois.readObject();
            } catch (Exception e) {
                hotel = new Hotel();
            }
        } else {
            hotel = new Hotel();
        }
    }

    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(hotel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- LÓGICA DE NEGOCIO ---

    public List<Habitacion> obtenerTodasHabitaciones() {
        return hotel.getHabitaciones();
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return hotel.getHabitaciones().stream()
                .filter(h -> h.getEstado() == EstadoHabitacion.DISPONIBLE)
                .collect(Collectors.toList());
    }

    public Huesped buscarHuespedPorRut(String rut) {
        return hotel.getHistorialHuespedes().stream()
                .filter(h -> h.getRut().equalsIgnoreCase(rut))
                .findFirst()
                .orElse(null);
    }

    public Reserva crearReserva(String rut, String nombre, String telefono, String email,
                                int numHabitacion, Date inicio, Date fin, String metodoPago) throws Exception {

        // 1. Buscar o Crear Huésped
        Huesped huesped = buscarHuespedPorRut(rut);
        if (huesped == null) {
            // Usamos hash del rut o random para ID interno
            huesped = new Huesped((int)(System.currentTimeMillis() % 100000), nombre, rut, telefono, email);
            hotel.registrarHuespedSiNoExiste(huesped);
        }

        // 2. Buscar Habitación
        Habitacion hab = hotel.buscarHabitacion(numHabitacion);
        if (hab == null || hab.getEstado() != EstadoHabitacion.DISPONIBLE) {
            throw new Exception("Habitación no disponible o inexistente.");
        }

        // 3. Crear Reserva
        int idReserva = hotel.generarIdReserva();
        Reserva reserva = new Reserva(idReserva, huesped, hab, inicio, fin);

        // 4. Confirmar (cambia estado de habitación)
        reserva.confirmarReserva(); // Esto pone la habitación en OCUPADA internamente

        // 5. Guardar en historial del hotel
        hotel.getReservas().add(reserva);

        // 6. Guardar cambios en disco
        guardarDatos();

        return reserva;
    }

    public void liberarHabitacion(int numHabitacion) throws Exception {
        Habitacion hab = hotel.buscarHabitacion(numHabitacion);
        if (hab != null && hab.getEstado() == EstadoHabitacion.OCUPADA) {
            Reserva r = hab.getReservaActual();
            if (r != null) {
                r.cancelarReserva(); // Libera la habitación y cambia estado reserva
            } else {
                // Forzar liberación si no hay reserva linkeada (por seguridad)
                hab.cambiarEstado(EstadoHabitacion.DISPONIBLE);
            }
            guardarDatos();
        } else {
            throw new Exception("La habitación no está ocupada.");
        }
    }

    public List<Reserva> obtenerHistorialReservas() {
        return hotel.getReservas();
    }
}