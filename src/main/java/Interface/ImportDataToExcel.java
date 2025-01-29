package Interface;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportDataToExcel extends DataImporter {

    private String excelFilePath = "data_scrapp.xlsx"; // Nom du fichier Excel

    @Override
    protected void writeData(ResultSet resultSet) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("DataScrapp");
            writeHeader(sheet);
            int rowCount = 0;

            // Parcours des résultats de la requête et écriture dans le fichier Excel
            while (resultSet.next()) {
                rowCount++;
                Row row = sheet.createRow(rowCount);
                for (int i = 1; i <= 25; i++) { // 25 colonnes dans la table
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
            System.out.println("Données exportées avec succès vers " + excelFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour écrire l'en-tête dans le fichier Excel
    private void writeHeader(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] columns = { "id", "Sitename", "Postename", "PostDescription", "ProfilDescription", "EntrepriseName",
                "EntrepriseDescription", "EntreprisAdress", "region", "Ville", "SecteurActivite", "Metier",
                "Experience", "NiveauEtude", "Langue", "hardSkills", "SoftSkills", "DatePublication",
                "DateLimitePostuler", "NombrePoste", "TypeContrat", "Teletravail", "LienPostuler", "SalaireMensuel",
                "Reference", "EntrepriseSite", "Spécialité", "ProfilRecherché", "TraitsPersonnalite",
                "CompétenceRecommandées", "NiveauLangue", "AvantagesSociaux" };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    public static void main(String[] args) {
        ImportDataToExcel importer = new ImportDataToExcel();
        String sqlQuery = "SELECT * FROM dataset";
        importer.executeImport(sqlQuery);
        importer.closeConnection();
    }
}
