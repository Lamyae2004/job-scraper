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

public class talenseo {

    private static String[] columns = {"id","sitename","publié","ville","métier","contrat"};
    private static List<DataTalensio> dataList = new ArrayList<>();


    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i <= 3; i++) {
                try {
                    Document doc = Jsoup.connect("https://talenseo.com/offres-demploi-maroc?page/" + i + "/").get();
                    System.out.println("Page " + i + " téléchargée avec succès!");

                    Elements newsHeadlines = doc.select(".job-content-wrap");

                    for (Element job : newsHeadlines) {
                        try {
                            String sitename = "talenseo.com";
                            String publié=job.select(".time-ago").text();
                            System.out.println(publié);
                            String ville=job.select(".address a span ").text();
                            System.out.println(ville);
                            String métier = job.select(".job-info.yes-logo h3.job-title a").text();
                            System.out.println(métier);
                            String contrat = job.select(".categories.iwj-job-page span").text();
                            System.out.println(contrat);




                            dataList.add(new DataTalensio(k, sitename, publié, ville, métier,contrat));
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
        try (FileOutputStream fileOut = new FileOutputStream("talenseo.xlsx")) {
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
            for (DataTalensio data : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.publié);
                row.createCell(3).setCellValue(data.ville);
                row.createCell(4).setCellValue(data.métier);
                row.createCell(5).setCellValue(data.contrat);
                loading_talensio dbHandler = new loading_talensio(data);
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