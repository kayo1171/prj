import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class databases extends JFrame {
private JTable table;
private JLabel topLabel;
private JTextField searchField;
private JLabel sortLabel;
private JButton homeButton;
private JButton deleteButton;
private JButton editButton;
private JButton makeAppointmentButton;
private JComboBox colComboBox;
private JLabel searchLabel;
private JPanel panel;

public databases() {
	panel = new JPanel();

	panel.setBackground(new Color(255, 204, 204));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

	topLabel = new JLabel("register") {
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			super.paintComponent(g2);
		}
	};

}

}
