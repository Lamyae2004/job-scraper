package Interface;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Consulter extends JFrame {

	private JPanel contentPane;
	private Image imageWelcome= new ImageIcon(Login.class.getResource("/images/bonjour.png")).getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH);
	private Image imageProfil= new ImageIcon(Login.class.getResource("/images/femelle.png")).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
	private Image imageBack= new ImageIcon(Login.class.getResource("/images/logo1.png")).getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH);
	private Image imageStop= new ImageIcon(Login.class.getResource("/images/stopp.png")).getImage().getScaledInstance(75,45,Image.SCALE_SMOOTH);
	private Image imageUpload= new ImageIcon(Login.class.getResource("/images/table1.png")).getImage().getScaledInstance(75,45,Image.SCALE_SMOOTH);
	private Image imageconsult= new ImageIcon(Login.class.getResource("/images/consult.png")).getImage().getScaledInstance(75,45,Image.SCALE_SMOOTH);



	Based based = new Based();
	Connection objetConnection=based.connect();
	private JTable table;
	static String namee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args,String userName) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					namee=userName;
					Consulter frame = new Consulter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Consulter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1211, 756);
		//setBounds(100, 100, 868, 394);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#ABDFE1"));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);



		JLabel placeProfil = new JLabel("");
		placeProfil.setHorizontalAlignment(SwingConstants.CENTER);
		placeProfil.setBounds(1080, 0, 98, 103);
		contentPane.add(placeProfil);
		placeProfil.setIcon(new ImageIcon(imageProfil));






		/*/
		/*/
		JLabel Username = new JLabel("inasse");
		Username.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		Username.setHorizontalAlignment(SwingConstants.RIGHT);
		Username.setBounds(840, 21, 250, 50);
		contentPane.add(Username);
		Username.setText(namee);
		Username.setForeground(Color.WHITE);
		Username.setBackground(Color.WHITE);
		JLabel lblNewLabel = new JLabel("X");
		/* Fermet la page lors du clique sur le X */
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Consulter.this.dispose();
			}
		});
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(1140, 0, 71, 46);
		contentPane.add(lblNewLabel);

		/*ajouter image de retour */
		JLabel placeBack = new JLabel("");
		placeBack.setHorizontalAlignment(SwingConstants.CENTER);
		placeBack.setBounds(-1, 1, 141, 94);
		contentPane.add(placeBack);
		placeBack.setIcon(new ImageIcon(imageBack));

		/*JLabel placelogo = new JLabel("");
		placelogo.setHorizontalAlignment(SwingConstants.CENTER);
		placelogo.setBounds(350, 21, 150, 103);
		contentPane.add(placelogo);
		placelogo.setIcon(new ImageIcon(imageconsult));*/

		JLabel placep = new JLabel("Résultats du scraping disponibles ci-dessous");
		placep.setHorizontalAlignment(SwingConstants.CENTER);
		placep.setBounds(280, 120, 600, 50); // Dimensions ajustées
		placep.setForeground(Color.decode("#1466b8"));
		placep.setBackground(Color.decode("#ABDFE1"));
		placep.setFont(new Font("Tahoma", Font.BOLD  , 20));
		placep.setOpaque(true); // Active la couleur de fond
		contentPane.add(placep); // Ajoute le JLabel au contentPane








		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 252, 1195, 494);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel attention = new JLabel("");
		attention.setForeground(new Color(255, 0, 0));
		attention.setFont(new Font("Tahoma", Font.BOLD, 23));
		attention.setHorizontalAlignment(SwingConstants.CENTER);
		attention.setBounds(299, 196, 499, 46);
		contentPane.add(attention);

		JLabel positionStop = new JLabel("");
		positionStop.setHorizontalAlignment(SwingConstants.CENTER);
		positionStop.setBounds(818, 196, 63, 46);
		contentPane.add(positionStop);

		JPanel panel_1 = new RoundedPanel(80,Color.decode("#ABDFE1"));
		panel_1.setBackground(Color.decode("#ABDFE1"));
		panel_1.setBounds(520, 173, 100, 69);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		/*JLabel lblNewLabel_1 = new JLabel("Download");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(37, 10, 134, 49);
		panel_1.add(lblNewLabel_1);*/

		/*JLabel placeTelecharger = new JLabel("télécharger");
		placeTelecharger.setHorizontalAlignment(SwingConstants.CENTER);
		placeTelecharger.setBounds(20, 10, 80, 49);
		panel_1.add(placeTelecharger);
		placeTelecharger.setIcon(new ImageIcon(imageUpload));*/


		JButton btnTelecharger = new JButton("Télécharger");
		btnTelecharger.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTelecharger.setBounds(600, 180, 150, 40); // Position et taille du bouton
		//btnTelecharger.setBackground(Color.decode("#1466b8"));
		btnTelecharger.setBackground(Color.decode("#FFA500"));
		//btnTelecharger.setForeground(Color.WHITE);
		btnTelecharger.setForeground(Color.decode("#1466b8"));
		btnTelecharger.setFocusPainted(false);
		contentPane.add(btnTelecharger);





		// Ajout du bouton "Personnaliser"
		JButton btnPersonnaliser = new JButton("Personnaliser");
		btnPersonnaliser.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPersonnaliser.setBounds(430, 180, 150, 40); // Position et taille du bouton
		//btnPersonnaliser.setBackground(Color.decode("#1466b8"));
		btnPersonnaliser.setBackground(Color.decode("#FFA500"));
		//btnPersonnaliser.setForeground(Color.WHITE);
		btnPersonnaliser.setForeground(Color.decode("#1466b8"));
		btnPersonnaliser.setFocusPainted(false);
		contentPane.add(btnPersonnaliser);



// Gestionnaire d'événements pour le bouton
		btnPersonnaliser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Ouvrir l'interface JobFilterApp
				//JobFilterApp.main(null);

				JobFilterApp jobFilterApp = new JobFilterApp();
				jobFilterApp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ne ferme que cette fenêtre
				jobFilterApp.setVisible(true);


			}
		});



		/*__________________ lorsque vous cliquez sur l'image de retour_____________________ */
		placeBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Consulter.this.dispose();
				AccueilChoix.main(null, namee);
			}
		});

		/*_lorsque vous cliquez sur telecharger________________________ */
		btnTelecharger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String requetteVerificationDonnees="SELECT * FROM dataset WHERE 1";
					Statement statementVerificationDonnees=objetConnection.createStatement();
					ResultSet resultatVerificationDonnees=statementVerificationDonnees.executeQuery(requetteVerificationDonnees);
					if(!resultatVerificationDonnees.next()) {
						attention.setText("No data to download! ");
					}
					else {
						ImportDataToExcel.main(null);
						attention.setText("Excel downloaded successfully");
						attention.setBackground(Color.GREEN);
					}
				}catch(Exception excep) {
					excep.printStackTrace();
				}
			}
		});

		/*__________________________________________ Selectionner les donn es_*/
		try {
			UpdateNullFields.main(new String[]{});
			String requetteVerification="SELECT * FROM dataset WHERE 1";
			Statement statementVerification=objetConnection.createStatement();
			ResultSet resultatVerification=statementVerification.executeQuery(requetteVerification);

			if(resultatVerification.next()) {
				String requette ="select * from dataset";
				Statement statement=objetConnection.createStatement();
				ResultSet resultat=statement.executeQuery(requette);
				while(resultat.next()) {
					int id = resultat.getInt("id");
					String siteName = resultat.getString("Sitename");
					String postName = resultat.getString("Postename");
					String postDescription = resultat.getString("PostDescription");
					String profilDescription = resultat.getString("ProfilDescription");
					String entrepriseName = resultat.getString("EntrepriseName");
					String entrepriseDescription = resultat.getString("EntrepriseDescription");
					String entrepriseAdress = resultat.getString("EntreprisAdress");
					String region = resultat.getString("region");
					String ville = resultat.getString("Ville");
					String secteurActivite = resultat.getString("SecteurActivite");
					String metier = resultat.getString("Metier");
					String experience = resultat.getString("Experience");
					String niveauEtude = resultat.getString("NiveauEtude");
					String langue = resultat.getString("Langue");
					String hardSkills = resultat.getString("hardSkills");
					String softSkills = resultat.getString("SoftSkills");
					String datePublication = resultat.getString("DatePublication");
					String dateLimitePostuler = resultat.getString("DateLimitePostuler");
					String nombrePoste = resultat.getString("NombrePoste");
					String typeContrat = resultat.getString("TypeContrat");
					String teletravail = resultat.getString("Teletravail");
					String lienPostuler = resultat.getString("LienPostuler");
					String salaireMensuel = resultat.getString("SalaireMensuel");
					String reference = resultat.getString("Reference");
					String EntrepriseSite = resultat.getString("EntrepriseSite");
					String Spécialité = resultat.getString("Spécialité");
					String ProfilRecherché = resultat.getString("ProfilRecherché");
					String TraitsPersonnalite = resultat.getString("TraitsPersonnalite");
					String CompétenceRecommandées = resultat.getString("CompétenceRecommandées");
					String NiveauLangue = resultat.getString("NiveauLangue");
					String AvantagesSociaux = resultat.getString("AvantagesSociaux");




					table.setModel(DbUtils.resultSetToTableModel(resultat));
				}
			}
			else {
				String requette ="select * from  dataset";
				Statement statement=objetConnection.createStatement();
				ResultSet resultat=statement.executeQuery(requette);
				table.setModel(DbUtils.resultSetToTableModel(resultat));
				positionStop.setIcon(new ImageIcon(imageStop));
				attention.setText("Run Scrapping first");

			}


		}catch(Exception exception) {
			exception.printStackTrace();
		}


	}
}