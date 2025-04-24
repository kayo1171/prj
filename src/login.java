import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame {
    private JTextField usernameField;
    private JButton loginBtn;
    private JTextField passwordField;
    private JLabel loginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel panel;

    public login() {
        panel = new JPanel();
        panel.setBackground(new Color(255, 204, 204));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Login title
        loginLabel = new JLabel("login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paintComponent(g2);
            }
        };
        loginLabel.setFont(new Font("Sans-Serif", Font.BOLD, 40));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create styled components
        usernameField = createStyledTextField();
        passwordField = createStyledTextField();
        usernameLabel = createStyledLabel("username");
        passwordLabel = createStyledLabel("password");
        loginBtn = makeCustomBtn("login");

        panel.add(Box.createVerticalGlue());
        panel.add(loginLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(wrapCentered(createInputRow(usernameLabel, usernameField)));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(wrapCentered(createInputRow(passwordLabel, passwordField)));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(loginBtn);
        panel.add(Box.createVerticalGlue());

        setTitle("login");
        setSize(530, 400);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);


        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = usernameField.getText();
                String enteredPass = passwordField.getText();

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bld", "root", "root");
                    PreparedStatement stmt = conn.prepareStatement("SELECT password FROM Users WHERE username = ?");
                    stmt.setString(1, userName);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String storedHashedPassword = rs.getString("password");
                        if (BCrypt.checkpw(enteredPass, storedHashedPassword)) {
                            loginLabel.setForeground(Color.GREEN);
                            loginLabel.setText("Successful login!");
                            System.out.println("successful login");
                            new dashboard();
                            dispose(); // closes window
                        } else {
                            loginLabel.setForeground(Color.RED);
                            loginLabel.setText("Incorrect password.");
                            System.out.println("wrong password");
                        }
                    } else {
                        loginLabel.setForeground(Color.RED);
                        loginLabel.setText("User not found.");
                        System.out.println("user not found");
                    }

                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    loginLabel.setForeground(Color.RED);
                    loginLabel.setText("Database error.");
                    System.out.println("database error");
                }
            }
        });

    }

    private JPanel wrapCentered(JPanel inner) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(inner);
        return wrapper;
    }

    private JPanel createInputRow(JLabel label, JTextField field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        label.setPreferredSize(new Dimension(120, 40));
        row.add(label);
        row.add(Box.createRigidArea(new Dimension(20, 0)));
        row.add(field);
        return row;
    }

    private JLabel createStyledLabel(String text) {
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

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        field.setPreferredSize(new Dimension(300, 45));
        field.setMaximumSize(new Dimension(300, 45));
        field.setBorder(BorderFactory.createLineBorder(new Color(255, 153, 153), 4, true));
        return field;
    }
    private JButton makeCustomBtn(String text) {
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
        button.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(440, 50));
        button.setMaximumSize(new Dimension(440, 50));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        return button;
    }
}
