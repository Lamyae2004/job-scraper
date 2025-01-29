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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenaraJob {
    private static String[] columns = {"id", "sitename", "Postname", "entrepriseName", "DateDePublication",
            "Ville", "Postuler", "posteDescription"};
    private static List<Data_MenaraJob> data_V2 = new ArrayList<>();


        public static String convertDate(String dateText) {
            // Définir le format de sortie JJ/MM/YYYY
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Pattern daysPattern = Pattern.compile("(\\d+) jour(?:s)? avant");
            Matcher daysMatcher = daysPattern.matcher(dateText);
            if (daysMatcher.find()) {
                int daysAgo = Integer.parseInt(daysMatcher.group(1));
                // Calculer la date
                LocalDate date = LocalDate.now().minusDays(daysAgo);
                return date.format(formatter);
            }


            // Matched "X semaine(s) avant"
            Pattern weekPattern = Pattern.compile("(\\d+) semaine(?:s)? avant");
            Matcher weekMatcher = weekPattern.matcher(dateText);
            if (weekMatcher.find()) {
                int weeksAgo = Integer.parseInt(weekMatcher.group(1));
                // Calculer la date
                LocalDate date = LocalDate.now().minusWeeks(weeksAgo);
                return date.format(formatter);
            }

            // Matched "X mois avant"
            Pattern monthPattern = Pattern.compile("(\\d+) mois avant");
            Matcher monthMatcher = monthPattern.matcher(dateText);
            if (monthMatcher.find()) {
                int monthsAgo = Integer.parseInt(monthMatcher.group(1));
                // Calculer la date
                LocalDate date = LocalDate.now().minusMonths(monthsAgo);
                return date.format(formatter);
            }

            return dateText;
        }



    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i < 80; i++) {
                try {
                    Document doc = Jsoup.connect("https://www.m-job.ma/recherche?page="+i).get();
                    Elements newsHeadlines = doc.select("div.offer-box ");

                    for (Element job : newsHeadlines) {
                        try {
                            String postName = job.select("div.left-area div.offer-heading h3 ").text();

                            String entrepriseName = job.select("div.left-area div.cat h5 ").text();

                            String ville = job.select("div.left-area div.offer-body div.location").text();

                            String posteDescription =  job.select("div.left-area div.offer-body p").get(1).text();

                            String rawDate = job.select("div.right-area div.date-buttons span").text();
                            String dateDePublication = convertDate(rawDate);

                            String lien = job.select("div.left-area div.offer-heading h3 a").attr("href");
                            System.out.println(lien);
                            String siteName = "m-job.ma";

                            data_V2.add(new Data_MenaraJob(k, siteName, postName, entrepriseName, dateDePublication, ville,"https://www.m-job.ma" +lien , posteDescription));

                            System.out.println("Job " + k + " successfully collected!");
                            k++;
                        } catch (Exception e) {
                            System.err.println("Erreur lors du traitement d'un job sur la page " + i + ": " + e.getMessage());
                        }
                    }

                    System.out.println("Page " + i + " successfully collected!");
                } catch (Exception e) {
                    System.err.println("Erreur lors du traitement de la page " + i + ": " + e.getMessage());
                }
            }

            saveToExcel();
        } catch (Exception e) {
            System.out.println("Erreur générale de la connexion: " + e.getMessage());
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

            for (Data_MenaraJob data : data_V2) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.Postname);
                row.createCell(3).setCellValue(data.entrepriseName);
                row.createCell(4).setCellValue(data.DateDePublication);
                row.createCell(5).setCellValue(data.Ville);
                row.createCell(6).setCellValue(data.Postuler);
                row.createCell(7).setCellValue(data.posteDescription);
                Loading_MenaraJob dbHandler = new Loading_MenaraJob(data);
                dbHandler.insererDonneesDansBase();

            }
            TraitementData.main(new String[]{});
            workbook.write(fileOut);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


}
