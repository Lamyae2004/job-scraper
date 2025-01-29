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

public class Jobrapido {

    private static String[] columns = {"id", "sitename", "postname", "entrepriseName", "region", "Postuler"};
    private static List<DataJobrapido> dataList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i <= 30; i++) {
                try {
                    Document doc = Jsoup.connect("https://ma.jobrapido.com/Offres-d-emploi-en-Maroc?r=auto&p=" + i + "&shm=all&ae=60").get();
                    System.out.println("Page " + i + " téléchargée avec succès!");
                    Elements newsHeadlines = doc.select(".result-item__wrapper");

                    for (Element job : newsHeadlines) {
                        try {
                        	 String sitename = "jobrapido.com";  
                             String postname = job.select("div.result-item__title").text();  
                             String entrepriseName = job.select("div.result-item__company span").text(); 
                             if (entrepriseName.isEmpty()) {
                            	 entrepriseName = job.select("div.result-item__website span").text();
                             }
                             String region = job.select("div.result-item__location span span").text(); 
                             String lien = job.select("a.result-item__link").attr("href");  

                   
                            dataList.add(new DataJobrapido(k, sitename, postname, entrepriseName, region, lien));
                            k++;

                        } catch (Exception e) {
                            System.err.println("Erreur lors du traitement d'un job sur la page " + i + ": " + e.getMessage());
                        }
                    }

                    System.out.println("Page " + i + " traitée avec succès!");
                } catch (Exception e) {
                    System.err.println("Erreur lors du traitement de la page " + i + ": " + e.getMessage());
                }
            }

      
            System.out.println("Nombre de données collectées : " + dataList.size());
            saveToExcel();
        } catch (Exception e) {
            System.err.println("Erreur inattendue: " + e.getMessage());
        }
    }


    public static void saveToExcel() {
        try (FileOutputStream fileOut = new FileOutputStream("jobRapido.xlsx")) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("JobRapido");

            // Style pour l'entête
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
            for (DataJobrapido data : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.postname);
                row.createCell(3).setCellValue(data.entrepriseName);
                row.createCell(4).setCellValue(data.region);
                row.createCell(5).setCellValue(data.postuler);
                Loading_Jobrapido dbHandler = new Loading_Jobrapido(data);
                dbHandler.insererDonneesDansBase();
            }
            TraitementData.main(new String[]{});
            workbook.write(fileOut);
            System.out.println("Fichier Excel généré avec succès!");
        } catch (IOException | SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier Excel: " + e.getMessage());
        }
    }
}
