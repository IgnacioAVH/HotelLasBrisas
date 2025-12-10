package Vista;
import Controller.HotelController;
import Vista.VistaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            HotelController controller = new HotelController();
            VistaPrincipal vista = new VistaPrincipal(controller);
            vista.setVisible(true);
        });
    }
}