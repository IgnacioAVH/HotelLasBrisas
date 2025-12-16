package Vista;

import Controller.HotelController;
import Modelo.Huesped;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHuespedes extends JPanel {
    private HotelController controlador;
    private VistaPrincipal mainFrame;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelHuespedes(HotelController ctrl, VistaPrincipal frame) {
        this.controlador = ctrl;
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Panel Superior ---
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Huéspedes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnVolver = new JButton("< Volver al Menú");
        btnVolver.addActionListener(e -> mainFrame.mostrarVista("MENU"));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre", "RUT", "Teléfono", "Email"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- Panel Inferior (BOTÓN ELIMINAR MEJORADO) ---
        JPanel panelSur = new JPanel();

        JButton btnEliminar = new JButton("ELIMINAR HUÉSPED SELECCIONADO");

        // ESTILOS DE ALTA VISIBILIDAD
        btnEliminar.setBackground(Color.RED);          // Rojo intenso
        btnEliminar.setForeground(Color.WHITE);        // Letra blanca
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14)); // Letra más grande y negrita
        btnEliminar.setFocusPainted(false);            // Quita el recuadro de foco al hacer clic

        // Estos dos son clave si estás en Windows/Mac para que se vea el color sólido:
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);

        btnEliminar.addActionListener(e -> eliminarHuespedSeleccionado());

        panelSur.add(btnEliminar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void eliminarHuespedSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row != -1) {
            String rut = (String) modelo.getValueAt(row, 2);
            String nombre = (String) modelo.getValueAt(row, 1);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de eliminar a " + nombre + " (RUT: " + rut + ")?\nEsta acción es irreversible.",
                    "PELIGRO: Eliminar Huésped",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controlador.eliminarHuesped(rut);
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Huésped eliminado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un huésped de la lista primero.");
        }
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        List<Huesped> lista = controlador.obtenerTodosHuespedes();
        if (lista != null) {
            for (Huesped h : lista) {
                modelo.addRow(new Object[]{
                        h.getIdHuesped(), h.getNombre(), h.getRut(), h.getTelefono(), h.getEmail()
                });
            }
        }
    }
}