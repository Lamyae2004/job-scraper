package Interface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Scraping extends JFrame {

	private JPanel contentPane;
	private Image imageBack = new ImageIcon(Login.class.getResource("/images/logo1.png")).getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
	private Image imageProfil= new ImageIcon(Login.class.getResource( "/images/femelle.png")).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
	private Image imageWelcome= new ImageIcon(Login.class.getResource("/images/bonjour.png")).getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH);
	private Image imageChercher= new ImageIcon(Login.class.getResource("/images/chercher.png")).getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH);
	private Image imagescrap= new ImageIcon(Login.class.getResource("/images/scrap.png")).getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);
	static String name;
	Based based = new Based();
	Connection objetConnection=based.connect();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args,String username ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					name=username;
					Scraping frame = new Scraping();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


    public Scraping() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 394);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#ABDFE1"));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel placeBack = new JLabel("");

		placeBack.setHorizontalAlignment(SwingConstants.CENTER);
		placeBack.setBounds(5, 13, 80, 80);
		contentPane.add(placeBack);
		setUndecorated(true);
		setLocationRelativeTo(null);
		placeBack.setIcon(new ImageIcon(imageBack));
		
		
		JLabel placelogo = new JLabel("");
		placelogo.setHorizontalAlignment(SwingConstants.CENTER);
		placelogo.setBounds(350, 21, 150, 103);
		contentPane.add(placelogo);
		placelogo.setIcon(new ImageIcon(imagescrap));

		// Message principal
		JLabel placep = new JLabel("Choisissez une source pour lancer le scraping");
		placep.setHorizontalAlignment(SwingConstants.CENTER);
		placep.setBounds(120, 120, 600, 50);
		placep.setForeground(Color.decode("#1466b8"));
		placep.setFont(new Font("Tahoma", Font.BOLD, 20));
		placep.setOpaque(true);
		placep.setBackground(Color.decode("#ABDFE1"));
		contentPane.add(placep);

		// Combo box et lien
		JPanel linkPanel = new JPanel();
		linkPanel.setBounds(240, 180, 400, 100);
		linkPanel.setLayout(null);
		linkPanel.setBackground(Color.decode("#ABDFE1"));
		contentPane.add(linkPanel);

		// JComboBox pour sélectionner les sites
		JComboBox<String> siteChooser = new JComboBox<>();
		siteChooser.addItem("rekrute.ma");
		siteChooser.addItem("emploi.com");
		siteChooser.addItem("talenso.com");
		siteChooser.addItem("Optioncarrière.com");
		siteChooser.addItem("Marocemploi.com");
		siteChooser.addItem("MenaraJob.com");
		siteChooser.addItem("Jobrapido.com");
		siteChooser.addItem("job2vente.com");
		siteChooser.addItem("Bayt.com");
		siteChooser.addItem("anapec.com");
		siteChooser.addItem("MarocAnnonce.com");

		siteChooser.setBounds(100, 10, 200, 30);
		siteChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		siteChooser.setBackground(Color.white); // Couleur de fond
		siteChooser.setForeground(Color.decode("#1466b8")); 
		linkPanel.add(siteChooser);
		
		siteChooser.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        if (isSelected) {
		            renderer.setBackground(Color.decode("#1466b8")); // Couleur de fond si sélectionné
		            renderer.setForeground(Color.WHITE); // Couleur du texte si sélectionné
		        } else {
		            renderer.setBackground(Color.decode("#DFF6FF")); // Couleur de fond par défaut
		            renderer.setForeground(Color.decode("#1466b8")); // Couleur de texte par défaut
		        }
		        return renderer;
		    }
		});
		


		// Panneau blanc pour afficher le lien
		JPanel whitePanel = new JPanel();
		whitePanel.setBounds(50, 50, 300, 30);
		whitePanel.setBackground(Color.WHITE);
		whitePanel.setLayout(null);
		linkPanel.add(whitePanel);

		// JLabel pour afficher le lien sélectionné
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(5, 5, 360, 20);
		whitePanel.add(lblNewLabel);

		// ActionListener pour mettre à jour le lien
		siteChooser.addActionListener(e -> {
		    String siteChoisi = (String) siteChooser.getSelectedItem();
		    lblNewLabel.setText("https://www." + siteChoisi + "/");
		});

		// Bouton Scrapper avec coins arrondis
		RoundedPanel panel = new RoundedPanel(50, Color.decode("#FFA500"));
		panel.setBounds(370, 300, 120, 50);
		panel.setBackground(Color.decode("#ABDFE1")); // Fond assorti
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lancer = new JLabel("Scrapper");
		lancer.setBounds(10, 0, 100, 50);
		panel.add(lancer);
		lancer.setForeground(Color.decode("#1466b8"));
		lancer.setFont(new Font("Tahoma", Font.BOLD, 20));
		lancer.setHorizontalAlignment(SwingConstants.CENTER);

        // Fermer la page
        JLabel fermerPage = new JLabel("X");
        fermerPage.setFont(new Font("Tahoma", Font.BOLD, 23));
        fermerPage.setForeground(Color.WHITE);
        fermerPage.setHorizontalAlignment(SwingConstants.CENTER);
        fermerPage.setBounds(827, 0, 41, 39);
        fermerPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Scraping.this.dispose();
            }
        });
        contentPane.add(fermerPage);

        /* lorsque vous cliquez sur back */
		placeBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AccueilChoix.main(null,name);
				Scraping.this.dispose();
			}
		});
        
        
      
        
		/* lorsque vous lancez scraping */
		lancer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtenez le site sélectionné depuis le JComboBox
                String siteChoisi = (String) siteChooser.getSelectedItem();

                // Exécuter le scraping en fonction du site sélectionné
                switch (siteChoisi) {
                case "MarocEmploi.com":
                    MarocEmploi marocEmploiScraper = new MarocEmploi();
                    // Exécuter la logique de scraping pour "MarocEmploi.com" ici
                    marocEmploiScraper.main(new String[0]);
                    JOptionPane.showMessageDialog(null, "Scraping on MarocEmploi.com is successfully completed");
                    break;

                
                    case "rekrute.ma":
                        recrute rekruteScraper = new recrute();
                        rekruteScraper.main(new String[0]);
                        JOptionPane.showMessageDialog(null, "Scraping on rekrute.ma is successfully completed");
                        break;
                    case "emploi.com":
                        emploi emploiScraper = new emploi();
					try {
						emploiScraper.main(new String[0]);
						JOptionPane.showMessageDialog(null, "Scraping on emploi.com is successfully completed");

						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                         break;
                    case "anapaec.com":
                        anapec anapecScraper = new anapec();
                        anapecScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on anapaec.com is successfully completed");
                        break;
                    case "talenso.com":
                        talenseo talenseoScraper = new talenseo();
                        talenseoScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on talenso.com is successfully completed");
                        break;
                    case "Optioncarrière.com":
                        Optioncarriere  OptioncarriereScraper = new  Optioncarriere();
                        OptioncarriereScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on Optioncarrière.com is successfully completed");
                        break;
                    case "MenaraJob.com":
                        MenaraJob  MenaraJobScraper = new  MenaraJob();
                        MenaraJobScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  MenaraJob.com is successfully completed");
                        break;
                    case "Jobrapido.com":
                    	Jobrapido  JobrapidoScraper = new  Jobrapido();
                    	JobrapidoScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  Jobrapido.com is successfully completed");
                        break;
                    case "job2vente.com":
                    	job2vente  job2venteScraper = new  job2vente();
                    	job2venteScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  job2vente.com is successfully completed");
                        break;
                    case "Bayt.com":
                    	bayt_extract  BaytScraper = new  bayt_extract();
                    	
                    	BaytScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  Bayt.com is successfully completed");
                        break;
                    case "MarocAnnonce.com":
                    	MarocAnnonce  MarocAnnonceScraper = new MarocAnnonce();
                    	
                    	MarocAnnonce.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  MarocAnnonce.com is successfully completed");
                        break;
                    case "Marocemploi.com":
                    	MarocEmploi  MarocEmploiScraper = new MarocEmploi();
                    	
                    	MarocEmploiScraper.main(null);  
                        JOptionPane.showMessageDialog(null, "Scraping on  MarocEmploiScraper.com is successfully completed");
                        break;
                        
                    default:
                        // Site non pris en charge ou erreur de sélection
                        break;
                }
            }
        });
        
        
       
        
    }
}