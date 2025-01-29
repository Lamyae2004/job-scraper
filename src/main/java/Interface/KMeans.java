package Interface;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;

public class KMeans {

    public static void main(String[] args) {
        try {
            // Charger les données d'entraînement depuis le fichier ARFF
            DataSource source = new DataSource("train_dataff.arff");
            Instances data = source.getDataSet();
            
            // Vérifier si la dernière colonne est la classe et la supprimer
            if (data.classIndex() != -1) {
            	  data.setClassIndex(-1); // Réinitialiser l'attribut de classe au lieu de le supprimer
            }

            // Initialiser l'algorithme K-Means avec 2 clusters (changez ce nombre selon vos besoins)
            SimpleKMeans kMeans = new SimpleKMeans();
            kMeans.setNumClusters(2);
            kMeans.buildClusterer(data);  // Appliquer K-Means sur les données

            // Afficher les centres des clusters
            System.out.println("Centroids of the clusters:");
            for (int i = 0; i < kMeans.getNumClusters(); i++) {
                System.out.println(kMeans.getClusterCentroids().instance(i));
            }

            // Appliquer le clustering sur les données d'entraînement et afficher les résultats
            System.out.println("\nCluster assignments for train data:");
            for (int i = 0; i < data.numInstances(); i++) {
                Instance inst = data.instance(i);
                int clusterID = kMeans.clusterInstance(inst);
                System.out.println("Instance " + i + " -> Cluster " + clusterID);
            }

            // Charger les données de test
            DataSource testSource = new DataSource("test_dataff.arff");
            Instances testData = testSource.getDataSet();
            
            // Vérifier et supprimer l'attribut de classe pour les données de test
            if (data.classIndex() != -1) {
                data.setClassIndex(-1); // Réinitialiser l'attribut de classe au lieu de le supprimer
            }
            // Appliquer le modèle K-Means sur les données de test
            System.out.println("\nCluster assignments for test data:");
            for (int i = 0; i < testData.numInstances(); i++) {
                Instance testInst = testData.instance(i);
                int clusterID = kMeans.clusterInstance(testInst);
                System.out.println("Test Instance " + i + " -> Cluster " + clusterID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}