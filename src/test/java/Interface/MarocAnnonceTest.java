package Interface;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarocAnnonceTest {

    @BeforeEach
    void setUp() {
        // Reset the dataList before each test
        MarocAnnonce.addData(new DataMarocAnnonce(
                1, "marocannonces.com", "Software Engineer", "Tech Corp", "Rabat",
                "Bachelors", "Software Development", "5000 DH", "2024-12-20", "https://example.com"
        ));
    }

    @Test
    void testSaveToExcel() {
        // Arrange: Create mock data for testing
        DataMarocAnnonce mockData = new DataMarocAnnonce(
                1, "marocannonces.com", "Software Engineer", "Tech Corp", "Rabat",
                "Bachelors", "Software Development", "5000 DH", "2024-12-20", "https://example.com"
        );
        MarocAnnonce.addData(mockData);

        // Act: Call the saveToExcel method
        MarocAnnonce.saveToExcel();

        // Assert: Verify if the file was created
        File file = new File("MarocAnnonce.xlsx");
        assertTrue(file.exists(), "Excel file should be created");

        // Open the Excel file and verify its content
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);

            assertNotNull(row, "Row should not be null");
            assertEquals("1", getCellValue(row.getCell(0)));
            assertEquals("marocannonces.com", getCellValue(row.getCell(1)));
            assertEquals("Software Engineer", getCellValue(row.getCell(2)));
            assertEquals("Tech Corp", getCellValue(row.getCell(3)));
            assertEquals("Rabat", getCellValue(row.getCell(4)));
            assertEquals("Bachelors", getCellValue(row.getCell(5)));
            assertEquals("Software Development", getCellValue(row.getCell(6)));
            assertEquals("5000 DH", getCellValue(row.getCell(7)));
            assertEquals("2024-12-20", getCellValue(row.getCell(8)));
            assertEquals("https://example.com", getCellValue(row.getCell(9)));

        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to read Excel file: " + e.getMessage());
        }
    }

    @Test
    void testMain() {

        try {
            // Act: Call the main method
            MarocAnnonce.main(new String[] {});

            // Assert: Verify that the Excel file is created after the execution
            File file = new File("MarocAnnonce.xlsx");
            assertTrue(file.exists(), "Excel file should be created");

            // Optional clean up after test by deleting the file
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
