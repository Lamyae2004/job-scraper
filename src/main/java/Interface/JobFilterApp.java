package Interface;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobFilterApp extends JFrame {
    private JComboBox<String> siteNameBox, secteurBox, regionBox, contratBox;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    static String namee;

    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/data";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Image imageBack = new ImageIcon(Login.class.getResource("/images/logo1.png"))
            .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

    public JobFilterApp() {
        setTitle("Job Filter App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#ABDFE1"));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 3));
        setContentPane(mainPanel);

        // Back button
        JLabel backButton = new JLabel(new ImageIcon(imageBack));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JobFilterApp.this.dispose();
                AccueilChoix.main(null, namee);
            }
        });
        mainPanel.add(backButton, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        siteNameBox = createStyledComboBox(getUniqueValues("Sitename"));
        secteurBox = createStyledComboBox(getUniqueValues("SecteurActivite"));
        regionBox = createStyledComboBox(getUniqueValues("Region"));
        contratBox = createStyledComboBox(getUniqueValues("TypeContrat"));

        // Add default "All" option to filters
        siteNameBox.insertItemAt("All", 0);
        secteurBox.insertItemAt("All", 0);
        regionBox.insertItemAt("All", 0);
        contratBox.insertItemAt("All", 0);

        siteNameBox.setSelectedIndex(0);
        secteurBox.setSelectedIndex(0);
        regionBox.setSelectedIndex(0);
        contratBox.setSelectedIndex(0);

        /*filterPanel.add(new JLabel("Site Name:"));
        filterPanel.add(siteNameBox);
        filterPanel.add(new JLabel("Secteur:"));
        filterPanel.add(secteurBox);
        filterPanel.add(new JLabel("Region:"));
        filterPanel.add(regionBox);
        filterPanel.add(new JLabel("Contrat:"));
        filterPanel.add(contratBox);*/

        // Modification du code pour appliquer la couleur et la taille de police
        JLabel siteNameLabel = new JLabel("Site Name:");
        siteNameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Police Arial, taille 14, gras
        siteNameLabel.setForeground(Color.decode("#1466b8")); // Couleur bleue
        filterPanel.add(siteNameLabel);
        filterPanel.add(siteNameBox);


        JLabel secteurLabel = new JLabel("Secteur:");
        secteurLabel.setFont(new Font("Arial", Font.BOLD, 14));
        secteurLabel.setForeground(Color.decode("#1466b8"));
        filterPanel.add(secteurLabel);
        filterPanel.add(secteurBox);
        JLabel regionLabel = new JLabel("Region:");
        regionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        regionLabel.setForeground(Color.decode("#1466b8"));
        filterPanel.add(regionLabel);
        filterPanel.add(regionBox);
        JLabel contratLabel = new JLabel("Contrat:");
        contratLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contratLabel.setForeground(Color.decode("#1466b8"));
        filterPanel.add(contratLabel);
        filterPanel.add(contratBox);









        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Table for results
        tableModel = new DefaultTableModel(new String[]{
                "Sitename", "Postename", "PostDescription", "ProfilDescription", "EntrepriseName",
                "EntrepriseDescription", "EntrepriseAdress", "Region", "Ville", "SecteurActivite", "Metier",
                "Experience", "NiveauEtude", "Langue", "HardSkills", "SoftSkills", "DatePublication",
                "DateLimitePostuler", "NombrePoste", "TypeContrat", "Teletravail", "LienPostuler",
                "SalaireMensuel", "Reference", "EntrepriseSite", "Spécialité", "ProfilRecherché",
                "TraitsPersonnalite", "CompétenceRecommandées", "NiveauLangue", "AvantagesSociaux"
        }, 0);

        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add listeners to combo boxes
        siteNameBox.addActionListener(e -> updateTable());
        secteurBox.addActionListener(e -> updateTable());
        regionBox.addActionListener(e -> updateTable());
        contratBox.addActionListener(e -> updateTable());

        // Populate initial table
        updateTable();
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.BOLD, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return comboBox;
    }

    private String[] getUniqueValues(String column) {
        List<String> values = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT " + column + " FROM dataset")) {
            while (rs.next()) {
                values.add(rs.getString(column));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching values for column: " + column + "\n" + e.getMessage());
            e.printStackTrace();
        }
        return values.toArray(new String[0]);
    }

    private void updateTable() {
        String selectedSite = siteNameBox.getSelectedItem().toString();
        String selectedSecteur = secteurBox.getSelectedItem().toString();
        String selectedRegion = regionBox.getSelectedItem().toString();
        String selectedContrat = contratBox.getSelectedItem().toString();

        tableModel.setRowCount(0); // Clear table

        String query = "SELECT * FROM dataset WHERE " +
                "(? = 'All' OR Sitename = ?) AND " +
                "(? = 'All' OR SecteurActivite = ?) AND " +
                "(? = 'All' OR Region = ?) AND " +
                "(? = 'All' OR TypeContrat = ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, selectedSite);
            stmt.setString(2, selectedSite);
            stmt.setString(3, selectedSecteur);
            stmt.setString(4, selectedSecteur);
            stmt.setString(5, selectedRegion);
            stmt.setString(6, selectedRegion);
            stmt.setString(7, selectedContrat);
            stmt.setString(8, selectedContrat);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("Sitename"), rs.getString("Postename"), rs.getString("PostDescription"),
                        rs.getString("ProfilDescription"), rs.getString("EntrepriseName"),
                        rs.getString("EntrepriseDescription"), rs.getString("EntreprisAdress"),
                        rs.getString("Region"), rs.getString("Ville"), rs.getString("SecteurActivite"),
                        rs.getString("Metier"), rs.getString("Experience"), rs.getString("NiveauEtude"),
                        rs.getString("Langue"), rs.getString("HardSkills"), rs.getString("SoftSkills"),
                        rs.getString("DatePublication"), rs.getString("DateLimitePostuler"),
                        rs.getString("NombrePoste"), rs.getString("TypeContrat"), rs.getString("Teletravail"),
                        rs.getString("LienPostuler"), rs.getString("SalaireMensuel"), rs.getString("Reference"),
                        rs.getString("EntrepriseSite"), rs.getString("Spécialité"), rs.getString("ProfilRecherché"),
                        rs.getString("TraitsPersonnalite"), rs.getString("CompétenceRecommandées"),
                        rs.getString("NiveauLangue"), rs.getString("AvantagesSociaux")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JobFilterApp().setVisible(true));
    }
}