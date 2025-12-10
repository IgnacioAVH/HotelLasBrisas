package Vista;

import Controller.HotelController;
import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    private HotelController controlador;
    private JPanel panelContenido;
    private CardLayout cardLayout;

    public VistaPrincipal(HotelController controlador) {
        this.controlador = controlador;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Sistema de Gestión Hotelera");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // Crear Paneles
        JPanel panelMenu = crearPanelMenu();
        JPanel panelHabitaciones = new PanelHabitaciones(controlador, this);
        JPanel panelReserva = new PanelReserva(controlador, this);

        // Añadir al CardLayout con nombres clave
        panelContenido.add(panelMenu, "MENU");
        panelContenido.add(panelHabitaciones, "HABITACIONES");
        panelContenido.add(panelReserva, "RESERVA");

        add(panelContenido);
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Bienvenido al Hotel", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(titulo, gbc);

        JButton btnHabitaciones = crearBotonMenu("Ver Estado Habitaciones");
        btnHabitaciones.addActionListener(e -> {
            ((PanelHabitaciones) panelContenido.getComponent(1)).actualizarTabla();
            mostrarVista("HABITACIONES");
        });
        gbc.gridy = 1; panel.add(btnHabitaciones, gbc);

        JButton btnReserva = crearBotonMenu("Nueva Reserva / Check-In");
        btnReserva.addActionListener(e -> {
            ((PanelReserva) panelContenido.getComponent(2)).cargarHabitacionesDisponibles();
            mostrarVista("RESERVA");
        });
        gbc.gridy = 2; panel.add(btnReserva, gbc);

        JButton btnSalir = crearBotonMenu("Salir");
        btnSalir.setBackground(new Color(255, 100, 100));
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 3; panel.add(btnSalir, gbc);

        return panel;
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setPreferredSize(new Dimension(300, 60));
        return btn;
    }

    public void mostrarVista(String nombreVista) {
        cardLayout.show(panelContenido, nombreVista);
    }
}
