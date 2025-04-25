import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import static ui.UIUtils.*;


public class register extends JFrame {

private JTextField usernameField;
private JTextField passwordField;
private JButton registerBtn;
private JLabel registerLabel;
private JLabel usernameLabel;
private JLabel passwordLabel;
private JPanel panel;

public register() {

    panel = new JPanel();
    panel.setBackground(new Color(255, 204, 204));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    SmoothLabel registerLabel = new SmoothLabel("register");
    registerLabel.setFont(new Font("Sans-Serif", Font.BOLD, 40));
    registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    usernameField = createStyledTextField();
    passwordField = createStyledTextField();
    usernameLabel = createStyledLabel("username");
    passwordLabel = createStyledLabel("password");
    registerBtn = makeCustomButton("register");

    panel.add(Box.createVerticalGlue());
    panel.add(registerLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    panel.add(wrapCentered(createInputRow(usernameLabel, usernameField)));
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    panel.add(wrapCentered(createInputRow(passwordLabel, passwordField)));
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    panel.add(registerBtn);
    panel.add(Box.createVerticalGlue());

    setTitle("register");
    setSize(530, 400);
    setContentPane(panel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);

    // akshun

    registerBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = usernameField.getText();
            String enteredPass = passwordField.getText();
            String hashedPass = BCrypt.hashpw(enteredPass, BCrypt.gensalt());

            if (
                    userName == null || userName.trim().isEmpty() ||
                            enteredPass == null || enteredPass.trim().isEmpty() ||
                            hashedPass == null || hashedPass.trim().isEmpty()
            ) {
                registerLabel.setForeground(Color.RED);
                registerLabel.setText("Insertion cannot contain null values.");
                System.out.println("Insertion cannot contain null or empty values.");
                return;
            }

            try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bld", "root", "root");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO bld.Users (username, password) VALUES (?, ?)");
                stmt.setString(1, userName);
                stmt.setString(2, hashedPass);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("successful insertion");
                } else {
                    System.out.println("unsuccessful insertion");
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                registerLabel.setForeground(Color.RED);
                registerLabel.setText("Database error.");
            }
        }
    });

    }
}

