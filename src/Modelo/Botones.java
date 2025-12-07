package Modelo;

import java.io.Serializable;

public class Botones extends Empleado implements Serializable {
    private static final long  serialVersionUID=1L;

    public Botones(int id, String nombre, String cargo) {
        super(id, nombre, cargo);
    }

    public void acompanarHuesped(Huesped h, Habitacion hab){
        System.out.println(" El botones " + getNombre() +
                " acompaña al huésped " + h.getNombre() +
                " hasta la habitación " + hab.getNumero());
    }
}
