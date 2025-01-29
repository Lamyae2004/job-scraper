
package Interface;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MarocEmploi {

    public static void main(String[] args) {
        List<String> urls = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            urls.add("https://marocemploi.net/offre/?ajax_filter=true&job_page=" + i);
        }

        List<Data_Emploimaroc> jobListings = new ArrayList<>();
        int idCounter = 1;

        try {
            for (String url : urls) {
                Document doc1 = Jsoup.connect(url).get();
                Elements docs = doc1.select(".jobsearch-job.jobsearch-joblisting-classic");

                for (Element doc : docs) {
                    Elements jobTitle = doc.select(".jobsearch-list-option h2 a");
                    Elements recruteurs = doc.select("div.jobsearch-list-option > ul > li.job-company-name");
                    Elements locations = doc.select("ul li i.jobsearch-icon.jobsearch-maps-and-flags");
                    Elements dates = doc.select("ul li i.jobsearch-icon.jobsearch-calendar");
                    Elements secteurs = doc.select("li i.jobsearch-icon.jobsearch-filter-tool-black-shape + a");
                    Elements contrats = doc.select("div.jobsearch-job-userlist > a");
                    Elements liens = doc.select(".jobsearch-list-option h2 a[href]");

                    String emploiUrl = liens.attr("href");
                    Document emploi = Jsoup.connect(emploiUrl).get();

                    Elements datePublication = emploi.select("ul.jobsearch-jobdetail-options li:contains(Date de Parution :)");
                    Elements datePostuler = emploi.select("ul.jobsearch-jobdetail-options li:contains(Postuler Avant :)");
                    Element posteDescription = emploi.selectFirst(".jobsearch-description ul");
                    Element profilDescription = emploi.selectFirst(".jobsearch-description");
                    Elements competences = emploi.select(".jobsearch-description");

                    for (int i = 0; i < jobTitle.size(); i++) {
                        String descriptionPoste = posteDescription != null ? posteDescription.text() : "";
                        String descriptionProfil = profilDescription != null ? profilDescription.text() : "";
                        String entreprise = !recruteurs.isEmpty() && recruteurs.size() > i ? recruteurs.get(i).text() : "";
                        String ville = !locations.isEmpty() && locations.size() > i ? locations.get(i).nextSibling().toString().trim() : "";
                        String secteur = !secteurs.isEmpty() && secteurs.size() > i ? secteurs.get(i).text() : "";
                        String competencesStr = competences != null && competences.select("ul").size() >= 3
                                ? competences.select("ul").get(2).text()
                                : "";
                        String publicationDate = !datePublication.isEmpty()
                                ? datePublication.get(0).text().replace("Date de Parution :", "").trim()
                                : "";
                        String limiteDate = !datePostuler.isEmpty()
                                ? datePostuler.get(0).text().replace("Postuler Avant :", "").trim()
                                : "";
                        String typeContrat = !contrats.isEmpty() && contrats.size() > i ? contrats.get(i).text() : "";

                        Data_Emploimaroc job = new Data_Emploimaroc(
                                idCounter++, "MarocEmploi", jobTitle.get(i).text(), descriptionPoste, descriptionProfil,
                                entreprise, ville, secteur, competencesStr, publicationDate, limiteDate, typeContrat, emploiUrl
                        );

                        jobListings.add(job);
                    }
                }
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Job Listings");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "ID", "Site_Name", "Post_Name", "Post_Description", "Profil_Description",
                    "Entreprise_Name", "Ville", "Secteur", "Hard_Skills",
                    "Date_Publication", "Date_Limite_Postuler", "Type_Contrat", "Lien_Postuler"
            };

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (Data_Emploimaroc job : jobListings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(job.id);
                row.createCell(1).setCellValue(job.sitename);
                row.createCell(2).setCellValue(job.Postname);
                row.createCell(3).setCellValue(job.posteDescription);
                row.createCell(4).setCellValue(job.profileDescription);
                row.createCell(5).setCellValue(job.entrepriseName);
                row.createCell(6).setCellValue(job.ville);
                row.createCell(7).setCellValue(job.secteur);
                row.createCell(8).setCellValue(job.hard_skills);
                row.createCell(9).setCellValue(job.DateDePublication);
                row.createCell(10).setCellValue(job.DateLimitePourPostuler);
                row.createCell(11).setCellValue(job.Type_de_contrat);
                row.createCell(12).setCellValue(job.Postuler);
                Loading_MarocEmploi dbHandler = new Loading_MarocEmploi(job);
                dbHandler.insererDonneesDansBase();
            }
            TraitementData.main(new String[]{});
            try (FileOutputStream fileOut = new FileOutputStream("OffresEmploi.xlsx")) {
                workbook.write(fileOut);
            }
            workbook.close();
            System.out.println("Données exportées avec succès dans le fichier Excel!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
