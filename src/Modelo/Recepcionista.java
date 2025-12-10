package Modelo;

import java.io.Serializable;

public class Recepcionista extends Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    // En el patrón MVC, el Recepcionista es un rol/usuario.
    // La lógica de "verificar disponibilidad" se ha movido al HotelController.

    public Recepcionista() {
        super();
    }

    public Recepcionista(int idEmpleado, String nombre, String rol) {
        super(idEmpleado, nombre, rol);
    }
}


