package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String direccion;

    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Empleado> empleados;
    private ArrayList<Reserva> reservas;

    private int capacidadMaxima = 50;
    private int contadorReservas = 1;

    public Hotel() {
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.reservas = new ArrayList<>();
        cargarHabitacionesIniciales();
    }

    public Hotel(String nombre, String direccion, ArrayList<Habitacion> habitaciones, ArrayList<Empleado> empleados) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.habitaciones = habitaciones;
        this.empleados = empleados;
        this.reservas = new ArrayList<>();
        cargarHabitacionesIniciales();
    }

    private void cargarHabitacionesIniciales() {
        habitaciones.add(new Habitacion(101, "Simple", 35000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(102, "Simple", 35000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(201, "Doble", 50000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(202, "Doble", 50000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(301, "Suite", 75000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(103, "Simple Plus", 40000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(203, "Doble Superior", 55000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(302, "Suite Junior", 80000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(303, "Suite Premium", 90000, EstadoHabitacion.DISPONIBLE));
        habitaciones.add(new Habitacion(401, "Penthouse", 150000, EstadoHabitacion.DISPONIBLE));

        System.out.println("🔹 Habitaciones cargadas correctamente.");
    }

    public int generarIdReserva() {
        return contadorReservas++;
    }

    public void registrarReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.getHabitacion().cambiarEstado(EstadoHabitacion.OCUPADA);
        System.out.println("✔️ Reserva registrada en el hotel.");
    }

    private Reserva obtenerReservaActivaPorHabitacion(int numeroHab) {
        for (Reserva r : reservas) {
            if (r.getHabitacion().getNumero() == numeroHab &&
                    r.getEstado() == Reserva.EstadoReserva.CONFIRMADA) {
                return r;
            }
        }
        return null;
    }

    public Habitacion buscarHabitacion(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }

    public boolean agregarHabitacion(Habitacion h) {
        if (habitaciones.size() < capacidadMaxima) {
            habitaciones.add(h);
            return true;
        }
        System.out.println("No se pueden agregar más habitaciones. Límite alcanzado.");
        return false;
    }

    public ArrayList<Habitacion> consultarDisponibilidad() {
        ArrayList<Habitacion> libres = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                libres.add(h);
            }
        }
        return libres;
    }

    public void mostrarHabitacionesTotales() {
        System.out.println("\n===== HABITACIONES DEL HOTEL =====");
        for (Habitacion h : habitaciones) {
            System.out.print(
                    "Hab. " + h.getNumero()
                            + " | Tipo: " + h.getTipo()
                            + " | Precio: " + h.getPrecio()
                            + " | Estado: " + h.getEstado()
            );

            // Si está ocupada → mostrar quién la arrienda
            if (h.getEstado() == EstadoHabitacion.OCUPADA) {
                Reserva r = obtenerReservaActivaPorHabitacion(h.getNumero());
                if (r != null) {
                    System.out.print(" | Ocupada por: " + r.getHuesped().getNombre());
                }
            }
            System.out.println();
        }
        System.out.println("=================================\n");
    }

    // 🔹 NUEVO: Listar huéspedes activos
    public void listarHuespedes() {
        System.out.println("\n===== HUÉSPEDES EN EL HOTEL =====");
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.CONFIRMADA) {
                System.out.println("• " + r.getHuesped().getNombre() +
                        " en Habitación " + r.getHabitacion().getNumero());
            }
        }
        System.out.println("=================================\n");
    }

    // Getters
    public ArrayList<Habitacion> getHabitaciones() { return habitaciones; }
    public ArrayList<Empleado> getEmpleados() { return empleados; }
    public ArrayList<Reserva> getReservas() { return reservas; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
}
