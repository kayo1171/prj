import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import static ui.UIUtils.*;

public class databases extends JFrame {
private JTable table;
private JLabel topLabel;
private JLabel searchLabel;
private JLabel sortLabel;
private JTextField searchField;
private JButton deleteButton;
private JButton editButton;
private JButton makeAppointmentButton;
private JComboBox colComboBox;
private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter; // used for sorting using combobobx

public databases() {
	JPanel panel = new JPanel();
	panel.setBackground(new Color(255, 204, 204));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

	SmoothLabel topLabel = new SmoothLabel("patients");
	topLabel.setFont(new Font("Sans-Serif", Font.BOLD, 40));
	topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	SmoothLabel sortLabel = new SmoothLabel("sort [a-Z]:");
	sortLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 24));

	SmoothLabel searchLabel = new SmoothLabel("search name:");
	searchLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 24));

	String[] columnNames = {"PatientID", "Name", "Age", "Phone", "BloodType"};
	Object[][] data = {};

	// make the cells uneditable
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

	searchField.setMaximumSize(new Dimension(500, 45));
	searchField.setPreferredSize(new Dimension(500, 45));
	deleteButton = makeCustomButton("delete");
	editButton = makeCustomButton("edit");
	makeAppointmentButton = makeCustomButton("add");
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

	// action buttons
	JPanel buttonRow = new JPanel();
	buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));
	buttonRow.setOpaque(false);
	buttonRow.add(makeAppointmentButton);
	buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
	buttonRow.add(editButton);
	buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
	buttonRow.add(deleteButton);

	panel.add(wrapCentered(buttonRow));
	panel.add(Box.createVerticalGlue());

	setTitle("databases");
	setSize(1000, 600);
	setContentPane(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setVisible(true);
	setResizable(false);

	loadPatients();
	buttonsFunctions();
	stylePopups();
	styleTable(table);
	styleComboBox(colComboBox);

	// sorting mechanism
	colComboBox.addActionListener(e -> {
		if (sorter == null) return;

		String selectedColumn = (String) colComboBox.getSelectedItem();
		int columnIndex = table.getColumnModel().getColumnIndex(selectedColumn);

		sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING)));
		sorter.sort();
	});

	searchField.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			if (sorter == null) return;

			String text = searchField.getText();
			if (text.trim().isEmpty()) {
				sorter.setRowFilter(null);
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // search by name, the regex makes it case insensitive
			}
		}
	});
}

// loads patients from the database
private void loadPatients() {
	String url = "jdbc:mysql://localhost:3306/bld";
	String username = "root";
	String password = "root";

	try (Connection conn = DriverManager.getConnection(url, username, password)) {
		String query = "SELECT PatientID, Name, Age, Phone, BloodType FROM Patients";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
		model.setRowCount(0); // clear the model so u can refresh it

		while (rs.next()) {
			Object[] row = new Object[]{
							rs.getInt("PatientID"),
							rs.getString("Name"),
							rs.getInt("Age"),
							rs.getString("Phone"),
							rs.getString("BloodType")
			};
			model.addRow(row);

			// allows to sort things with the combObox
			sorter = new javax.swing.table.TableRowSorter<>(model);
			table.setRowSorter(sorter);

		}

	} catch (SQLException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(this, "error loading data from db", "error", JOptionPane.ERROR_MESSAGE);
	}
}

//the button activities
private void buttonsFunctions() { makeAppointmentButton.addActionListener(e -> { JTextField[] fields = new JTextField[4];
		String[] labels = {"name:", "age:", "phone:", "blood type:"};
		JPanel form = createStyledFormPanel(labels, fields);

		int result = JOptionPane.showConfirmDialog(this, form, "add new patient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String name = fields[0].getText();
			String ageStr = fields[1].getText();
			String phone = fields[2].getText();
			String bloodType = fields[3].getText();

			if (name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() || bloodType.isEmpty()) {
				JOptionPane.showMessageDialog(this, "must fill out all fields", "warning", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bld", "root", "root")) {
				String query = "INSERT INTO Patients (Name, Age, Phone, BloodType) VALUES (?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, name);
				stmt.setInt(2, Integer.parseInt(ageStr));
				stmt.setString(3, phone);
				stmt.setString(4, bloodType);
				stmt.executeUpdate();
				loadPatients();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "error adding patient", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
	});

	editButton.addActionListener(e -> {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "select patient to edit", "warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// get current vaues of the patient
		String id = table.getValueAt(selectedRow, 0).toString();
		String currentName = table.getValueAt(selectedRow, 1).toString();
		String currentAge = table.getValueAt(selectedRow, 2).toString();
		String currentPhone = table.getValueAt(selectedRow, 3).toString();
		String currentBloodType = table.getValueAt(selectedRow, 4).toString();

		JTextField[] fields = new JTextField[4];
		String[] labels = {"Name:", "Age:", "Phone:", "Blood Type:"};
		JPanel form = createStyledFormPanel(labels, fields);

		fields[0].setText(currentName);
		fields[1].setText(currentAge);
		fields[2].setText(currentPhone);
		fields[3].setText(currentBloodType);

		int result = JOptionPane.showConfirmDialog(this, form, "edit patient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bld", "root", "root")) {
				String query = "UPDATE Patients SET Name=?, Age=?, Phone=?, BloodType=? WHERE PatientID=?";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, fields[0].getText());
				stmt.setInt(2, Integer.parseInt(fields[1].getText()));
				stmt.setString(3, fields[2].getText());
				stmt.setString(4, fields[3].getText());
				stmt.setInt(5, Integer.parseInt(id));
				stmt.executeUpdate();
				loadPatients();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "error updating patietn", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
	});

	deleteButton.addActionListener(e -> {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) { // -1 means it's not selected
			JOptionPane.showMessageDialog(this, "select patient to delete", "warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "are you sure you want to delete this patient", "confirm", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String id = table.getValueAt(selectedRow, 0).toString();
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bld", "root", "root")) {
				String query = "DELETE FROM Patients WHERE PatientID=?";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt(id));
				stmt.executeUpdate();
				loadPatients();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "error deleting patient", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
	});
}
}
