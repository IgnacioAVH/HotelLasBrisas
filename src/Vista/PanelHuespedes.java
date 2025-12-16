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


        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Huéspedes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnVolver = new JButton("< Volver al Menú");
        btnVolver.addActionListener(e -> mainFrame.mostrarVista("MENU"));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);


        String[] columnas = {"ID", "Nombre", "RUT", "Teléfono", "Email"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);


        JPanel panelSur = new JPanel();

        JButton btnEliminar = new JButton("Eliminar Huésped Seleccionado");
        btnEliminar.setBackground(new Color(255, 100, 100));
        btnEliminar.setForeground(Color.WHITE);

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
                    "¿Estás seguro de eliminar a " + nombre + " (RUT: " + rut + ")?\nEsta acción no se puede deshacer.",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controlador.eliminarHuesped(rut);
                    actualizarTabla(); // Recargar la tabla para ver que desapareció
                    JOptionPane.showMessageDialog(this, "Huésped eliminado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un huésped de la lista.");
        }
    }

    public void actualizarTabla() {
        modelo.setRowCount(0); // Limpiar tabla
        List<Huesped> lista = controlador.obtenerTodosHuespedes();

        if (lista != null) {
            for (Huesped h : lista) {
                modelo.addRow(new Object[]{
                        h.getIdHuesped(),
                        h.getNombre(),
                        h.getRut(),
                        h.getTelefono(),
                        h.getEmail()
                });
            }
        }
    }
}