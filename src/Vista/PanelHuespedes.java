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

    public PanelHuespedes(HotelController ctrl, VistaPrincipal frame) {
        this.controlador = ctrl;
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Título y Botón Volver ---
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Historial de Huéspedes Registrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnVolver = new JButton("< Volver al Menú");
        btnVolver.addActionListener(e -> mainFrame.mostrarVista("MENU"));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // --- Tabla de Huéspedes ---
        // Definimos las columnas
        String[] columnas = {"ID", "Nombre", "RUT", "Teléfono", "Email"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override // Hacemos que la tabla no sea editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    // Método para recargar los datos cada vez que entramos a esta pantalla
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