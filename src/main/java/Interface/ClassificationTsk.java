package Interface;

import weka.core.SerializationHelper;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import javax.swing.*;

public class ClassificationTsk {
    private J48 tree;

    // Méthode pour charger les données ARFF
    public Instances getData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File(filePath));
        Instances data = arffLoader.getDataSet();  // Charger les données ARFF
        reader.close();
        System.out.println("Nombre d'instances : " + data.numInstances());
        return data;
    }

    // Méthode pour exécuter l'algorithme J48
    public void J48Algo(Instances dataTrain, Instances dataTest, JTextArea consoleOutput) throws Exception {
        // Création du classifieur J48
        J48 j48Classifier = new J48();
    

        // Définir l'attribut de classe
        int classIndex = dataTrain.numAttributes() - 1;
        dataTrain.setClassIndex(classIndex);
        dataTest.setClassIndex(classIndex);

        // Construire le classifieur
        j48Classifier.buildClassifier(dataTrain);
        
        // Sauvegarder le modèle
        saveModel(j48Classifier, "j48.model"); // Remplacez "j48.model" par votre chemin désiré 
        
        // Affichage de l'arbre dans la console et dans l'interface
        String treeString = j48Classifier.toString();
        consoleOutput.append("Arbre de décision J48 : \n" + treeString + "\n\n");

        // Visualiser l'arbre
        visualizeTree(j48Classifier);

        // Évaluer le classifieur
        evaluate(j48Classifier, dataTrain, dataTest, consoleOutput);
        this.tree = j48Classifier;
    }

    // Méthode pour visualiser l'arbre de décision
    private void visualizeTree(J48 tree) throws Exception {
        // Utiliser TreeVisualizer pour afficher l'arbre dans une fenêtre
        TreeVisualizer visualizer = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
        JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48");
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(800, 600);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(visualizer, BorderLayout.CENTER);
        jf.setVisible(true);
        visualizer.fitToScreen();
    }

    // Méthode pour évaluer le modèle et afficher la matrice de confusion
    public void evaluate(J48 tree, Instances dataTrain, Instances dataTest, JTextArea consoleOutput) throws Exception {
        consoleOutput.append("Évaluation avec les données d'entraînement\n");
        Evaluation evalTrain = new Evaluation(dataTrain);
        evalTrain.crossValidateModel(tree, dataTrain, 10, new Random(1), new Object[]{});
        
        // Affichage de la matrice de confusion pour l'entraînement
        consoleOutput.append("\nMatrice de confusion pour l'entraînement:\n");
        consoleOutput.append(evalTrain.toMatrixString() + "\n");

        consoleOutput.append("Évaluation avec les données de test\n");
        Evaluation evalTest = new Evaluation(dataTest);
        evalTest.crossValidateModel(tree, dataTest, 10, new Random(1), new Object[]{});
        
        // Affichage de la matrice de confusion pour le test
        consoleOutput.append("\nMatrice de confusion pour le test:\n");
        consoleOutput.append(evalTest.toMatrixString() + "\n");

        // Affichage du résumé de l'évaluation
        consoleOutput.append("\nRésumé de l'évaluation (train) :\n");
        consoleOutput.append(evalTrain.toSummaryString() + "\n");

        consoleOutput.append("\nRésumé de l'évaluation (test) :\n");
        consoleOutput.append(evalTest.toSummaryString() + "\n");
    }

    public static void main(String[] args) throws Exception {
        // Créer une interface graphique
        JFrame frame = new JFrame("Classification TSK - Arbre de Décision");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Panneau principal avec JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Panneau gauche pour afficher l'arbre de décision
        JPanel treePanel = new JPanel();
        treePanel.setLayout(new BorderLayout());
        splitPane.setLeftComponent(treePanel);

        // Panneau droit pour afficher les résultats
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        JTextArea consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        splitPane.setRightComponent(resultPanel);

        // Titre du panneau
        JLabel headerLabel = new JLabel("Évaluation de l'Arbre de Décision J48", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Ajouter le splitPane au cadre
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);

        // Créer une instance de la classe de classification
        ClassificationTsk classifier = new ClassificationTsk();

        // Charger les données d'entraînement et de test à partir du fichier ARFF
        Instances dataTrain = classifier.getData("data_trainff.arff");  //7 Remplacer par le chemin réel
        Instances dataTest = classifier.getData("data_testff.arff");   // 7Utilisez un fichier de test distinct si nécessaire

        // Exécuter l'algorithme J48
        System.out.println("*** J48 ****");
        classifier.J48Algo(dataTrain, dataTest, consoleOutput);
    }

    public void saveModel(J48 tree, String filePath) throws Exception {
        SerializationHelper.write(filePath, tree);
        System.out.println("Modèle sauvegardé dans : " + filePath);
    }

    // Méthode pour charger un modèle J48 sauvegardé
    public J48 loadModel(String filePath) throws Exception {
        J48 loadedTree = (J48) SerializationHelper.read(filePath);
        System.out.println("Modèle chargé depuis : " + filePath);
        return loadedTree;
    }

}