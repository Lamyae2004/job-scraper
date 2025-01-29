package Interface;

import java.io.*;
import java.util.Random;
import javax.swing.*;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class ClassificationTask extends JFrame {
    private J48 tree;
    private JTextArea textArea;

    public ClassificationTask() {
        // Configuration de l'interface
        setTitle("Résultats de Classification");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajout du JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Redirection de System.out vers JTextArea
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
    }

    public Instances getData(String chemin) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(chemin));
        ArffReader arff = new ArffReader(reader);
        Instances data = arff.getData();
        System.out.println("Nombre d'instances : " + data.numInstances());
        return data;
    }

    public void J48Algo(Instances dataTrain, Instances dataTest) throws Exception {
        // Création du classifieur J48
        J48 tree = new J48();
        dataTrain.setClassIndex(1);
        dataTest.setClassIndex(1);

        String[] options = new String[1];
        options[0] = "-U";
        tree.setOptions(options);
        tree.buildClassifier(dataTrain);
        System.out.println(tree);

        Evalute(tree, dataTrain, dataTest);
        this.tree = tree;
    }

    public void Evalute(J48 tree, Instances dataTrain, Instances dataTest) throws Exception {
        System.out.println("Évaluation avec les données d'entraînement");
        Evaluation evalTrain = new Evaluation(dataTrain);
        evalTrain.crossValidateModel(tree, dataTrain, 10, new Random(1), new Object[] {});
        System.out.println(evalTrain.toSummaryString());

        System.out.println("Évaluation avec les données de test");
        Evaluation evalTest = new Evaluation(dataTest);
        evalTest.crossValidateModel(tree, dataTest, 10, new Random(1), new Object[] {});
        System.out.println(evalTest.toSummaryString());
    }

    public void RandomForest(Instances dataTrain, Instances dataTest) throws Exception {
        // Assurez-vous que la classe cible est définie
        dataTrain.setClassIndex(1);
        dataTest.setClassIndex(1);

        // Création du modèle RandomForest
        weka.classifiers.trees.RandomForest forest = new weka.classifiers.trees.RandomForest();

        // Entraînement du modèle
        forest.buildClassifier(dataTrain);
        System.out.println(forest);

        // Évaluation avec les données d'entraînement
        System.out.println("Évaluation avec les données d'entraînement");
        Evaluation evalTrain = new Evaluation(dataTrain);
        evalTrain.crossValidateModel(forest, dataTrain, 10, new Random(1), new Object[] {});
        System.out.println(evalTrain.toSummaryString());

        // Évaluation avec les données de test
        System.out.println("Évaluation avec les données de test");
        Evaluation evalTest = new Evaluation(dataTest);
        evalTest.crossValidateModel(forest, dataTest, 10, new Random(1), new Object[] {});
        System.out.println(evalTest.toSummaryString());
    }


    public static void main(String[] args) throws Exception {
        // Création de la fenêtre
        ClassificationTask frame = new ClassificationTask();
        frame.setVisible(true);

        // Exécution de la classification
        Instances dataTrain = frame.getData("traincompF.arff");
        Instances dataTest = frame.getData("testcompF.arff");

        System.out.println("************************** J48 *************************");
        frame.J48Algo(dataTrain, dataTest);
        frame.RandomForest(dataTrain, dataTest);
    }
}
/*Ce programme semble être un outil d'évaluation des technologies ou des compétences utilisées
 dans un certain contexte, comme une base de données, une plateforme de développement,
  ou un environnement de travail. Les résultats indiquent la présence ou l'absence de
   technologies spécifiques (comme MariaDB) dans certains cas ou contextes.*/



/*The code trains a decision tree model using WEKA's J48 algorithm. The model performed
well on the training data, correctly classifying 95.6% of instances, but its performance
dropped to 86.8% on the test data. This suggests the model might be overfitting to the
training data. The tree has 78 leaves and 142 nodes, indicating its complexity. The Kappa
statistic and error values also show the model's performance, with a higher accuracy on
training data and a lower one on test data. This shows that while the decision tree works
well, further improvements or adjustments may be needed.*/