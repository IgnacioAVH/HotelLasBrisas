package Modelo;

import java.io.Serializable;

public class Empleado implements Serializable {
    private static final long  serialVersionUID=1L;

private int idEmpleado;
private String nombre;
private String rol;
public Empleado(){

}
    public Empleado(int idEmpleado, String nombre, String rol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
