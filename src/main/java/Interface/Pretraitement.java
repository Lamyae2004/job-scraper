package Interface;
import java.sql.*;

public class Pretraitement {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/data"; // Remplace 'database' par le nom de ta base
        String user = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Connexion à la base de données
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Vérifier si la table "data_pretraite" existe
            resultSet = connection.getMetaData().getTables(null, null, "data_pretraite", null);
            boolean tableExists = resultSet.next();

            if (!tableExists) {
                // Créer une copie de la structure de la table d'origine
                statement.executeUpdate("CREATE TABLE data_pretraite LIKE dataset");
                System.out.println("Table data_pretraite créée.");
            }

            // Sélectionner toutes les données de la table source
            resultSet = statement.executeQuery("SELECT * FROM dataset");

            // Nettoyage des données : Convertir NULL en 'N/A' et compter les valeurs N/A
            PreparedStatement insertStmt = connection.prepareStatement(
            		 "INSERT INTO data_pretraite (`id`,`Sitename`, `Postename`, `PostDescription`, `ProfilDescription`, `EntrepriseName`, `EntrepriseDescription`, `EntreprisAdress`, `region`, `Ville`, `SecteurActivite`, `Metier`, `Experience`, `NiveauEtude`, `Langue`, `hardSkills`, `SoftSkills`, `DatePublication`, `DateLimitePostuler`, `NombrePoste`, `TypeContrat`, `Teletravail`, `LienPostuler`, `SalaireMensuel`, `Reference`, `EntrepriseSite`, `Spécialité`, `ProfilRecherché`, `TraitsPersonnalite`, `CompétenceRecommandées`, `NiveauLangue`, `AvantagesSociaux`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            while (resultSet.next()) {
                int naCount = 0;
                Object[] rowData = new Object[32]; // 31 colonnes dans ta table

                // Traiter chaque colonne et remplir rowData avec les bonnes valeurs
                for (int i = 1; i <= 32; i++) {
                    String value = resultSet.getString(i);
                    if (value == null || value.trim().isEmpty()) {
                        value = "N/A"; // Remplacer null ou vide par 'N/A'
                        naCount++;
                    }
                    rowData[i - 1] = value; // Remplir le tableau avec les données
                }

                // Insérer la ligne si elle contient <= 7 valeurs 'N/A'
                if (naCount <= 5) {
                    // Assurer l'insertion correcte dans l'ordre des colonnes
                    for (int i = 0; i < 32; i++) {
                        insertStmt.setString(i + 1, (String) rowData[i]);
                    }
                    insertStmt.executeUpdate();
                }
            }

            System.out.println("Prétraitement terminé. Les données ont été insérées dans data_pretraite.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
