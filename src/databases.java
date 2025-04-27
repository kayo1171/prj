import javax.swing.*;
import java.sql.*;
import java.awt.*;
import static ui.UIUtils.*;

public class databases extends JFrame {
private JTable table;
private JLabel topLabel;
private JLabel searchLabel;
private JLabel sortLabel;
private JTextField searchField;
private JButton homeButton;
private JButton deleteButton;
private JButton editButton;
private JButton makeAppointmentButton;
private JComboBox colComboBox;
private JPanel panel;

public databases() {
	panel = new JPanel();
	panel.setBackground(new Color(255, 204, 204));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

	panel.setBackground(new Color(255, 204, 204));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

	SmoothLabel topLabel = new SmoothLabel("patients");
	topLabel.setFont(new Font("Sans-Serif", Font.BOLD, 40));
	topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	SmoothLabel sortLabel = new SmoothLabel("sort:");
	sortLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 24));

	SmoothLabel searchLabel = new SmoothLabel("search:");
	searchLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 24));

	table = createStyledJTable();
	searchField = createStyledTextField();
	homeButton = makeCustomButton("home");
	deleteButton = makeCustomButton("delete");
	editButton = makeCustomButton("edit");
	makeAppointmentButton = makeCustomButton("make appointment");
	colComboBox = createStyledComboBox();


	panel.add(Box.createVerticalGlue());
	panel.add(topLabel);
	panel.add(Box.createRigidArea(new Dimension(0, 30)));


//	public static JPanel createInputRow(JLabel label, JTextField field) {
//		JPanel row = new JPanel();
//		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
//		row.setOpaque(false);
//		label.setPreferredSize(new Dimension(120, 40));
//		row.add(label);
//		row.add(Box.createRigidArea(new Dimension(20, 0)));
//		row.add(field);
//		return row;
//	}



	setTitle("databases");
	setSize(1000, 600);
	setContentPane(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setVisible(true);
	setResizable(false);



}

}
