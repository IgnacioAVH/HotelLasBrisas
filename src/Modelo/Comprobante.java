package Modelo;

import java.util.Date;

public class Comprobante {
    private int idComprobante;
    private String descripcion;
    private int idPago;
    private Date fecha;

    public Comprobante(int idComprobante, String descripcion, int idPago) {
        this.idComprobante = idComprobante;
        this.descripcion = descripcion;
        this.idPago = idPago;
        this.fecha = new Date(); // fecha actual
    }

    public void imprimir() {
        System.out.println("\n📄 ==== COMPROBANTE DE PAGO ====");
        System.out.println("🧾 ID Comprobante: " + idComprobante);
        System.out.println("💳 Pago asociado: " + idPago);
        System.out.println("📝 Descripción: " + descripcion);
        System.out.println("📅 Fecha: " + fecha);
        System.out.println("=================================\n");
    }

    public int getIdComprobante() {
        return idComprobante;
    }
}
