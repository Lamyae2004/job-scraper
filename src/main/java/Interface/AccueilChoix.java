package Interface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class AccueilChoix extends JFrame {

    private JPanel contentPane;
    private Image imageProfil = new ImageIcon(Login.class.getResource("/images/femelle.png")).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
    private Image imageConsulter = new ImageIcon(Login.class.getResource("/images/consult.png")).getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
    private Image imagePrediction = new ImageIcon(Login.class.getResource("/images/imagepré.png")).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    private Image imageScrapp = new ImageIcon(Login.class.getResource("/images/scrap.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image imageModel = new ImageIcon(Login.class.getResource("/images/model1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image imagechose = new ImageIcon(Login.class.getResource("/images/c1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image imageLogOut = new ImageIcon(Login.class.getResource("/images/logout1.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Image pour Log Out

    static String Username, genre;

    public static void main(String[] args, String username) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Username = username;
                    AccueilChoix frame = new AccueilChoix();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AccueilChoix() {
        setState(Frame.ICONIFIED);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 939, 577);
        contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#ABDFE1"));
        contentPane.setBorder(new LineBorder(new Color(0, 0, 128), 4));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null); // Assurez-vous que la fenêtre est centrée sur l'écran
        setVisible(true);


        JLabel placePhoto = new JLabel("");
        placePhoto.setBounds(780, 10, 84, 82);
        placePhoto.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(placePhoto);
        setLocationRelativeTo(null);

        JLabel userName = new JLabel("Username");
        userName.setBounds(600, 21, 164, 66);
        userName.setFont(new Font("Tahoma", Font.BOLD, 21));
        userName.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(userName);

        JLabel consulter = new JLabel("Consult");
        consulter.setBounds(260, 190, 103, 105);
        consulter.setForeground(Color.WHITE);
        consulter.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        contentPane.add(consulter);
        JLabel placeConsulter = new JLabel("");
        placeConsulter.setBounds(350, 205, 84, 89);
        contentPane.add(placeConsulter);
        placeConsulter.setIcon(new ImageIcon(imageConsulter));

        JPanel panel = new RoundedPanel(30, Color.decode("#1980e6"));
        panel.setBounds(250, 186, 205, 124);
        panel.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panel);

        JLabel scrapp = new JLabel("Scrapp");
        scrapp.setBounds(10, 0, 103, 105);
        scrapp.setForeground(Color.WHITE);
        scrapp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        contentPane.add(scrapp);
        JLabel placeScrapp = new JLabel("");
        placeScrapp.setBounds(120, 16, 86, 89);

        JPanel panel_1 = new RoundedPanel(30, Color.decode("#F6C026"));
        panel_1.setBounds(250, 320, 205, 124);
        panel_1.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        panel_1.add(scrapp);
        panel_1.add(placeScrapp);

        JLabel model = new JLabel("Model");
        model.setBounds(10, 0, 103, 105);
        model.setForeground(Color.WHITE);
        model.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        contentPane.add(model);

        JPanel panel_2 = new RoundedPanel(30, Color.decode("#F6C026"));
        panel_2.setBounds(490, 185, 205, 124);
        panel_2.setBackground(Color.decode("#ABDFE1"));
        contentPane.add(panel_2);
        panel_2.setLayout(null);
        panel_2.add(model);
        JLabel placeModel = new JLabel("");
        placeModel.setBounds(104, 16, 80, 89);
        panel_2.add(placeModel);

        JLabel lblPrediction = new JLabel("Visualization");
        lblPrediction.setBounds(740, 400, 112, 105);
        lblPrediction.setForeground(Color.WHITE);
        lblPrediction.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));

        JPanel panel_3 = new RoundedPanel(30,Color.decode("#1980e6"));
        panel_3.setBounds(490, 320, 205, 124);
        panel_3.setBackground(Color.decode("#ABDFE1"));
        panel_3.setLayout(null);
        panel_3.add(lblPrediction);


        JLabel placePrediction = new JLabel("");
        placePrediction.setBounds(100, 10, 103, 89);
        placePrediction.setIcon(new ImageIcon(imagePrediction));
        panel_3.add(placePrediction);

        contentPane.add(panel_3);

        JLabel prediction = new JLabel("Visualization");
        prediction.setBounds(500, 320, 103, 105);
        prediction.setForeground(Color.WHITE);
        prediction.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        contentPane.add(prediction);
        contentPane.setComponentZOrder(prediction, 0);

        // Ajout de l'image pour Log Out
        JLabel labelLogOut = new JLabel(new ImageIcon(imageLogOut));
        labelLogOut.setBounds(850, 10, 80, 80);
        contentPane.add(labelLogOut);

        // Ajout de l'action de log out
        labelLogOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login.main(null); // Retourne à l'écran de login
                AccueilChoix.this.dispose(); // Ferme la fenêtre actuelle
            }
        });

        // Actions sur les autres composants
        placeScrapp.setIcon(new ImageIcon(imageScrapp));
        placeModel.setIcon(new ImageIcon(imageModel));
        userName.setText(Username);
        userName.setForeground(Color.WHITE);
        placePhoto.setIcon(new ImageIcon(imageProfil));

        // Interaction avec Consulter
        consulter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccueilChoix.this.dispose();
                Consulter.main(null, Username);
            }
        });

        // Interaction avec Scrapp
        panel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccueilChoix.this.dispose();
                Scraping.main(null, Username);
            }
        });

        // Interaction avec Model
        panel_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccueilChoix.this.dispose();
                ModelChoix.main(null,Username);
            }
        });

        // Interaction avec Prediction
        prediction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                visualisation.main(null,Username);


            }
        });
    }


}