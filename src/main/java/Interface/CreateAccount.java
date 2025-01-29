package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class CreateAccount extends JFrame {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField nomField;
    private JCheckBox hommeCheckBox;
    private JCheckBox femmeCheckBox;

    private Image imageUniversity = new ImageIcon(Login.class.getResource("/images/logo1.png")).getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
    private Image imageUser = new ImageIcon(Login.class.getResource("/images/login.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
    private Image imagePassword = new ImageIcon(Login.class.getResource("/images/oeil1.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);

    Based based = new Based();
    Connection connection = based.connect();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateAccount frame = new CreateAccount();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CreateAccount() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#ABDFE1"));
        contentPane.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel placeUniversity = new JLabel("");
        placeUniversity.setBounds(334, 14, 141, 147);
        placeUniversity.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(placeUniversity);
        placeUniversity.setIcon(new ImageIcon(imageUniversity));

        JPanel panelNom = new RoundedPanel(50, new Color(255, 255, 255));
        panelNom.setBounds(214, 171, 387, 53);
        panelNom.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelNom);
        panelNom.setLayout(null);
        
        
    
        

        nomField = new JTextField();
        nomField.setBackground(Color.WHITE);
        nomField.setBorder(null);
        nomField.setFont(new Font("Tahoma", Font.BOLD, 14));
        nomField.setText("Nom Complet");
        nomField.setBounds(20, 10, 226, 33);
        panelNom.add(nomField);
        nomField.setColumns(10);
        

        JPanel panelEmail = new RoundedPanel(50, new Color(255, 255, 255));
        panelEmail.setBounds(214, 236, 387, 53);
        panelEmail.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelEmail);
        panelEmail.setLayout(null);

        emailField = new JTextField();
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(null);
        emailField.setFont(new Font("Tahoma", Font.BOLD, 14));
        emailField.setText("Email");
        emailField.setBounds(20, 10, 226, 33);
        panelEmail.add(emailField);
        emailField.setColumns(10);

        JPanel panelUsername = new RoundedPanel(50, new Color(255, 255, 255));
        panelUsername.setBounds(214, 301, 387, 53);
        panelUsername.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelUsername);
        panelUsername.setLayout(null);
        

        usernameField = new JTextField();
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(null);
        usernameField.setFont(new Font("Tahoma", Font.BOLD, 14));
        usernameField.setText("Username");
        usernameField.setBounds(20, 10, 226, 33);
        panelUsername.add(usernameField);
        usernameField.setColumns(10);

        JPanel panelPassword = new RoundedPanel(50, new Color(255, 255, 255));
        panelPassword.setBounds(214, 366, 387, 53);
        panelPassword.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelPassword);
        panelPassword.setLayout(null);

        passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(null);
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordField.setText("Password");
        passwordField.setBounds(20, 10, 226, 33);
        panelPassword.add(passwordField);

        hommeCheckBox = new JCheckBox("Homme");
        hommeCheckBox.setBounds(280, 440, 80, 30);
        hommeCheckBox.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(hommeCheckBox);

        femmeCheckBox = new JCheckBox("Femme");
        femmeCheckBox.setBounds(430, 440, 80, 30);
        femmeCheckBox.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(femmeCheckBox);

        hommeCheckBox.addActionListener(e -> {
            if (hommeCheckBox.isSelected()) femmeCheckBox.setSelected(false);
        });

        femmeCheckBox.addActionListener(e -> {
            if (femmeCheckBox.isSelected()) hommeCheckBox.setSelected(false);
        });

        JPanel panelCreateAccount = new RoundedPanel(50, Color.decode("#FFA500"));
        panelCreateAccount.setBounds(310, 480, 200, 50);
        contentPane.add(panelCreateAccount);
        panelCreateAccount.setBackground(Color.decode("#ABDFE1"));
        panelCreateAccount.setLayout(null);

        JLabel lblCreateAccount = new JLabel("Créer un compte");
        lblCreateAccount.setForeground(Color.decode("#1466b8"));
        lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCreateAccount.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblCreateAccount.setBounds(0, 0, 200, 50);
        panelCreateAccount.add(lblCreateAccount);

        lblCreateAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String nomComplet = nomField.getText();
                String genre = hommeCheckBox.isSelected() ? "Homme" : femmeCheckBox.isSelected() ? "Femme" : null;

                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || nomComplet.isEmpty() || genre == null) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String sqlUser = "INSERT INTO user (nomcomplet, email) VALUES (?, ?)";
                        PreparedStatement stmtUser = connection.prepareStatement(sqlUser);
                        stmtUser.setString(1, nomComplet);
                        stmtUser.setString(2, email);
                        stmtUser.executeUpdate();

                        String sqlAuth = "INSERT INTO authentification (username, password, genre) VALUES (?, ?, ?)";
                        PreparedStatement stmtAuth = connection.prepareStatement(sqlAuth);
                        stmtAuth.setString(1, username);
                        stmtAuth.setString(2, password);
                        stmtAuth.setString(3, genre);
                        stmtAuth.executeUpdate();

                        JOptionPane.showMessageDialog(CreateAccount.this, "Compte créé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        CreateAccount.this.dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(CreateAccount.this, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        JLabel lblClose = new JLabel("X");
        lblClose.setBounds(800, 10, 50, 28);
        lblClose.setForeground(Color.WHITE);
        lblClose.setHorizontalAlignment(SwingConstants.CENTER);
        lblClose.setFont(new Font("Tahoma", Font.BOLD, 23));
        contentPane.add(lblClose);

        lblClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CreateAccount.this.dispose();
            }
        });

        setLocationRelativeTo(null);
    }
}
