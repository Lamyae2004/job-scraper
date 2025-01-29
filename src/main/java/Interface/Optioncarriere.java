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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Optioncarriere {
    private static String[] columns = {"id", "sitename", "Postname", "entrepriseName", "DateDePublication",
            "Ville", "Postuler", "posteDescription"};
    private static List<Data_optioncarriere> data_V2 = new ArrayList<>();
    public static String convertDate(String dateText) {
        // Définir le format de sortie JJ/MM/YYYY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Pattern hoursPattern = Pattern.compile("Il y a (\\d+) heures");
        Matcher hoursMatcher = hoursPattern.matcher(dateText);
        if (hoursMatcher.find()) {
            int hoursAgo = Integer.parseInt(hoursMatcher.group(1));
            LocalDateTime dateTime = LocalDateTime.now().minusHours(hoursAgo);
            return dateTime.toLocalDate().format(formatter); // Retourne la date sans l'heure
        }




        return dateText;
    }



    public static void main(String[] args) {
        try {
            int k = 1;

            for (int i = 2; i < 30; i++) {
                try{
                Document doc = Jsoup.connect("https://www.optioncarriere.ma/emploi?s=&l=Maroc&p="+i).get();

                // Sélectionner tous les articles avec la classe "job clicky"
                Elements jobOffers = doc.select("article.job.clicky");

                for (Element job : jobOffers) {
                    try {

                        String postName = job.select("header h2 a").text();

                        String entrepriseName = job.select("p.company a").text();


                        Elements locations = job.select("ul.location li");
                        String location = locations.text();


                        String description = job.select("div.desc").text();


                        String rawDate = job.select("footer ul.tags li span.badge.badge-r.badge-s.badge-icon ").text();
                        String dateDePublication = convertDate(rawDate);

                        String lien = job.select("p.company a").attr("href");

                        String siteName = "offrescarriere.ma";

                        data_V2.add(new Data_optioncarriere(k, siteName, postName, entrepriseName, dateDePublication, location,"https://www.optioncarriere.ma" +lien ,description));

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
    try (FileOutputStream fileOut = new FileOutputStream("offrescarriere.xlsx")) {
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

        for (Data_optioncarriere data : data_V2) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.id);
            row.createCell(1).setCellValue(data.sitename);
            row.createCell(2).setCellValue(data.Postname);
            row.createCell(3).setCellValue(data.entrepriseName);
            row.createCell(4).setCellValue(data.DateDePublication);
            row.createCell(5).setCellValue(data.Ville);
            row.createCell(6).setCellValue(data.Postuler);
            row.createCell(7).setCellValue(data.posteDescription);
            Loading_Optioncarriere dbHandler = new Loading_Optioncarriere(data);
            dbHandler.insererDonneesDansBase();

        }
        TraitementData.main(new String[]{});
        workbook.write(fileOut);
    } catch (IOException | SQLException e) {
        e.printStackTrace();
    }
}


}
