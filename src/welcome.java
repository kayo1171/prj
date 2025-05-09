import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static ui.UIUtils.*;

public class welcome extends JFrame {
private JButton goToLoginBtn;
private JButton goToSignUpBtn;
private JLabel logo;
private JLabel welcomeText;
private JPanel panel;

public welcome() {
	panel = new JPanel();
	panel.setBackground(new Color(255, 204, 204));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	// try catch because image keeps breaking
	try {
		ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png"));
		Image scaledImage = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		logo = new JLabel(new ImageIcon(scaledImage));
	} catch (Exception e) {
		logo = new JLabel("Image not found");
	}
	logo.setAlignmentX(Component.CENTER_ALIGNMENT);

	// smooth label is to fix the horrible kerning
	SmoothLabel welcomeText = new SmoothLabel("welcome");
	welcomeText.setFont(new Font("Sans-Serif", Font.BOLD, 40));
	welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

	goToLoginBtn = makeCustomButton("login");
	goToSignUpBtn = makeCustomButton("register");

	// padding
	panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

	panel.add(Box.createVerticalGlue()); //create verticl glue makes things more responsive - centering
	panel.add(logo);
	panel.add(Box.createRigidArea(new Dimension(0, 30))); // opposite, fixed size, not responsive, padding
	panel.add(welcomeText);
	panel.add(Box.createRigidArea(new Dimension(0, 30)));
	panel.add(goToLoginBtn);
	panel.add(Box.createRigidArea(new Dimension(0, 15)));
	panel.add(goToSignUpBtn);
	panel.add(Box.createVerticalGlue());

	setTitle("welcome");
	setSize(440, 625);
	setContentPane(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setVisible(true);
	setResizable(false);

	// ekshъn

	goToLoginBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new login();
			dispose();
		}
	});


	goToSignUpBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new register();
			dispose();
		}
	});
}
}