package Interface;

import weka.core.Instances;
import weka.classifiers.trees.J48;
import javax.swing.*;
import java.awt.*;

public class PredictionTsk {
    public static void main(String[] args) throws Exception {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Résultats de Prédiction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Zone de texte pour afficher les résultats
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Ajouter la zone de texte à la fenêtre
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        // Chemins des fichiers
        String modelPath = "j48.model"; // Modèle sauvegardé
        String testFilePath = "data_testff.arff"; // Fichier de test

        // Charger le modèle
        ClassificationTsk classification = new ClassificationTsk();
        J48 loadedTree = classification.loadModel(modelPath);

        // Charger les données de test
        Instances testData = classification.getData(testFilePath);
        testData.setClassIndex(testData.numAttributes() - 1);

        // Effectuer des prédictions
        for (int i = 0; i < testData.numInstances(); i++) {
            double predictionIndex = loadedTree.classifyInstance(testData.instance(i));
            String predictedClass = testData.classAttribute().value((int) predictionIndex);
            String result = "Instance " + (i + 1) + ": Classe prédite -> " + predictedClass + "\n";
            textArea.append(result); // Ajouter le résultat à la zone de texte
        }
    }
}