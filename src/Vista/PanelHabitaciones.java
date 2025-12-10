package Vista;

import Controller.HotelController;
import Modelo.Habitacion;
import Modelo.EstadoHabitacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelHabitaciones extends JPanel {
    private HotelController controlador;
    private VistaPrincipal mainFrame;
    private DefaultTableModel modelo;

    public PanelHabitaciones(HotelController ctrl, VistaPrincipal frame) {
        this.controlador = ctrl;
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Botón volver
        JButton btnVolver = new JButton("< Volver al Menú");
        btnVolver.addActionListener(e -> mainFrame.mostrarVista("MENU"));
        add(btnVolver, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Número", "Tipo", "Precio", "Estado", "Huésped Actual"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior con acción de liberar
        JPanel panelSur = new JPanel();
        JButton btnLiberar = new JButton("Liberar Habitación (Check-Out)");
        btnLiberar.setBackground(new Color(255, 150, 150));

        btnLiberar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row != -1) {
                int numHab = (int) modelo.getValueAt(row, 0);
                String estado = (String) modelo.getValueAt(row, 3);

                if ("DISPONIBLE".equals(estado)) {
                    JOptionPane.showMessageDialog(this, "La habitación ya está disponible.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this, "¿Liberar la habitación " + numHab + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controlador.liberarHabitacion(numHab);
                        actualizarTabla();
                        JOptionPane.showMessageDialog(this, "Habitación liberada correctamente.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una habitación ocupada.");
            }
        });

        panelSur.add(btnLiberar);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (Habitacion h : controlador.obtenerTodasHabitaciones()) {
            String huesped = "";
            if (h.getEstado() == EstadoHabitacion.OCUPADA && h.getReservaActual() != null) {
                huesped = h.getReservaActual().getHuesped().getNombre();
            }

            modelo.addRow(new Object[]{
                    h.getNumero(),
                    h.getTipo(),
                    h.getPrecio(),
                    h.getEstado().toString(),
                    huesped
            });
        }
    }
}