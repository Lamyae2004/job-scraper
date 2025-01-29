package Interface;


import org.junit.jupiter.api.Test;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PreprocessingTest {

    private final String trainFilePath = "train_dataff.arff";
    private final String testFilePath = "test_dataff.arff";

    @Test
    public void testPreprocessing() {
        try {
            // Vérifier si les fichiers générés existent
            File trainFile = new File(trainFilePath);
            File testFile = new File(testFilePath);

            assertTrue(trainFile.exists(), "Le fichier train_dataff.arff n'a pas été généré.");
            assertTrue(testFile.exists(), "Le fichier test_dataff.arff n'a pas été généré.");

            // Charger les données prétraitées
            Instances trainData = DataSource.read(trainFilePath);
            Instances testData = DataSource.read(testFilePath);

            assertNotNull(trainData, "Les données d'entraînement n'ont pas été chargées.");
            assertNotNull(testData, "Les données de test n'ont pas été chargées.");

            // Vérifier la proportion des ensembles
            int totalInstances = trainData.numInstances() + testData.numInstances();
            int trainSize = trainData.numInstances();
            int testSize = testData.numInstances();

            assertEquals(totalInstances, trainSize + testSize,
                    "La somme des instances d'entraînement et de test ne correspond pas au total des instances.");
            assertEquals(trainSize, (int) Math.round(totalInstances * 0.7),
                    "Les données d'entraînement ne représentent pas 70% des instances.");
            assertEquals(testSize, totalInstances - trainSize,
                    "Les données de test ne représentent pas 30% des instances.");

        } catch (Exception e) {
            fail("Une exception s'est produite lors du test du prétraitement : " + e.getMessage());
        }
    }
}
