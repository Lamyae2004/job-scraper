package Interface;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplitARFFGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/data"; // URL de la base de données
        String user = "root"; // Utilisateur de la base de données
        String password = ""; // Mot de passe de la base de données
        String trainFile = "traincompF.arff"; // Fichier pour 70% des données
        String testFile = "testcompF.arff"; // Fichier pour 30% des données

        // Liste des compétences
        String[] competencies = {
                "Java", "Python", "C++", "JavaScript", "C#", "Ruby", "Swift", "PHP", "HTML/CSS", "React", "Angular", "Vue.js",
                "Node.js", "Express.js", "RESTful API", "SQL", "MySQL", "PostgreSQL", "Oracle", "NoSQL", "MongoDB", "Cassandra",
                "Conception de bases de données", "Linux", "Unix", "Windows Server", "TCP/IP", "Routage et commutation",
                "Configuration de routeurs et de commutateurs", "Pare-feu", "firewalls", "Cryptographie", "Tests d'intrusion",
                "VMware", "Docker", "Amazon Web Services", "AWS", "Microsoft Azure", "Google Cloud Platform", "GCP",
                "Configuration et maintenance de serveurs", "Gestion des utilisateurs et des permissions", "Jenkins",
                "GitLab CI", "Travis CI", "Android", "Kotlin", "iOS", "Data mining", "Machine learning",
                "TensorFlow", "PyTorch", "Scripting", "Shell", "PowerShell", "Ansible", "Chef", "Puppet", "XML", "JSON",
                "GraphQL", "YAML", "Agile", "Scrum", "Kanban", "UML", "Unified Modeling Language", "Architecture logicielle",
                "Profilage de code", "Optimisation de la performance", "Cybersécurité", "Blockchain", "DevOps", "CI/CD", "Big Data",
                "Data Science", "React Native", "Redux", "Go (Golang)", "Rust", "TypeScript", "Dart", "Scala", "MATLAB",
                "Django", "Flask", "Ruby on Rails", "Laravel", "Spring", ".NET Core (C#)", "Xamarin", "Ember.js", "Svelte",
                "Microsoft SQL Server", "SQLite", "MariaDB", "CouchDB", "Firebase Realtime Database", "Neo4j",
                "Flutter", "PhoneGap / Cordova", "Ionic",
                "Mercurial", "Bazaar", "Extreme Programming (XP)", "Lean Software Development", "Feature Driven Development (FDD)",
                "CircleCI", "Bamboo", "TeamCity", "Kubernetes", "OpenShift", "SaltStack", "etcd", "RabbitMQ", "Apache Kafka",
                "OWASP", "ISO 27001", "KVM", "Hadoop", "Apache HBase", "MQTT", "CoAP", "Selenium", "JUnit", "Jira", "Trello",
                "Hyperledger Fabric", "Ethereum"
        };

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Requête pour récupérer les données nécessaires
            String query = "SELECT secteur, competence FROM offres_competences";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                // Liste pour stocker toutes les lignes de données
                List<String> dataLines = new ArrayList<>();

                // Générer les données à partir des résultats SQL
                while (rs.next()) {
                    String secteur = rs.getString("secteur").replace("é", "e").replace("è", "e").replace("à", "a").replace(" ", "_").toLowerCase();
                    String competence = rs.getString("competence");

                    // Générer les valeurs true/false pour les compétences
                    StringBuilder dataLine = new StringBuilder(secteur);
                    for (String comp : competencies) {
                        dataLine.append(",");
                        dataLine.append(comp.equalsIgnoreCase(competence) ? "true" : "false");
                    }
                    dataLines.add(dataLine.toString());
                }

                // Mélanger aléatoirement les données
                Collections.shuffle(dataLines);

                // Diviser les données en 70% et 30%
                int splitIndex = (int) (dataLines.size() * 0.7);
                List<String> trainData = dataLines.subList(0, splitIndex);
                List<String> testData = dataLines.subList(splitIndex, dataLines.size());

                // Écrire les fichiers ARFF
                writeARFFFile(trainFile, competencies, trainData);
                writeARFFFile(testFile, competencies, testData);

                System.out.println("Fichiers ARFF générés avec succès : " + trainFile + ", " + testFile);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour écrire un fichier ARFF
    private static void writeARFFFile(String fileName, String[] competencies, List<String> dataLines) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Écrire le header du fichier ARFF
            writer.write("% 1. Title: Competence\n");
            writer.write("% (c) Date: " + new java.util.Date() + "\n\n");
            writer.write("@RELATION competence\n\n");

            // Définir les secteurs comme valeurs possibles sans accents et avec underscores
            writer.write("@ATTRIBUTE secteur {informatique,services_generaux,finance,communication,btp,industrie,etudes,juridique,administration,transport,enseignement,hotellerie,agroalimentaire,immobilier,commerce}\n");

            // Ajouter chaque compétence comme attribut
            for (String competency : competencies) {
                writer.write("@ATTRIBUTE \"" + competency.replace("\"", "\\\"") + "\" {false, true}\n");
            }
            writer.write("\n@DATA\n");

            // Ajouter les données
            for (String line : dataLines) {
                writer.write(line + "\n");
            }
        }
    }
}
