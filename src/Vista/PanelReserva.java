package Vista;

import Controller.HotelController;
import Modelo.Huesped;
import Modelo.Habitacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class PanelReserva extends JPanel {
    private HotelController controlador;
    private VistaPrincipal mainFrame;

    // Campos del formulario
    private JTextField txtRut, txtNombre, txtTelefono, txtEmail;
    private JComboBox<String> comboHabitaciones; // Guardaremos el ID como string
    private JComboBox<String> comboPago;

    public PanelReserva(HotelController ctrl, VistaPrincipal frame) {
        this.controlador = ctrl;
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 40, 20, 40));

        add(new JLabel("Nueva Reserva", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        // 1. Buscador de cliente
        formPanel.add(new JLabel("RUT Huésped:"));
        JPanel panelRut = new JPanel(new BorderLayout());
        txtRut = new JTextField();
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.addActionListener(e -> buscarCliente());
        panelRut.add(txtRut, BorderLayout.CENTER);
        panelRut.add(btnBuscar, BorderLayout.EAST);
        formPanel.add(panelRut);

        // 2. Datos Cliente
        formPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);

        formPanel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        formPanel.add(txtTelefono);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        // 3. Selección Habitación
        formPanel.add(new JLabel("Habitación Disponible:"));
        comboHabitaciones = new JComboBox<>();
        formPanel.add(comboHabitaciones);

        // 4. Pago
        formPanel.add(new JLabel("Método Pago:"));
        comboPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA", "TRANSFERENCIA"});
        formPanel.add(comboPago);

        add(formPanel, BorderLayout.CENTER);

        // Botones Acción
        JPanel btnPanel = new JPanel();
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> mainFrame.mostrarVista("MENU"));

        JButton btnConfirmar = new JButton("Confirmar Reserva");
        btnConfirmar.setBackground(new Color(100, 200, 100));
        btnConfirmar.addActionListener(e -> procesarReserva());

        btnPanel.add(btnCancelar);
        btnPanel.add(btnConfirmar);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void buscarCliente() {
        String rut = txtRut.getText().trim();
        if (rut.isEmpty()) return;

        Huesped h = controlador.buscarHuespedPorRut(rut);
        if (h != null) {
            txtNombre.setText(h.getNombre());
            txtTelefono.setText(h.getTelefono());
            txtEmail.setText(h.getEmail());
            JOptionPane.showMessageDialog(this, "Huésped encontrado: " + h.getNombre());
        } else {
            JOptionPane.showMessageDialog(this, "Huésped nuevo. Por favor llene los datos.");
            txtNombre.setText("");
            txtTelefono.setText("");
            txtEmail.setText("");
            txtNombre.requestFocus();
        }
    }

    public void cargarHabitacionesDisponibles() {
        comboHabitaciones.removeAllItems();
        List<Habitacion> disponibles = controlador.obtenerHabitacionesDisponibles();
        for (Habitacion h : disponibles) {
            // Guardamos "Numero - Tipo ($Precio)"
            comboHabitaciones.addItem(h.getNumero() + " - " + h.getTipo() + " ($" + h.getPrecio() + ")");
        }
    }

    private void procesarReserva() {
        try {
            // Validaciones básicas
            if (txtNombre.getText().isEmpty() || comboHabitaciones.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Faltan datos.");
                return;
            }

            String rut = txtRut.getText();
            String nombre = txtNombre.getText();
            String fono = txtTelefono.getText();
            String email = txtEmail.getText();
            String pago = (String) comboPago.getSelectedItem();

            // Parsear el numero de habitacion del string del combo
            String selectedHab = (String) comboHabitaciones.getSelectedItem(); // "101 - Simple..."
            int numHab = Integer.parseInt(selectedHab.split(" ")[0]);

            // Crear reserva (Fechas simuladas hoy -> mañana)
            controlador.crearReserva(rut, nombre, fono, email, numHab, new Date(), new Date(), pago);

            JOptionPane.showMessageDialog(this, "¡Reserva Creada con Éxito!");

            // Limpiar y volver
            txtRut.setText(""); txtNombre.setText(""); txtTelefono.setText(""); txtEmail.setText("");
            mainFrame.mostrarVista("MENU");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al reservar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
