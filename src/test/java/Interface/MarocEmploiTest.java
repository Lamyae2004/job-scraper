package Interface;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MarocEmploiTest {

    @Test
    void testMain() {
        try {
            // Act: Call the main method of MarocEmploi
            MarocEmploi.main(new String[] {});

            // Assert: Verify that the Excel file is created
            File file = new File("OffresEmploi.xlsx");
            assertTrue(file.exists(), "Excel file should be created");

            // Optional: Open the Excel file and verify its contents
            try (FileInputStream fis = new FileInputStream(file)) {
                Workbook workbook = new XSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(0);
                assertNotNull(sheet, "Sheet should not be null");

                // Verify if the header row is correct
                Row headerRow = sheet.getRow(0);
                assertNotNull(headerRow, "Header row should not be null");
                assertEquals("ID", getCellValue(headerRow.getCell(0)));
                assertEquals("Site_Name", getCellValue(headerRow.getCell(1)));
                assertEquals("Post_Name", getCellValue(headerRow.getCell(2)));

                // Optionally, verify content of the first row (e.g., job data)
                Row firstRow = sheet.getRow(1);
                assertNotNull(firstRow, "First row should not be null");
                assertEquals("1", getCellValue(firstRow.getCell(0))); // Assuming ID starts at 1
                // Additional assertions can be added here to check other columns

            } catch (IOException e) {
                e.printStackTrace();
                fail("Failed to read Excel file: " + e.getMessage());
            }

            // Optional: Clean up after the test by deleting the file
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue() % 1 == 0
                        ? String.valueOf((int) cell.getNumericCellValue())
                        : String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
