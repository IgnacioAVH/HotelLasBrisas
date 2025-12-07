package Modelo;

import java.util.Date;

public class Pago {

    private int idPago;
    private double monto;
    private Date fecha;
    private String metodo;
    private int idReserva;
    private int idPedido;

    public Pago(int idPago, double monto, String metodo, int idReserva, int idPedido) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = new Date();
        this.metodo = metodo;
        this.idReserva = idReserva;
        this.idPedido = idPedido;
    }

    public void procesarPago() {
        System.out.println("\n💳 Procesando pago...");
        System.out.println("✔ Monto: $" + monto);
        System.out.println("✔ Método: " + metodo);
        System.out.println("✔ Pago confirmado.\n");
    }

    public Comprobante generarComprobante() {
        return new Comprobante(
                (int)(Math.random() * 99999),
                "Pago por reserva ID " + idReserva + " por $" + monto,
                idPago
        );
    }

    // Getters
    public int getIdPago() { return idPago; }
    public double getMonto() { return monto; }
    public int getIdReserva() { return idReserva; }
    public int getIdPedido() { return idPedido; }
}

