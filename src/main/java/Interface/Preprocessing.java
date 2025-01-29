package Interface;

import java.io.File;
import weka.core.*;
import weka.core.converters.DatabaseLoader;
import weka.core.converters.ConverterUtils.DataSink;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class Preprocessing {
    public static void main(String[] args) {
        try {
            // 1. Charger les données depuis la base MySQL
            DatabaseLoader loader = new DatabaseLoader();
            loader.setSource("jdbc:mysql://localhost:3306/data", "root", "");
            loader.setQuery("SELECT CAST(id AS CHAR), Sitename, Postename, PostDescription, ProfilDescription, " +
                             "EntrepriseName, EntrepriseDescription, EntreprisAdress, region, Ville, SecteurActivite, " +
                             "Metier, Experience, NiveauEtude, Langue, hardSkills, SoftSkills, DatePublication, " +
                             "DateLimitePostuler, NombrePoste, TypeContrat, Teletravail, LienPostuler, SalaireMensuel, " +
                             "Reference, EntrepriseSite, Spécialité, ProfilRecherché, TraitsPersonnalite, CompétenceRecommandées, " +
                             "NiveauLangue, AvantagesSociaux FROM data_pretraite");
            Instances data = loader.getDataSet();

            // 2. Nettoyer les valeurs manquantes
            ReplaceMissingValues missingValuesFilter = new ReplaceMissingValues();
            missingValuesFilter.setInputFormat(data);
            data = Filter.useFilter(data, missingValuesFilter);

            // 3. Nettoyage et traitement des colonnes textuelles
            StringToWordVector filter = new StringToWordVector();
            filter.setInputFormat(data);
            filter.setTFTransform(true);  // Appliquer TF-IDF
            filter.setIDFTransform(true);
            filter.setStopwordsHandler(new weka.core.stopwords.WordsFromFile());

            // Appliquer le filtre
            Instances filteredData = Filter.useFilter(data, filter);

            // 4. Diviser les données en train (70%) et test (30%)
            int trainSize = (int) Math.round(filteredData.numInstances() * 0.7);
            int testSize = filteredData.numInstances() - trainSize;

            Instances train = new Instances(filteredData, 0, trainSize);
            Instances test = new Instances(filteredData, trainSize, testSize);

            // 5. Sauvegarder les jeux de données prétraités
            DataSink.write("train_dataff.arff", train);
            DataSink.write("test_dataff.arff", test);

            System.out.println("Prétraitement terminé. Les données train et test sont sauvegardées.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
