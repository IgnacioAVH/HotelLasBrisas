package Vista;

import Controller.HotelController;
import Modelo.*;
import Persistencia.HotelPersistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Hotel hotel = HotelPersistencia.cargarHotel();
        Recepcionista recep = new Recepcionista(1, "Juan", "Recepcionista", hotel);
        Botones botones = new Botones(2, "Mario", "Botones");
        HotelController controller = new HotelController(hotel, recep);

        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarHabitacionesDisponibles(hotel);
                    break;
                case 2:
                    realizarProcesoReserva(scanner, hotel, controller, botones);
                    break;
                case 3:
                    mostrarHabitacionesTotales(hotel);
                    break;
                case 4:
                    liberarHabitacion(scanner, hotel);
                    break;
                case 5: // 🔹 Nuevo caso: mostrar huéspedes
                    mostrarHuespedes(hotel);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    HotelPersistencia.guardarHotel(hotel);
                    break;
                default:
                    System.out.println(" ❗ Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarHabitacionesDisponibles(Hotel hotel) {
        ArrayList<Habitacion> libres = hotel.consultarDisponibilidad();

        System.out.println("\n 📌 Habitaciones disponibles:");

        if (libres.isEmpty()) {
            System.out.println(" 🔴 No hay habitaciones libres.");
            return;
        }

        for (Habitacion hab : libres) {
            System.out.println("- Habitación " + hab.getNumero()
                    + " | Tipo: " + hab.getTipo()
                    + " | Precio: " + hab.getPrecio());
        }
    }

    private static void mostrarHabitacionesTotales(Hotel hotel) {
        System.out.println("\n 🏨 Listado completo de habitaciones:");

        for (Habitacion hab : hotel.getHabitaciones()) {
            System.out.print("- Habitación " + hab.getNumero()
                    + " | Tipo: " + hab.getTipo()
                    + " | Precio: " + hab.getPrecio()
                    + " | Estado: " + hab.getEstado());

            if (hab.getReservaActual() != null) {
                System.out.print(" | Ocupada por: " + hab.getReservaActual().getHuesped().getNombre());
            }
            System.out.println();
        }
    }

    private static void realizarProcesoReserva(Scanner scanner, Hotel hotel, HotelController controller, Botones botones) {
        scanner.nextLine();

        System.out.print("\n🆔 ID del huésped: ");
        int idHuesped = scanner.nextInt();
        scanner.nextLine();

        System.out.print("👤 Nombre del huésped: ");
        String nombre = scanner.nextLine();

        System.out.print("🪪 RUT del huésped: ");
        String rut = scanner.nextLine();

        System.out.print("📞 Teléfono del huésped: ");
        String telefono = scanner.nextLine();

        System.out.print("📧 Email del huésped: ");
        String email = scanner.nextLine();

        System.out.print("👉 Número de habitación que desea: ");
        int numHab = scanner.nextInt();
        scanner.nextLine();

        Date inicio = new Date();
        Date fin = new Date(inicio.getTime() + 24 * 60 * 60 * 1000);

        Huesped huesped = new Huesped(idHuesped, nombre, rut, telefono, email);

        Reserva reserva = controller.reservarHabitacion(huesped, inicio, fin, numHab);

        if (reserva == null) {
            System.out.println("❌ No se pudo realizar la reserva.");
            return;
        }

        System.out.println("\n💾 Reserva registrada con éxito.");
        System.out.println("🧾 ID Reserva: " + reserva.getIdReserva());

        System.out.print("\n💳 Seleccione método de pago (efectivo / debito): ");
        String metodoPago = scanner.nextLine().trim().toLowerCase();

        while (!metodoPago.equals("efectivo") && !metodoPago.equals("debito")) {
            System.out.println("❌ Método de pago inválido. Intente nuevamente.");
            System.out.print("\n💳 Seleccione método de pago (efectivo / debito): ");
            metodoPago = scanner.nextLine().trim().toLowerCase();
        }

        Pago pago = new Pago(
                (int) (Math.random() * 10000),
                reserva.getHabitacion().getPrecio(),
                metodoPago,
                reserva.getIdReserva(),
                0
        );

        pago.procesarPago();
        Comprobante comp = pago.generarComprobante();
        comp.imprimir();

        System.out.println("🔑 Recepcionista entrega llaves de la habitación " + numHab);

        botones.acompanarHuesped(reserva.getHuesped(), reserva.getHabitacion());
    }

    private static void liberarHabitacion(Scanner scanner, Hotel hotel) {
        System.out.print("\n🛏 Número de habitación a liberar: ");
        int numHab = scanner.nextInt();

        Habitacion hab = hotel.buscarHabitacion(numHab);
        if (hab == null) {
            System.out.println("❌ La habitación no existe.");
            return;
        }

        Reserva r = hab.getReservaActual();
        if (r == null) {
            System.out.println("⚠️ La habitación ya está libre.");
            return;
        }

        r.cancelarReserva();

        HotelPersistencia.guardarHotel(hotel);

        System.out.println("✔️ La habitación " + numHab + " ahora está libre.");
    }

    // 🔹 Nuevo método para mostrar huéspedes
    private static void mostrarHuespedes(Hotel hotel) {
        System.out.println("\n=== 👥 HUÉSPEDES REGISTRADOS ===");
        for (Reserva r : hotel.getReservas()) {
            if (r.getEstado() == Reserva.EstadoReserva.CONFIRMADA) {
                Huesped h = r.getHuesped();
                System.out.println("ID: " + h.getIdHuesped()
                        + " | Nombre: " + h.getNombre()
                        + " | RUT: " + h.getRut()
                        + " | Teléfono: " + h.getTelefono()
                        + " | Email: " + h.getEmail()
                        + " | Habitación: " + r.getHabitacion().getNumero());
            }
        }
        System.out.println("================================\n");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== 🏨 MENÚ HOTEL ===");
        System.out.println("1. Ver habitaciones disponibles");
        System.out.println("2. Reservar habitación (con pago y botones)");
        System.out.println("3. Ver todas las habitaciones");
        System.out.println("4. Liberar habitación");
        System.out.println("5. Mostrar todos los huéspedes"); // 🔹 Nuevo
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
}