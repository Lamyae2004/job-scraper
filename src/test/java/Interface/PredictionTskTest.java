package Interface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class PredictionTskTest {

    private ClassificationTsk classification;

    @BeforeEach
    public void setUp() {
        // Initialisation de l'objet ClassificationTsk
        classification = new ClassificationTsk();
    }

    @Test
    public void testLoadModel() throws Exception {
        // Vérifie si le modèle peut être chargé correctement
        String modelPath = "j48.model";
        File modelFile = new File(modelPath);
        assertTrue(modelFile.exists(), "Le fichier du modèle J48 doit exister.");

        J48 loadedTree = classification.loadModel(modelPath);
        assertNotNull(loadedTree, "Le modèle J48 chargé ne doit pas être nul.");
    }

    @Test
    public void testGetData() throws Exception {
        // Vérifie si les données de test peuvent être chargées correctement
        String testFilePath = "data_test7.arff";
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists(), "Le fichier de test ARFF doit exister.");

        Instances testData = classification.getData(testFilePath);
        assertNotNull(testData, "Les instances de test ne doivent pas être nulles.");
        assertTrue(testData.numInstances() > 0, "Le fichier ARFF doit contenir au moins une instance.");
    }

    @Test
    public void testPredictions() throws Exception {
        // Test complet de la prédiction
        String modelPath = "j48.model";
        String testFilePath = "data_test7.arff";

        // Charger le modèle
        J48 loadedTree = classification.loadModel(modelPath);
        assertNotNull(loadedTree, "Le modèle J48 chargé ne doit pas être nul.");

        // Charger les données de test
        Instances testData = classification.getData(testFilePath);
        assertNotNull(testData, "Les instances de test ne doivent pas être nulles.");
        testData.setClassIndex(testData.numAttributes() - 1);

        // Effectuer des prédictions et vérifier les résultats
        for (int i = 0; i < testData.numInstances(); i++) {
            double predictionIndex = loadedTree.classifyInstance(testData.instance(i));
            assertTrue(predictionIndex >= 0, "L'indice de prédiction doit être positif.");
            String predictedClass = testData.classAttribute().value((int) predictionIndex);
            assertNotNull(predictedClass, "La classe prédite ne doit pas être nulle.");
            System.out.println("Instance " + (i + 1) + ": Classe prédite -> " + predictedClass);
        }
    }
}
