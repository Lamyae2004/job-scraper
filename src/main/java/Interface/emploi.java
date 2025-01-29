package Interface;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class emploi {
    private static String[] columns = {"id", "sitename", "Postname", "description", "DateDePublication",
            "Entreprise", "region", "Competence",
            "Type_de_contrat", "experience",
            "niveau_etude","Postuler"};
    private static List<data_emploi> data_emploi = new ArrayList<>();

    public static void main(String[] args) {
        int pageCounter = 1;
        int jobCounter = 1;

        for (int i = 1; i < 28; i++) { // Scrape pages 2 to 19
            try {
                // Fetch the page
                Document doc = Jsoup.connect("https://www.emploi.ma/recherche-jobs-maroc?page=" + i).get();

                // Select job elements
                Elements newsHeadlines = doc.select("div.card-job-detail");

                for (Element job : newsHeadlines) {
                    try {
                        // Extract job details
                        String postName = job.select("h3").text();
                        System.out.println(postName);

                        String dateDePublication = job.select("time").text();
                        System.out.println(dateDePublication);

                        String entreprise = job.select("a.card-job-company.company-name").text();
                        System.out.println(entreprise);

                        String description = job.select("div.card-job-description p").text();
                        System.out.println(description);

                        String regionText = job.select("ul li:contains(Région de :) strong").text();
                        System.out.println(regionText);

                        String competence = job.select("ul li:contains(Compétences clés :) strong").text();
                        System.out.println(competence);

                        String typeDeContrat = job.select("ul li:contains(Contrat proposé :) strong").text();
                        System.out.println(typeDeContrat);

                        String experience = job.select("ul li:contains(Niveau d\\'expérience :) strong").text();
                        System.out.println(experience);

                        String niveauEtude = job.select("ul li:contains(Niveau d´études requis :) strong").text();
                        System.out.println(niveauEtude);

                        String lien = job.select("h3 a").attr("href");
                        String fullLien = "https://www.emploi.ma" + lien;
                        System.out.println(fullLien);

                        // Add data to the list
                        String sitename = "Emploi";

                        data_emploi.add(new data_emploi(
                                sitename, postName, description,
                                dateDePublication.split(" ")[0], entreprise,
                                regionText, competence, typeDeContrat,
                                experience, niveauEtude, fullLien
                        ));

                        System.out.println("Emploi " + jobCounter + " successfully collected!");
                        jobCounter++;
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la collecte d'une offre : " + e.getMessage());
                    }
                }

                pageCounter++;
                System.out.println("Page " + i + " successfully collected!");
            } catch (Exception e) {
                System.out.println("Erreur de la connexion à la page " + i);
                try {
                    SAVE();
                } catch (IOException | SQLException ioException) {
                    System.out.println("Erreur lors de la sauvegarde : " + ioException.getMessage());
                }
            }
        }

        try {
            SAVE();
        } catch (IOException | SQLException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }


    public static void SAVE() throws IOException, SQLException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("S2_emploi");

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

        for (data_emploi data : data_emploi) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(1).setCellValue(data.sitename);
            row.createCell(2).setCellValue(data.Postname);
            row.createCell(3).setCellValue(data.description);
            row.createCell(4).setCellValue(data.DateDePublication);
            row.createCell(5).setCellValue(data.Entreprise);
            row.createCell(6).setCellValue(data.region);
            row.createCell(7).setCellValue(data.Competence);
            row.createCell(8).setCellValue(data.Type_de_contrat);
            row.createCell(9).setCellValue(data.experience);
            row.createCell(10).setCellValue(data.niveau_etude);
            row.createCell(11).setCellValue(data.Postuler);
            loading_emploi dbHandler = new loading_emploi(data);
            dbHandler.insererDonneesDansBase();
        }
        TraitementData.main(new String[]{});

        FileOutputStream fileOut = new FileOutputStream("S2_Emploi.xlsx");
        workbook.write(fileOut);
        fileOut.close();

    }




}

