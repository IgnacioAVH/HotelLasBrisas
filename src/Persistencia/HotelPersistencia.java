
package Persistencia;

import Modelo.Hotel;
import java.io.*;
public class HotelPersistencia {
    private static final String HOTEL_FILE = "hotel.dat";

    public static void guardarHotel(Hotel hotel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HOTEL_FILE))) {
            oos.writeObject(hotel);
            System.out.println("✔ Hotel guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Hotel cargarHotel() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HOTEL_FILE))) {
            System.out.println("✔ Hotel cargado desde archivo.");
            return (Hotel) ois.readObject();
        } catch (Exception e) {
            System.out.println("⚠ No se encontró archivo de hotel. Se crea uno nuevo.");
            return new Hotel();
        }
    }
}
