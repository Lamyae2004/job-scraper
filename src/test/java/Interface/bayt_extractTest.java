package Interface;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class bayt_extractTest {

    @BeforeEach
    void setUp() {
        // Code to run before each test if needed
    }

    @Test
    void convertDate_DaysAgo() {
        String dateText = "Il y a 5 jours";
        String expectedDate = LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String result = bayt_extract.convertDate(dateText);
        assertEquals(expectedDate, result);
    }


    @Test
    void convertDate_30PlusDays() {
        String dateText = "30+ jours";
        String expectedDate = LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String result = bayt_extract.convertDate(dateText);
        assertEquals(expectedDate, result);
    }


    @Test
    void convertDate_Yesterday() {
        // Test for "Hier"
        String dateText = "Hier";

        String expectedDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));// using the formatter

        String result = bayt_extract.convertDate(dateText);
        assertEquals(expectedDate, result);
    }
    @Test
    void convertDate_Invalid() {
        // Test for invalid date
        String dateText = "Invalid Date";
        String result = bayt_extract.convertDate(dateText);
        assertEquals("Invalid Date", result);
    }
    @Test
    void main() {
        // Test for the main method which performs actual web scraping and file creation
        try {
            // Run the main method which performs web scraping and saves data to Excel
            bayt_extract.main(new String[]{});

            // Check that the Excel file has been created
            File excelFile = new File("Bayt.xlsx");
            assertTrue(excelFile.exists(), "The Excel file should be created.");

            // Check that the Excel file contains data (at least one row with data)
            try (FileInputStream fis = new FileInputStream(excelFile)) {
                Workbook workbook = new XSSFWorkbook(fis);
                assertTrue(workbook.getSheetAt(0).getPhysicalNumberOfRows() > 1, "There should be more than one row (header + data).");
            }

        } catch (Exception e) {
            fail("The execution of the main method failed: " + e.getMessage());
        }
    }
}
