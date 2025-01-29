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

public class MarocAnnonce {

    private static String[] columns = {"id", "sitename", "postname", "entrepriseName", "region",
            "niveauEtude", "posteDescription", "salaire", "DateDePublication", "Postuler"};

    private static List<DataMarocAnnonce> dataList = new ArrayList<>(); 

    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i <= 108; i++) { // Correction du nombre de pages
                try {
                    // Connexion et traitement de la page
                    Document doc = Jsoup.connect("https://www.marocannonces.com/categorie/309/Emploi/Offres-emploi/" + i + ".html").get();
                    Elements newsHeadlines = doc.select(" div .content_box ul li");

                    for (Element job : newsHeadlines) {
                        try {
                            String postname = job.select(".holder h3").text();
                            String niveauEtude = job.select(".niveauetude_label").text();
                            String salaire = job.select(".salary").text();
                            String region = job.select(".location").text();
                            String posteDescription = job.select("p").text();
                            String lien = job.select("a").attr("href");
                            String sitename = "marocannonces.com";
                            String entrepriseName = job.select("div.time2  a").attr("title");
                            if (entrepriseName.isEmpty()) {
                                entrepriseName = "Non spécifié";
                            }

                            String dateDePublication = job.select("div.time em.date").text();

                         // Si le premier sélecteur ne donne pas de résultat, essayez un autre sélecteur
                         if (dateDePublication.isEmpty()) {
                             dateDePublication = job.select("div.time2 em.date").text(); // Ajoutez ici le second sélecteur corrigé
                         }
                         if (dateDePublication.isEmpty()) {
                             dateDePublication = job.select("div.time em.date span.cnt-today").text(); // Ajoutez ici le second sélecteur corrigé
                         }
                         if (dateDePublication.isEmpty()) {
                             dateDePublication = job.select("div.time2 em.date span.cnt-today").text(); // Ajoutez ici le second sélecteur corrigé
                         }
                         System.out.println(dateDePublication);
                            dataList.add(new DataMarocAnnonce(
                                    k,
                                    sitename,
                                    postname,
                                    entrepriseName,
                                    region,
                                    niveauEtude,
                                    posteDescription,
                                    salaire,
                                    dateDePublication,
                                    "https://www.marocannonces.com/" + lien
                            ));

                            System.out.println("Job " + k + " successfully collected!");
                            k++;

                        } catch (Exception e) {
                            System.err.println("Erreur lors du traitement d'un job sur la page " + i + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Page " + i + " successfully collected!");
                } catch (Exception e) {
                    System.err.println("Erreur lors du traitement de la page " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
            saveToExcel();
        } catch (Exception e) {
            System.err.println("Erreur générale: " + e.getMessage());
            e.printStackTrace();
            saveToExcel();
        }
    }

    public static void saveToExcel() {
        try (FileOutputStream fileOut = new FileOutputStream("MarocAnnonce.xlsx")) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("MarocAnnonce");

            // Style pour l'entête
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Création de la ligne d'entête
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Remplissage des données
            int rowNum = 1;
            for (DataMarocAnnonce data : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.postname);
                row.createCell(3).setCellValue(data.entrepriseName);
                row.createCell(4).setCellValue(data.region);
                row.createCell(5).setCellValue(data.niveauEtude);
                row.createCell(6).setCellValue(data.posteDescription);
                row.createCell(7).setCellValue(data.salaire);
                row.createCell(8).setCellValue(data.DateDePublication);
                row.createCell(9).setCellValue(data.Postuler);
                Loading_MarocAnnonce dbHandler = new Loading_MarocAnnonce(data);
                dbHandler.insererDonneesDansBase();
            }
            TraitementData.main(new String[]{});
            workbook.write(fileOut);
            System.out.println("Fichier Excel généré avec succès!");
        } catch (IOException | SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void addData(DataMarocAnnonce data) {
        dataList.add(data);
    }
}