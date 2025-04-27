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


	String[] columnNames = {"PatientID", "Name", "Age", "Phone", "BloodType"};
	Object[][] data = {};

	// make hte cells uneditable
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames) {

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	table = new JTable(model);

	table.setRowHeight(30);
	table.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
	table.getTableHeader().setFont(new Font("Sans-Serif", Font.BOLD, 18));
	table.setFillsViewportHeight(true);

	JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setPreferredSize(new Dimension(600, 300));

	searchField = createStyledTextField();
	homeButton = makeCustomButton("home");
	deleteButton = makeCustomButton("delete");
	editButton = makeCustomButton("edit");
	makeAppointmentButton = makeCustomButton("make appointment");
	colComboBox = new JComboBox(new String[]{"PatientID", "Name", "Age", "Phone", "BloodType"});

// component adding
	panel.add(Box.createVerticalGlue());
	panel.add(topLabel);
	panel.add(Box.createRigidArea(new Dimension(0, 30)));

	JPanel row = new JPanel();
	row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
	row.setOpaque(false);
	row.add(sortLabel);
	row.add(Box.createRigidArea(new Dimension(10, 0)));
	row.add(colComboBox);
	row.add(Box.createRigidArea(new Dimension(20, 0)));
	row.add(searchLabel);
	row.add(Box.createRigidArea(new Dimension(10, 0)));
	row.add(searchField);
	panel.add(wrapCentered(row));

	panel.add(scrollPane);
	panel.add(Box.createVerticalStrut(20)); // add margin after table
	panel.add(Box.createVerticalGlue());

	setTitle("databases");
	setSize(1000, 600);
	setContentPane(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setVisible(true);
	setResizable(false);
	loadPatients();

}
private void loadPatients() {
	String url = "jdbc:mysql://localhost:3306/bld";
	String username = "root";
	String password = "root";

	try (Connection conn = DriverManager.getConnection(url, username, password)) {
		String query = "SELECT PatientID, Name, Age, Phone, BloodType FROM Patients";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		// get table model and clear it first
		javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
		model.setRowCount(0); // clears current rows

		while (rs.next()) {
			Object[] row = new Object[]{
							rs.getInt("PatientID"),
							rs.getString("Name"),
							rs.getInt("Age"),
							rs.getString("Phone"),
							rs.getString("BloodType")
			};
			model.addRow(row);
		}

	} catch (SQLException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(this, "error loading data from db", "Error", JOptionPane.ERROR_MESSAGE);
	}
}



}
