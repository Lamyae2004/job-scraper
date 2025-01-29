package Interface;

import weka.classifiers.functions.SMO;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class ClassificationSVM {
    private SMO svm;

    // Méthode pour charger les données ARFF
    public Instances getData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File(filePath));
        Instances data = arffLoader.getDataSet();
        reader.close();
        System.out.println("Nombre d'instances : " + data.numInstances());
        return data;
    }

    // Méthode pour exécuter l'algorithme SVM
    public void SVMAlgo(Instances dataTrain, Instances dataTest, JTextArea consoleOutput) throws Exception {
        SMO svmClassifier = new SMO();
        dataTrain.setClassIndex(dataTrain.numAttributes() - 1);
        dataTest.setClassIndex(dataTest.numAttributes() - 1);

        // Construire le classifieur
        svmClassifier.buildClassifier(dataTrain);
        consoleOutput.append("Modèle SVM entraîné avec succès.\n");

        // Sauvegarder le modèle
        saveModel(svmClassifier, "svm.model");
        consoleOutput.append("Modèle SVM sauvegardé dans le fichier : svm.model\n");

        // Évaluer le classifieur
        evaluate(svmClassifier, dataTrain, dataTest, consoleOutput);
        this.svm = svmClassifier;
    }

    // Méthode pour évaluer le modèle et afficher la matrice de confusion
    public void evaluate(SMO svm, Instances dataTrain, Instances dataTest, JTextArea consoleOutput) throws Exception {
        consoleOutput.append("Évaluation avec les données d'entraînement\n");
        Evaluation evalTrain = new Evaluation(dataTrain);
        evalTrain.crossValidateModel(svm, dataTrain, 10, new Random(1), new Object[]{});

        consoleOutput.append("\nMatrice de confusion pour l'entraînement:\n");
        consoleOutput.append(evalTrain.toMatrixString() + "\n");

        consoleOutput.append("Évaluation avec les données de test\n");
        Evaluation evalTest = new Evaluation(dataTest);
        evalTest.crossValidateModel(svm, dataTest, 10, new Random(1), new Object[]{});

        consoleOutput.append("\nMatrice de confusion pour le test:\n");
        consoleOutput.append(evalTest.toMatrixString() + "\n");

        consoleOutput.append("\nRésumé de l'évaluation (train):\n");
        consoleOutput.append(evalTrain.toSummaryString() + "\n");

        consoleOutput.append("\nRésumé de l'évaluation (test):\n");
        consoleOutput.append(evalTest.toSummaryString() + "\n");
    }

    public void saveModel(SMO svm, String filePath) throws Exception {
        SerializationHelper.write(filePath, svm);
        System.out.println("Modèle SVM sauvegardé dans : " + filePath);
    }

    public SMO loadModel(String filePath) throws Exception {
        SMO loadedSVM = (SMO) SerializationHelper.read(filePath);
        System.out.println("Modèle SVM chargé depuis : " + filePath);
        return loadedSVM;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Classification SVM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        JTextArea consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        splitPane.setRightComponent(resultPanel);

        JLabel headerLabel = new JLabel("Évaluation SVM", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);

        ClassificationSVM classifier = new ClassificationSVM();

        Instances dataTrain = classifier.getData("data_trainff.arff");
        Instances dataTest = classifier.getData("data_testff.arff");

        System.out.println("*** SVM ****");
        classifier.SVMAlgo(dataTrain, dataTest, consoleOutput);
    }
}
