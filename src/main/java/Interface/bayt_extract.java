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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class bayt_extract {
    private static String[] columns = {"id", "sitename", "Postname", "entrepriseName", "DateDePublication",
            "Ville", "Postuler", "posteDescription"};
    private static List<data_bayt> data_V2 = new ArrayList<>();

    public static String convertDate(String dateText) {

        String cleanDateText = dateText.split("/")[0].trim(); // Get only the part before "/"


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Pattern daysPattern = Pattern.compile("(Il y a|Depuis) (\\d+) jour(?:s)?");
        Matcher daysMatcher = daysPattern.matcher(cleanDateText);

        if (daysMatcher.find()) {

            System.out.println("Matched: " + daysMatcher.group(1) + " " + daysMatcher.group(2) + " days");

            int daysAgo = Integer.parseInt(daysMatcher.group(2));

            LocalDate date = LocalDate.now().minusDays(daysAgo);
            return date.format(formatter);
        }

        if (dateText.contains("30+ jours")) {
            LocalDate date = LocalDate.now().minusDays(30);
            return date.format(formatter);
        }


        Pattern hoursPattern = Pattern.compile("Il y a|Depuis (\\d+) heures");
        Matcher hoursMatcher = hoursPattern.matcher(dateText);
        if (hoursMatcher.find()) {
            int hoursAgo = Integer.parseInt(hoursMatcher.group(1));
            LocalDateTime dateTime = LocalDateTime.now().minusHours(hoursAgo);
            return dateTime.toLocalDate().format(formatter);
        }


        if (dateText.equalsIgnoreCase("Hier")) {
            LocalDate date = LocalDate.now().minusDays(1);
            return date.format(formatter);
        }


        return dateText;
    }


    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i < 80; i++) {
                try {
                    Document doc = Jsoup.connect("https://www.bayt.com/fr/morocco/jobs/?page=" + i + "&_gl=1*1blkjjf*_up*MQ..*_ga*MTMwODY1OTA0Mi4xNzMzOTM3NDMx*_ga_1NKPLGNKKD*MTczMzkzNzQyOS4xLjEuMTczMzkzNzQ2Mi4yNy4wLjA.").get();
                    Elements newsHeadlines = doc.select("li.has-pointer-d");

                    for (Element job : newsHeadlines) {
                        try {
                            String postName = job.select("div.row.is-compact.is-m.no-wrap h2 a").text();

                            String entrepriseName = job.select("div.row.is-m.no-wrap div.t-nowrap.p10l div.t-nowrap span").text();

                            String ville = job.select("div.row.is-m.no-wrap div.t-nowrap.p10l div.t-mute.t-small").text();

                            String posteDescription = job.select("div.jb-descr.m10t.t-small").text();

                            String rawDate = job.select("div.jb-footer.row.is-m.v-align-center.m10t div.jb-date.col.p0x.p0t.t-mute span").text();
                            String dateDePublication = convertDate(rawDate);


                            String lien = job.select("div.row.is-compact.is-m.no-wrap h2 a").attr("href");
                            System.out.println(lien);
                            String siteName = "bayt.com";

                            data_V2.add(new data_bayt(k, siteName, postName, entrepriseName, dateDePublication, ville, "https://www.bayt.com" +lien , posteDescription));

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
        try (FileOutputStream fileOut = new FileOutputStream("Bayt.xlsx")) {
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

            for (data_bayt data : data_V2) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.id);
                row.createCell(1).setCellValue(data.sitename);
                row.createCell(2).setCellValue(data.Postname);
                row.createCell(3).setCellValue(data.entrepriseName);
                row.createCell(4).setCellValue(data.DateDePublication);
                row.createCell(5).setCellValue(data.Ville);
                row.createCell(6).setCellValue(data.Postuler);
                row.createCell(7).setCellValue(data.posteDescription);


                Loading_Bayt dbHandler = new Loading_Bayt(data);
               dbHandler.insererDonneesDansBase();
            }
            TraitementData.main(new String[]{});
            workbook.write(fileOut);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


}
