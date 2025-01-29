package Interface;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MenaraJobTest {

    @Test
    public void testMainExecution() {
        try {

            String excelFilePath = "S1_rekrute.xlsx";
            Path path = Paths.get(excelFilePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            // Exécuter la méthode principale
            MenaraJob.main(new String[]{});

            // Vérifier que le fichier Excel a été créé
            File excelFile = new File(excelFilePath);
            assertTrue(excelFile.exists(), "Le fichier Excel n'a pas été créé.");

            // Vérifier que le fichier Excel n'est pas vide
            assertTrue(excelFile.length() > 0, "Le fichier Excel est vide.");

            System.out.println("Test passed: Le fichier Excel a été généré avec succès et contient des données.");

        } catch (Exception e) {
            fail("Une exception s'est produite lors du test : " + e.getMessage());
        }
    }
}
