package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Login extends JFrame {
    private JPanel contentPane;
    private JTextField Username;
    private JPasswordField Password;
    private Image imageUniversity = new ImageIcon(Login.class.getResource("/images/logo1.png")).getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
    private Image imageMan = new ImageIcon(Login.class.getResource("/images/login.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
    private Image ImagePassword = new ImageIcon(Login.class.getResource("/images/oeil1.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
    private Image imageCle = new ImageIcon(Login.class.getResource("/images/cle.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
    private Image imageStop = new ImageIcon(Login.class.getResource("/images/stopp.png")).getImage().getScaledInstance(75, 45, Image.SCALE_SMOOTH);

    private String inputLogin, inputPassword;
    Based based = new Based();
    Connection objetConnection = based.connect();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 449);
        contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#ABDFE1")); // Couleur de fond
        contentPane.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelUsername = new RoundedPanel(50, new Color(255, 255, 255));
        panelUsername.setBounds(214, 171, 387, 53);
        panelUsername.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelUsername);
        panelUsername.setLayout(null);

        Username = new JTextField();
        Username.setBackground(Color.WHITE); // Couleur des champs texte
        Username.setBorder(null);
        Username.setFont(new Font("Tahoma", Font.BOLD , 14));
        Username.setText("Username");
        Username.setBounds(20, 10, 226, 33);
        panelUsername.add(Username);
        Username.setColumns(10);

        JLabel placeMan = new JLabel("");
        placeMan.setBackground(Color.LIGHT_GRAY);
        placeMan.setHorizontalAlignment(SwingConstants.CENTER);
        placeMan.setBounds(309, 10, 68, 33);
        panelUsername.add(placeMan);

        JPanel panelPassword = new RoundedPanel(50, new Color(255, 255, 255));
        panelPassword.setBounds(214, 236, 387, 53);
        panelPassword.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panelPassword);
        panelPassword.setLayout(null);

        Password = new JPasswordField();
        Password.setBorder(null);
        Password.setBackground(Color.WHITE); // Couleur des champs texte
        Password.setFont(new Font("Tahoma", Font.BOLD , 14));
        Password.setText("password");
        Password.setBounds(20, 10, 221, 33);
        panelPassword.add(Password);

        JLabel placePassword = new JLabel("");
        placePassword.setBackground(SystemColor.control);
        placePassword.setHorizontalAlignment(SwingConstants.CENTER);
        placePassword.setBounds(309, 10, 68, 33);
        panelPassword.add(placePassword);

        // Ajouter un MouseListener pour afficher/masquer le mot de passe sur l'image
        placePassword.setIcon(new ImageIcon(ImagePassword));
        placePassword.addMouseListener(new MouseAdapter() {
            private boolean isPasswordVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible = !isPasswordVisible;
                Password.setEchoChar(isPasswordVisible ? (char) 0 : '*');
            }
        });

        JPanel panelLogin = new RoundedPanel(50, Color.decode("#FFA500"));
        panelLogin.setBounds(310, 300, 200, 50); // Taille réduite
        panelLogin.setBackground(Color.decode("#ABDFE1")); // Couleur modifiée
        contentPane.add(panelLogin);
        panelLogin.setLayout(null);

        JLabel lblNewLabel = new JLabel("LOGIN");
        lblNewLabel.setForeground(UIManager.getColor("CheckBox.interiorBackground"));
        lblNewLabel.setForeground(Color.decode("#1466b8")); // Couleur police
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20)); // Taille de police ajustée si nécessaire
        lblNewLabel.setBounds(0, 0, 200, 50);
        panelLogin.add(lblNewLabel);

        
        JLabel lblCreateAccount = new JLabel("Vous n'avez pas un compte? Inscrivez-vous");
        lblCreateAccount.setBounds(250, 375, 300, 20); // Ajustez la largeur pour éviter les coupures
        lblCreateAccount.setForeground(Color.decode("#1466b8"));
        lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblCreateAccount);


        
        
      
        JLabel placeCle = new JLabel("");
        placeCle.setBounds(249, 10, 55, 41);
        panelLogin.add(placeCle);

        JLabel placeUnversity = new JLabel("");
        placeUnversity.setBounds(334, 14, 141, 147);
        placeUnversity.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(placeUnversity);

        JLabel lblNewLabel_1 = new JLabel("X");
        lblNewLabel_1.setBounds(800, 10, 50, 28);
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 23));
        contentPane.add(lblNewLabel_1);

        placeUnversity.setIcon(new ImageIcon(imageUniversity));
        placeMan.setIcon(new ImageIcon(imageMan));
        placeCle.setIcon(new ImageIcon(imageCle));

        JLabel attention = new JLabel("");
        attention.setBounds(250, 391, 312, 48);
        attention.setForeground(Color.RED);
        attention.setHorizontalAlignment(SwingConstants.CENTER);
        attention.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPane.add(attention);

        JLabel positionStop = new JLabel("");
        positionStop.setBounds(513, 394, 95, 45);
        positionStop.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(positionStop);
        setLocationRelativeTo(null);

        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login.this.dispose();
            }
        });

        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputLogin = Username.getText();
                inputPassword = Password.getText();
                if (inputLogin.isEmpty() || inputPassword.isEmpty()) {
                    attention.setText("Veuillez remplir tous champs");
                    //positionStop.setIcon(new ImageIcon(imageStop));
                } else {
                    try {
                        String requetteVerification = "select * from authentification where username='" + inputLogin + "' and password='" + inputPassword + "'";
                        Statement statementVerification = objetConnection.createStatement();
                        ResultSet resultatVerification = statementVerification.executeQuery(requetteVerification);
                        if (!resultatVerification.next()) {
                            attention.setText("Login ou mot de passe incorrect");
                            //positionStop.setIcon(new ImageIcon(imageStop));
                        } else {
                            String genree = resultatVerification.getString("genre");
                            AccueilChoix.main(null, inputLogin);
                            Login.this.dispose();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        lblCreateAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CreateAccount.main(null);
                Login.this.dispose();
            }
        });
    }
}