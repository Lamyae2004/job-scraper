package Interface;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class KMeansWithGraph {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("K-Means Clustering");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            // Panel pour le graphique
            JPanel graphPanel = new JPanel(new BorderLayout());

            // JTextArea pour afficher les résultats
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Rediriger la sortie console vers le JTextArea
            PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
            System.setOut(printStream);
            System.setErr(printStream);

            // Lancer l'algorithme K-Means
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            new Thread(() -> {
                try {
                    // Charger les données d'entraînement
                    DataSource trainSource = new DataSource("train_dataff.arff");
                    Instances trainData = trainSource.getDataSet();
                    if (trainData.classIndex() != -1) {
                        trainData.setClassIndex(-1);
                    }

                    // Charger les données de test
                    DataSource testSource = new DataSource("test_dataff.arff");
                    Instances testData = testSource.getDataSet();
                    if (testData.classIndex() != -1) {
                        testData.setClassIndex(-1);
                    }

                    // Initialiser K-Means
                    SimpleKMeans kMeans = new SimpleKMeans();
                    kMeans.setNumClusters(2);
                    kMeans.buildClusterer(trainData);

                    // Afficher les résultats pour les données d'entraînement
                    System.out.println("Centroids of the clusters:");
                    for (int i = 0; i < kMeans.getNumClusters(); i++) {
                        System.out.println(kMeans.getClusterCentroids().instance(i));
                    }

                    System.out.println("\nCluster assignments for train data:");
                    int[] trainClusterCounts = new int[kMeans.getNumClusters()];
                    for (int i = 0; i < trainData.numInstances(); i++) {
                        Instance inst = trainData.instance(i);
                        int clusterID = kMeans.clusterInstance(inst);
                        trainClusterCounts[clusterID]++;
                        System.out.println("Train Instance " + i + " -> Cluster " + clusterID);
                    }

                    // Afficher les résultats pour les données de test
                    System.out.println("\nCluster assignments for test data:");
                    int[] testClusterCounts = new int[kMeans.getNumClusters()];
                    for (int i = 0; i < testData.numInstances(); i++) {
                        Instance inst = testData.instance(i);
                        int clusterID = kMeans.clusterInstance(inst);
                        testClusterCounts[clusterID]++;
                        System.out.println("Test Instance " + i + " -> Cluster " + clusterID);
                    }

                    // Ajouter les données d'entraînement au dataset pour le graphique
                    for (int i = 0; i < trainClusterCounts.length; i++) {
                        dataset.addValue(trainClusterCounts[i], "Train Cluster " + i, "Cluster " + i);
                    }

                    // Ajouter les données de test au dataset pour le graphique
                    for (int i = 0; i < testClusterCounts.length; i++) {
                        dataset.addValue(testClusterCounts[i], "Test Cluster " + i, "Cluster " + i);
                    }

                    // Créer le graphique
                    JFreeChart chart = ChartFactory.createBarChart(
                            "Distribution des Clusters",
                            "Clusters",
                            "Nombre d'Instances",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );

                    // Mettre à jour le graphique dans l'interface
                    SwingUtilities.invokeLater(() -> {
                        graphPanel.removeAll();
                        graphPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
                        graphPanel.revalidate();
                        graphPanel.repaint();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Ajouter les composants au JFrame
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphPanel, scrollPane);
            splitPane.setDividerLocation(400);
            frame.add(splitPane);
            frame.setVisible(true);
        });
    }
}