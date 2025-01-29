package Interface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateurARFF {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/data"; // Base de données
        String user = "root"; // Nom d'utilisateur
        String password = ""; // Mot de passe

        // Fichiers ARFF de sortie
        String trainFile = "data_trainff.arff";
        String testFile = "data_testff.arff";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connexion réussie à la base de données.");

            // Étape 1 : Récupérer les données depuis la base
            String query = "SELECT SecteurActivite, TypeContrat, Teletravail, ville FROM data_pretraite";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<String> dataLines = new ArrayList<>();

            // Liste des secteurs
            List<String> secteurs = List.of(
            	    "industrie", "etudes", "transport", "agroalimentaire", "hotellerie", "informatique",
            	    "commerce", "administration", "btp", "finance", "communication", "agriculture",
            	    "sante", "juridique", "immobilier", "agences", "enseignement", "services_generaux"
            	);


            // Liste des types de contrats
            List<String> typesContrats = List.of("CDI", "CDD", "Interim", "Freelance", "Autre", "Stage");

            // Liste des options de télétravail
            List<String> teletravailOptions = List.of("oui", "non", "hybride");

            // Liste des villes
            List<String> villes = List.of(
                "casablanca", "meknès", "marrakech", "al_hoceima", "agadir", "rabat",
                "kénitra", "béni_mellal", "tanger", "oujda", "maroc", "tan_tan",
                "fès", "settat", "laâyoune", "inconnu", "indifférente"
            );

         // Étape 2 : Préparer les données avec classification
            while (resultSet.next()) {
                String secteur = resultSet.getString("SecteurActivite").trim().toLowerCase().replace(" ", "_");
                String contrat = resultSet.getString("TypeContrat").trim();
                String teletravail = resultSet.getString("Teletravail").trim().toLowerCase();
                String ville = resultSet.getString("ville").trim().toLowerCase().replace(" ", "_");

                // Valider les valeurs contre les listes prédéfinies
                if (!secteurs.contains(secteur) || !typesContrats.contains(contrat) ||
                    !teletravailOptions.contains(teletravail) || !villes.contains(ville)) {
                    continue; // Ignorer les lignes avec des valeurs non reconnues
                }

                // Classification par secteur
             // Exemple de classification plus détaillée
                String classification;
                if (secteur.equals("informatique")) {
                    if (teletravail.equals("oui") || teletravail.equals("hybride")) {
                        classification = "Attrayante";
                    } else {
                        classification = "Domaine_Attractif_mais_Sans_Teletravail";
                    }
                } else if (secteur.equals("finance")) {
                    if (contrat.equals("CDI")) {
                        classification = "Attrayante";
                    } else {
                        classification = "Domaine_Attractif_mais_Contrat_Temporaire";
                    }
                } else {
                    classification = "NonAttrayante";
                }

                // Ajouter la ligne formatée avec la classification intermédiaire
                dataLines.add(secteur + "," + contrat + "," + teletravail + "," + ville + "," + classification);


                // Ajouter la ligne formatée avec la classification intermédiaire
            }


            // Mélanger les données aléatoirement
            Collections.shuffle(dataLines);

            // Diviser les données en 70% entraînement et 30% test
            int trainSize = (int) (dataLines.size() * 0.7);
            List<String> trainData = dataLines.subList(0, trainSize);
            List<String> testData = dataLines.subList(trainSize, dataLines.size());

            // Générer les fichiers ARFF
            generateARFFFile(trainFile, trainData, secteurs, typesContrats, teletravailOptions, villes, "data_train2");
            generateARFFFile(testFile, testData, secteurs, typesContrats, teletravailOptions, villes, "data_test2");

            System.out.println("Fichiers ARFF générés avec succès : " + trainFile + " et " + testFile);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion ou de la récupération des données : " + e.getMessage());
        }
    }

    private static void generateARFFFile(String fileName, List<String> data, List<String> secteurs, List<String> typesContrats,
                                         List<String> teletravailOptions, List<String> villes, String relationName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        	// En-tête ARFF
        	writer.write("@relation " + relationName + "\n\n");

        	// Déclaration des attributs
        	writer.write("@attribute SecteurActivite {" + String.join(",", secteurs) + "}\n");
        	writer.write("@attribute TypeContrat {" + String.join(",", typesContrats) + "}\n");
        	writer.write("@attribute Teletravail {" + String.join(",", teletravailOptions) + "}\n");
        	writer.write("@attribute ville {" + String.join(",", villes) + "}\n");

        	// Déclaration de la classification avec toutes les valeurs possibles
        	writer.write("@attribute Classification {Attrayante,NonAttrayante,Domaine_Attractif_mais_Sans_Teletravail,Domaine_Attractif_mais_Contrat_Temporaire}\n\n");

        	// Début des données
        	writer.write("@data\n");


            // Écrire les données
            for (String line : data) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier ARFF : " + e.getMessage());
        }
    }
}
