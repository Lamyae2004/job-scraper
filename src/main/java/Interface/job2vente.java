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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class job2vente {

    private static String[] columns = {"id", "sitename", "Postname", "DateDePublication","ville", "Type_de_contrat", "secteur",
            "posteDescription",
            "Postuler"};
    private static List<Data_job2vente> data_job2vente = new ArrayList<>();

    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 1; i < 19; i++) {
                try {
                    // Tentative de connexion et traitement de la page
                    Document doc = Jsoup.connect("https://www.job2vente.ma/offres/index?page="+i).get();
                    Elements newsHeadlines = doc.select("article.post.post-medium");

                    for (Element job : newsHeadlines) {
                        try {
                            String Postname = job.select("div.post-content h3").text();
                           
                            String posteDescription = job.select("div.post-content p").text();
                   
                            String DateDePublication = job.select("div.post-meta span").get(0).text();
                      
                            String secteur = job.select("div.post-meta span").get(1).text();
                           
                            String ville = job.select("div.post-meta span").get(2).text();
                         
                            String Type_de_contrat = job.select("div.post-meta span").get(3).text();
                  
                            String lien = job.select("div.post-image.img-thumbnail a").attr("href");
                         
                            String sitename = "job2vente.com";
                            data_job2vente.add(new Data_job2vente(k, sitename, Postname, DateDePublication,
                                    ville, Type_de_contrat, secteur, posteDescription,
                           "https://www.job2vente.ma/" + lien));
                            
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
        try (FileOutputStream fileOut = new FileOutputStream("S1_job2vente.xlsx")) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("job2vente");

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

            for (Data_job2vente data : data_job2vente) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.Postname);
                row.createCell(3).setCellValue(data.DateDePublication);
                row.createCell(4).setCellValue(data.ville);
                row.createCell(5).setCellValue(data.Type_de_contrat);
                row.createCell(6).setCellValue(data.secteur);
                row.createCell(7).setCellValue(data.posteDescription);
                row.createCell(8).setCellValue(data.Postuler);
                Loading_job2vente dbHandler = new Loading_job2vente(data);
                dbHandler.insererDonneesDansBase();
            }
            TraitementData.main(new String[]{});
            workbook.write(fileOut);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

  
   
}
