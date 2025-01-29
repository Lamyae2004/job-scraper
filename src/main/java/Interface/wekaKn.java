package Interface;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.neighboursearch.LinearNNSearch;
import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class wekaKn {

	public static void main(String[] args) throws Exception {
		// Charger les données d'entraînement et de test à partir des fichiers ARFF
		BufferedReader traind = new BufferedReader(new FileReader("train_dataff.arff"));
		BufferedReader testd = new BufferedReader(new FileReader("test_dataff.arff"));

		// Créer des instances pour les fichiers d'entraînement et de test
		Instances train = new Instances(traind);
		Instances test = new Instances(testd);

		// Définir l'attribut "region" comme classe cible pour les deux jeux de données
		train.setClassIndex(train.attribute("region").index());
		test.setClassIndex(test.attribute("region").index());

		traind.close();
		testd.close();

		// Initialiser le classifieur KNN (IBk)
		IBk knnClassifier = new IBk();

		// Construire le modèle sur les données d'entraînement
		knnClassifier.buildClassifier(train);

		// Évaluer le modèle sur les données d'entraînement
		Evaluation trainEval = new Evaluation(train);
		trainEval.crossValidateModel(knnClassifier, train, 10, new Random(1));

		// Évaluer le modèle sur les données de test
		Evaluation testEval = new Evaluation(train);
		testEval.evaluateModel(knnClassifier, test);

		// Afficher les résultats pour le jeu de données d'entraînement
		System.out.println("\nRésultats sur les données d'entraînement:");
		System.out.println(trainEval.toSummaryString("\nRésumé\n========\n", true));
		System.out.println(trainEval.toClassDetailsString());
		System.out.println(trainEval.toMatrixString());

		// Afficher les résultats pour le jeu de données de test
		System.out.println("\nRésultats sur les données de test:");
		System.out.println(testEval.toSummaryString("\nRésumé\n========\n", true));
		System.out.println(testEval.toClassDetailsString());
		System.out.println(testEval.toMatrixString());

		// Mesures supplémentaires (précision, rappel, etc.)
		System.out.println("\nMesures supplémentaires:");
		System.out.println("F-Measure: " + testEval.fMeasure(1));
		System.out.println("Précision: " + testEval.precision(1));
		System.out.println("Rappel: " + testEval.recall(1));

		// Tester les k plus proches voisins pour une instance spécifique
		//LinearNNSearch knnSearch = new LinearNNSearch(train);
		//System.out.println("\nInstance la plus proche pour la première donnée de test:");
		//System.out.println(knnSearch.kNearestNeighbours(test.get(0), 1).firstInstance());
	}
}
