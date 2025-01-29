package Interface;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.ui.RectangleInsets;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Random;

public class EvaluationGraph {

    public static void main(String[] args) throws Exception {
        // Création de la fenêtre principale
        JFrame mainFrame = new JFrame("Interface Intégrée - Évaluation et Graphes");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1300, 1000);

        // Mise en page principale
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Création du JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Ajouter les graphiques
        tabbedPane.addTab("Graphiques", createGraphPanel());

        // Ajouter les résultats de Weka
        JTextArea consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        tabbedPane.addTab("Résultats Weka", scrollPane);

        // Rediriger la sortie standard vers JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(consoleOutput));
        System.setOut(printStream);
        System.setErr(printStream);

        // Exécuter l'algorithme Weka et afficher les résultats
        runWekaAlgorithm();

        // Ajouter le JTabbedPane au panneau principal
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Ajouter un titre
        JLabel headerLabel = new JLabel("Évaluation des Modèles ", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Configurer la fenêtre principale
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    private static JTabbedPane createGraphPanel() {
        JTabbedPane graphTabbedPane = new JTabbedPane();

        // Graphique 1 : BarChartPrecision
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(0.881, "Précision", "Casablanca et région");
        barDataset.addValue(0.821, "Précision", "Tanger et région");
        barDataset.addValue(0.0, "Précision", "Settat et région");
        barDataset.addValue(0.500, "Précision", "Fès et région");
        barDataset.addValue(0.600, "Précision", "Al Hoceima et région");
        barDataset.addValue(0.940, "Précision", "Rabat et région");


        JFreeChart barChart = ChartFactory.createBarChart(
                "Précision par région", "Région", "Précision", barDataset);

        // Personnalisation du BarChart
        barChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        barChart.setPadding(new org.jfree.chart.ui.RectangleInsets(10, 10, 10, 10));
        BarRenderer renderer = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(79, 129, 189)); // Couleur personnalisée

        graphTabbedPane.addTab("Bar Chart (Précision)", new ChartPanel(barChart));

        // Graphique 2 : PieChartClassification
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Correctement Classifié", 349);
        pieDataset.setValue("Incorrectement Classifié", 49);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Classification des Instances", pieDataset, true, true, false);

        // Personnalisation du PieChart
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setSectionPaint("Correctement Classifié", new Color(144, 238, 144));
        piePlot.setSectionPaint("Incorrectement Classifié", new Color(255, 99, 71));
        piePlot.setExplodePercent("Incorrectement Classifié", 0.15);
        piePlot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        pieChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));

        graphTabbedPane.addTab("Pie Chart (Classification)", new ChartPanel(pieChart));

        return graphTabbedPane;
    }

    private static void runWekaAlgorithm() throws Exception {
        // Import des données
        BufferedReader traind = new BufferedReader(new FileReader("train_dataff.arff"));
        BufferedReader testd = new BufferedReader(new FileReader("test_dataff.arff"));
        Instances train = new Instances(traind);
        Instances test = new Instances(testd);

        train.setClassIndex(train.attribute("region").index());
        test.setClassIndex(test.attribute("region").index());

        traind.close();
        testd.close();

        // Création et entraînement du classifieur
        IBk knn = new IBk();
        knn.buildClassifier(train);

        // Évaluation
        Evaluation eval = new Evaluation(train);
        eval.crossValidateModel(knn, train, 10, new Random(3));
        System.out.println(eval.toSummaryString("\nRésultats\n==========\n", true));
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());
    }
}