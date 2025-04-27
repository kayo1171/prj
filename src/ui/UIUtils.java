package ui;

import javax.swing.*;
import java.awt.*;

public class UIUtils {

public static JLabel createStyledLabel(String text) {
	JLabel label = new JLabel(text) {
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			super.paintComponent(g2);
		}
	};
	label.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
	return label;
}

public static JTextField createStyledTextField() {
	JTextField field = new JTextField();
	field.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
	field.setPreferredSize(new Dimension(300, 45));
	field.setMaximumSize(new Dimension(300, 45));
	field.setBorder(BorderFactory.createLineBorder(new Color(255, 169, 169), 3, false));
	return field;
}

public static JButton makeCustomButton(String text) {
	JButton button = new JButton(text) {
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			super.paintComponent(g2);
		}
	};
	button.setFocusPainted(false);
	button.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
	button.setAlignmentX(Component.CENTER_ALIGNMENT);
	button.setBackground(Color.WHITE);
	button.setForeground(Color.BLACK);
	button.setPreferredSize(new Dimension(300, 55));
	button.setMaximumSize(new Dimension(300, 55));
	button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
	return button;
}

public static JPanel wrapCentered(JPanel inner) {
	JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	wrapper.setOpaque(false);
	wrapper.add(inner);
	return wrapper;
}

public static JPanel createInputRow(JLabel label, JTextField field) {
	JPanel row = new JPanel();
	row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
	row.setOpaque(false);
	label.setPreferredSize(new Dimension(120, 40));
	row.add(label);
	row.add(Box.createRigidArea(new Dimension(20, 0)));
	row.add(field);
	return row;
}

public static JPanel createStyledFormPanel(String[] labels, JTextField[] fields) {
	JPanel panel = new JPanel(new GridLayout(labels.length, 2, 10, 10));
	panel.setBackground(new Color(255, 235, 238)); // light pink background
	panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	Font labelFont = new Font("Sans-Serif", Font.PLAIN, 18);

	for (int i = 0; i < labels.length; i++) {
		JLabel label = new JLabel(labels[i]);
		label.setFont(labelFont);
		panel.add(label);
		fields[i] = createStyledTextField();
		panel.add(fields[i]);
	}

	return panel;
}

public static void stylePopups() {
	UIManager.put("OptionPane.background", new Color(255, 204, 204)); // Background color
	UIManager.put("Panel.background", new Color(255, 204, 204)); // Background of panel inside
	UIManager.put("Button.background", Color.WHITE); // Button background
	UIManager.put("Button.foreground", Color.BLACK); // Button text color
	UIManager.put("Button.font", new Font("Sans-Serif", Font.BOLD, 16)); // Button font
	UIManager.put("OptionPane.messageFont", new Font("Sans-Serif", Font.PLAIN, 18)); // Message font
	UIManager.put("OptionPane.buttonFont", new Font("Sans-Serif", Font.BOLD, 16)); // Button font
	UIManager.put("OptionPane.messageForeground", Color.BLACK); // Message text color
}

public static void styleTable(JTable table) {
	table.setRowHeight(30);
	table.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
	table.setForeground(Color.BLACK);
	table.setBackground(Color.WHITE);
	table.setSelectionBackground(new Color(255, 153, 153));
	table.setSelectionForeground(Color.BLACK);

	table.getTableHeader().setFont(new Font("Sans-Serif", Font.BOLD, 18));
	table.getTableHeader().setBackground(new Color(255, 153, 153));
	table.getTableHeader().setForeground(Color.BLACK);
	table.getTableHeader().setOpaque(false);

	table.setGridColor(new Color(255, 204, 204));
}

public static void styleComboBox(JComboBox comboBox) {
	comboBox.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
	comboBox.setBackground(Color.WHITE);
	comboBox.setForeground(Color.BLACK);
	comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
	comboBox.setFocusable(false);
}

public static class SmoothLabel extends JLabel {
	public SmoothLabel(String text) {
		super(text);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
	}
}
}
