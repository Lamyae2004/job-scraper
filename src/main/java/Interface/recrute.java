package Interface;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class recrute {

    private static String[] columns = {"id", "sitename", "Postname", "DateDePublication", "DateLimitePourPostuler",
            "entrepriseName", "EntrepriseDes", "addressEntreprise", "region", "Type_de_contrat", "secteur",
            "Niveau_etude", "experience", "langue", "nombrePostes", "posteDescription", "profilRecherche",
            "Télétravail", "Postuler", "TraitePersonaliter"};
    private static List<Data_rekrute> data_rekrute = new ArrayList<>();
    static List<String> hardSkillsList = Arrays.asList(
            "Java", "Python", "C++", "JavaScript", "C#", "Ruby", "Swift", "PHP", "HTML/CSS", "React", "Angular", "Vue.js",
            "Node.js", "Express.js", "RESTful API", "SQL", "MySQL", "PostgreSQL", "Oracle", "NoSQL", "MongoDB", "Cassandra",
            "Conception de bases de données", "Linux", "Unix", "Windows Server", "TCP/IP", "Routage et commutation",
            "Configuration de routeurs et de commutateurs", "Pare-feu", "firewalls", "Cryptographie", "Tests d'intrusion",
            "VMware", "Docker", "Amazon Web Services", "AWS", "Microsoft Azure", "Google Cloud Platform", "GCP",
            "Configuration et maintenance de serveurs", "Gestion des utilisateurs et des permissions", "Jenkins",
            "GitLab CI", "Travis CI", "Android", "Java", "Kotlin", "iOS", "Swift", "Data mining", "Machine learning",
            "TensorFlow", "PyTorch", "Scripting", "Shell", "PowerShell", "Ansible", "Chef", "Puppet", "XML", "JSON",
            "GraphQL", "YAML", "Agile", "Scrum", "Kanban", "UML", "Unified Modeling Language", "Architecture logicielle",
            "Profilage de code", "Optimisation de la performance","Cybersécurité", "Blockchain", "DevOps", "CI/CD", "Big Data", "Data Science", "React Native", "Vue.js", "Redux",
            "Go (Golang)", "Rust", "TypeScript", "Dart", "Scala", "Kotlin", "MATLAB",
            "Django ", "Flask ", "Ruby on Rails", "Laravel ", "Spring ", ".NET Core (C#)", "Xamarin", "Ember.js", "Svelte",
            "Microsoft SQL Server", "SQLite", "MariaDB", "CouchDB", "Firebase Realtime Database", "Neo4j (graph database)",
            "Xamarin", "Flutter", "PhoneGap / Cordova", "Ionic",
            "Mercurial", "Bazaar", "Extreme Programming (XP)", "Lean Software Development", "Feature Driven Development (FDD)", "CircleCI", "Bamboo", "TeamCity", "Kubernetes", "OpenShift", "SaltStack", "etcd", "RabbitMQ", "Apache Kafka", "GraphQL", "Falcor", "OWASP ", "ISO 27001 (norme de sécurité de l'information)", "KVM ", "Hadoop", "Apache HBase", "MQTT ", "CoAP ", "Selenium", "JUnit", "Jira", "Trello", "Hyperledger Fabric", "Ethereum"
    );

    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 1; i < 127; i++) {
                try {
                    // Tentative de connexion et traitement de la page
                    Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?s=1&p=" + i + "&o=1").get();
                    Elements newsHeadlines = doc.select(".post-id");

                    for (Element job : newsHeadlines) {
                        try {
                            Elements Postname = job.select(".titreJob");
                            Elements DateDePublication = job.select(".date span");
                            Elements lien = job.select(".section h2 a");
                            Document emploi = Jsoup.connect("https://www.rekrute.com" + lien.attr("href")).get();
                            Elements Type_de_contrat = emploi.select("span[title=Type de contrat]");
                            Elements Télétravail = emploi.select("span[title=Télétravail]");
                            String teletravailText = Télétravail.text().substring(Télétravail.text().indexOf(":") + 1).trim();

                            Elements Niveau_etude = emploi.select("li[title=\"Niveau d'étude et formation\"]");
                            Elements experience = emploi.select("li[title=Expérience requise]");
                            Elements region = emploi.select("li[title=Région]");

                            String regionText = region.text().replaceAll("\\d+ poste\\(s\\) sur", "").trim();
                            Elements elements = emploi.select("span.tagSkills");
                            List<String> texts = elements.eachText();
                            Set<String> uniqueTexts = new HashSet<>(texts); // Supprime les doublons
                            String traitePersonaliter = String.join(", ", uniqueTexts); // Combiner les textes uniques
                             
                         // Extraire la ville en prenant la partie avant le premier tiret
                            String city = regionText.split(" -")[0].trim();

                            
                            Elements secteur = emploi.select(".h2italic");
                            String secteurText = secteur.text().substring(secteur.text().indexOf(":") + 1).trim();

                            // Nettoyage du texte pour obtenir uniquement le secteur après le dernier tiret
                            String secteurSimplified = secteurText.split(" -")[1].trim();  // Prendre la deuxième partie après le dernier tiret

                            // Si le secteur contient "Secteur", on le retire pour ne garder que le nom
                            if (secteurSimplified.toLowerCase().startsWith("secteur")) {
                                secteurSimplified = secteurSimplified.substring("secteur".length()).trim();
                            }


                            Elements dateElements = emploi.select("div.col-md-12.col-sm-12.col-xs-12 span.newjob");
                            String dateDeadline = dateElements.select("b").text(); // Date limite

                            Element entrepriseNameElement = emploi.select("h2:contains(Entreprise) + p strong").first();
                            String entrepriseName = entrepriseNameElement != null ? entrepriseNameElement.text() : "";

                            Elements EntrepriseDes = emploi.select("#recruiterDescription p");
                            Element addressEntreprise = emploi.selectFirst("span#address");
                            Element posteDescriptionElement = emploi.selectFirst("div.col-md-12.blc h2:contains(Poste) + p");
                            String posteDescriptionText = (posteDescriptionElement != null) ? posteDescriptionElement.text() : "";
                            Element profilRecherche = emploi.selectFirst("div.col-md-12.blc h2:contains(Profil recherché) + ul");

                            String langue = "français anglais";
                            String sitename = "rekrute.com";
                            String nombrePostes = region.select("b").text();
                            Document emploi1 = Jsoup.connect("https://www.rekrute.com" + lien.attr("href")).get();
                            Element profilRechercheElement = emploi1.selectFirst("div.col-md-12.blc h2:contains(Profil recherché) + ul, div.col-md-12.blc h2:contains(Profil recherché) + p");
                            String profilRechercheText = "";

                            if (profilRechercheElement != null) {
                                if ("ul".equalsIgnoreCase(profilRechercheElement.tagName())) {
                                    Elements items = profilRechercheElement.select("li");
                                    for (Element item : items) {
                                        profilRechercheText += item.text() + "\n";
                                    }
                                } else if ("p".equalsIgnoreCase(profilRechercheElement.tagName())) {
                                    profilRechercheText = profilRechercheElement.text();
                                }
                            }
                            //hard skils
                            // Recherche des hard skills dans le texte de la page de l'offre d'emploi
                            String pageText = emploi.text().toLowerCase(); // Convertir tout le texte en minuscules pour la comparaison
                            List<String> foundHardSkills = new ArrayList<>();
                            for (String skill : hardSkillsList) {
                                if (pageText.contains(skill.toLowerCase())) {
                                    foundHardSkills.add(skill);
                                }
                            }

                            data_rekrute.add(new Data_rekrute(k, sitename, Postname.text(), DateDePublication.first().text(), dateDeadline,
                                    entrepriseName, EntrepriseDes.text(), (addressEntreprise != null) ? addressEntreprise.text() : "",
                                    regionText, Type_de_contrat.text(), secteurSimplified, Niveau_etude.text(),
                                    experience.text(), langue, nombrePostes, posteDescriptionText,
                                    (profilRecherche != null) ? profilRecherche.text() : "", teletravailText,
                                    "https://www.rekrute.com" + lien.attr("href"), traitePersonaliter, String.join(", ", foundHardSkills)));
                            
                            System.out.println("job " + k + " successfully collected !!!!!!!!!!");
                            k++;
                        } catch (Exception e) {
                            // Log erreur pour un job spécifique
                            System.err.println("Erreur lors du traitement d'un job sur la page " + i + ": " + e.getMessage());
                        }
                    }
                    System.out.println("page " + i + " successfully collected!!!!!");
                } catch (Exception e) {
                    // Log erreur pour une page entière
                    System.err.println("Erreur lors du traitement de la page " + i + ": " + e.getMessage());
                }
            }
            saveToExcel();
        } catch (Exception e) {
            System.out.println("Erreur générale de la connexion");
            saveToExcel();
        }
    }

    public static void saveToExcel() {
        try (FileOutputStream fileOut = new FileOutputStream("S1_rekrute.xlsx")) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("rekrute");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());
   
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;

            for (Data_rekrute data : data_rekrute) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.Postname);
                row.createCell(3).setCellValue(data.DateDePublication);
                row.createCell(4).setCellValue(data.DateLimitePourPostuler);
                row.createCell(5).setCellValue(data.entrepriseName);
                row.createCell(6).setCellValue(data.EntrepriseDes);
                row.createCell(7).setCellValue(data.addressEntreprise);
                row.createCell(8).setCellValue(data.region);
                row.createCell(9).setCellValue(data.Type_de_contrat);
                row.createCell(10).setCellValue(data.secteur);
                row.createCell(11).setCellValue(data.Niveau_etude);
                row.createCell(12).setCellValue(data.experience);
                row.createCell(13).setCellValue(data.langue);
                row.createCell(14).setCellValue(data.nombrePostes);
                row.createCell(15).setCellValue(data.posteDescription);
                row.createCell(16).setCellValue(data.profilRecherche);
                row.createCell(17).setCellValue(data.Télétravail); // Updated column index to include Teletravail
                row.createCell(18).setCellValue(data.Postuler);
                row.createCell(19).setCellValue(data.TraitePersonaliter);
                loading_rekrute dbHandler = new loading_rekrute(data);
                dbHandler.insererDonneesDansBase();
            }

            TraitementData.main(new String[]{});
            Pretraitement.main(new String[]{});
            Preprocessing.main(new String[]{});
            GenerateurARFF.main(new String[]{});
            CompetenceAnalyzer.main(new String[]{});
            SplitARFFGenerator.main(new String[]{});

            workbook.write(fileOut);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

  
}