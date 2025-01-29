package Interface;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySQLToARFF {
    public static void main(String[] args) {
        // Paramètres de la base de données
        String url = "jdbc:mysql://localhost:3306/data";
        String user = "root";
        String password = ""; // Remplacez par votre mot de passe si nécessaire

        // Nom des fichiers ARFF
        String arffTrainFile = "comptrain.arff";
        String arffTestFile = "comptest.arff";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connexion à la base de données réussie.");

            // Requête SQL pour récupérer les données
            String query = "SELECT competence, id_offre FROM offres_competences"; // Table contenant les données
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                // Liste pour stocker les données
                List<String[]> data = new ArrayList<>();

                // Récupérer toutes les données et les ajouter à la liste
                while (resultSet.next()) {
                    String competence = resultSet.getString("competence").replace(",", " "); // Nettoyage des virgules
                    int idOffre = resultSet.getInt("id_offre");
                    data.add(new String[]{competence, String.valueOf(idOffre)});
                }

                // Mélanger les données pour garantir une répartition aléatoire
                Collections.shuffle(data);

                // Calcul de la taille de l'ensemble d'entraînement (70%)
                int trainSize = (int) (data.size() * 0.7);
                List<String[]> trainData = data.subList(0, trainSize);  // Données d'entraînement (70%)
                List<String[]> testData = data.subList(trainSize, data.size());  // Données de test (30%)

                // Créer les fichiers ARFF
                FileWriter writerTrain = new FileWriter(arffTrainFile);
                FileWriter writerTest = new FileWriter(arffTestFile);

                // Écriture de l'entête ARFF
                writerTrain.write("@relation competences\n\n");
                writerTrain.write("@attribute competence string\n");
                writerTrain.write("@attribute id_offre numeric\n");
                writerTrain.write("\n@data\n");

                writerTest.write("@relation competences\n\n");
                writerTest.write("@attribute competence string\n");
                writerTest.write("@attribute id_offre numeric\n");
                writerTest.write("\n@data\n");

                // Écrire les données d'entraînement dans le fichier ARFF
                for (String[] record : trainData) {
                    writerTrain.write("\"" + record[0] + "\"," + record[1] + "\n");
                }

                // Écrire les données de test dans le fichier ARFF
                for (String[] record : testData) {
                    writerTest.write("\"" + record[0] + "\"," + record[1] + "\n");
                }

                // Fermer les fichiers
                writerTrain.close();
                writerTest.close();

                System.out.println("Fichiers ARFF générés avec succès : " + arffTrainFile + " et " + arffTestFile);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou de récupération des données.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur lors de la création des fichiers ARFF.");
            e.printStackTrace();
        }
    }
}
