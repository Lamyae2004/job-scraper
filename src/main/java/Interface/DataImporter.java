package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DataImporter {

    // Connexion à la base de données
    protected Connection connection;
    protected String jdbcURL = "jdbc:mysql://localhost:3306/data";
    protected String username = "root";
    protected String password = "";

    public DataImporter() {
        try {
            this.connection = createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode abstraite pour l'écriture des données
    protected abstract void writeData(ResultSet resultSet);

    // Méthode pour exécuter la requête et obtenir les données
    public void executeImport(String sqlQuery) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            writeData(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour établir la connexion à la base de données
    private Connection createConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    // Méthode pour fermer la connexion (optionnel mais recommandé)
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
