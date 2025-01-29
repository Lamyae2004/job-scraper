package Interface;

import java.sql.*;

public class CompetenceAnalyzer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/data"; // URL de la base de données
        String user = "root"; // Utilisateur de la base de données
        String password = ""; // Mot de passe de la base de données

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Supprimer la table si elle existe
            String dropTableQuery = "DROP TABLE IF EXISTS offres_competences";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(dropTableQuery);
                System.out.println("Table 'offres_competences' supprimée si elle existait.");
            }

            // Créer la table 'offres_competences' avec la structure correcte
            String createTableQuery = "CREATE TABLE IF NOT EXISTS offres_competences ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "id_offre INT NOT NULL, "
                    + "competence VARCHAR(255) NOT NULL, "
                    + "secteur VARCHAR(255) NOT NULL, "
                    + "FOREIGN KEY (id_offre) REFERENCES dataset(id) "
                    + ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableQuery);
                System.out.println("Table 'offres_competences' créée avec succès.");
            }

            // Récupérer les compétences et le secteur d'activité depuis la table dataset
            String selectQuery = "SELECT id, hardSkills, SecteurActivite FROM dataset WHERE hardSkills IS NOT NULL AND hardSkills != ''";
            try (PreparedStatement stmt = conn.prepareStatement(selectQuery);
                 ResultSet rs = stmt.executeQuery()) {
                String insertQuery = "INSERT INTO offres_competences (id_offre, competence, secteur) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    while (rs.next()) {
                        int idOffre = rs.getInt("id"); // ID de l'offre
                        String hardSkills = rs.getString("hardSkills"); // Liste des compétences
                        String secteurActivite = rs.getString("SecteurActivite"); // Secteur d'activité

                        // Diviser les compétences en fonction des virgules
                        String[] competencies = hardSkills.split(",\\s*");
                        for (String competence : competencies) {
                            insertStmt.setInt(1, idOffre);          // ID de l'offre
                            insertStmt.setString(2, competence);   // Compétence isolée
                            insertStmt.setString(3, secteurActivite); // Secteur d'activité
                            insertStmt.executeUpdate();           // Insérer dans la table
                        }
                    }
                }
            }
            System.out.println("Insertion des compétences et des secteurs terminée.");

            // Supprimer les enregistrements où la compétence est égale à "Chef

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
