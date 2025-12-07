package Controller;

import Modelo.*;
import Persistencia.HotelPersistencia;

import java.util.ArrayList;
import java.util.Date;

public class HotelController {

    private Hotel hotel;
    private Recepcionista recepcionista;

    public HotelController(Hotel hotel, Recepcionista recepcionista) {
        this.hotel = hotel;
        this.recepcionista = recepcionista;
    }

    public ArrayList<Habitacion> obtenerHabitacionDisponible() {
        return recepcionista.verificarDisponibilidad();
    }

    public Reserva reservarHabitacion(String nombreHuesped, Date inicio, Date fin, int numeroHabitacionElegida) {
        Huesped huesped = new Huesped(nombreHuesped);
        return reservarHabitacion(huesped, inicio, fin, numeroHabitacionElegida);
    }

    public Reserva reservarHabitacion(Huesped huesped, Date inicio, Date fin, int numeroHabitacionElegida) {
        ArrayList<Habitacion> disponibles = hotel.consultarDisponibilidad();
        Habitacion seleccionada = null;

        for (Habitacion h : disponibles) {
            if (h.getNumero() == numeroHabitacionElegida) {
                seleccionada = h;
                break;
            }
        }

        if (seleccionada == null) {
            System.out.println("❌ No hay habitación disponible con ese número.");
            return null;
        }

        int idReserva = hotel.generarIdReserva();
        Reserva reserva = new Reserva(idReserva, huesped, seleccionada, inicio, fin);

        reserva.confirmarReserva();

        hotel.registrarReserva(reserva);
        HotelPersistencia.guardarHotel(hotel);

        return reserva;
    }
}