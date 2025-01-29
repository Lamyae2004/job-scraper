package Interface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;

public class anapec {
    private static int globalCounter = 1;

    public static void main(String[] args) {
        int totalPages = 30;

        for (int page = 1; page <= totalPages; page++) {
            String searchUrl = "http://anapec.org/sigec-app-rv/chercheurs/resultat_recherche/page:" + page + "/tout:all/language:fr";

            try {
                Document document = Jsoup.connect(searchUrl).timeout(10000).get();
                Element table = document.select("table#myTable").first();
                Elements rows = table.select("tbody tr");

                for (Element row : rows) {
                    String reference = row.select("td:nth-child(2) a").text();
                    String date = row.select("td:nth-child(3)").text();
                    String intitule = row.select("td:nth-child(4)").text();
                    String nombrePostes = row.select("td:nth-child(5)").text();
                    String entreprise = row.select("td:nth-child(6)").text();
                    String lieuTravail = row.select("td:nth-child(7)").text();
                    String jobDetailsUrl = "http://anapec.org" + row.select("td:nth-child(2) a").attr("href");

                    scrapeJobDetails(jobDetailsUrl, reference, date, intitule, nombrePostes, entreprise, lieuTravail);

                    globalCounter++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void scrapeJobDetails(String jobDetailsUrl, String reference, String date, String intitule,
                                         String nombrePostes, String entreprise, String lieuTravail) {
        try {
            Document document = Jsoup.connect(jobDetailsUrl).get();

            String descriptionEntreprise = document.select("h1:contains(Description de l\\'entreprise)").next().text();
            String descriptionPoste = document.select("h1:contains(Description de Poste)").next().text();
            String typeContrat = document.select("p:contains(Type de contrat)").select("span").text();
            String salaireMensuel = document.select("p:contains(Salaire mensuel)").select("span").text();
            String secteur = document.select("p:contains(Secteur d’activité)").select("span").text();
            String caracteristiquesPoste = document.select("p:contains(Caractéristiques du poste)").select("p").text();

            data_anapec data = new data_anapec("Anapec", reference, date, intitule, nombrePostes, entreprise, lieuTravail,
                    jobDetailsUrl, descriptionEntreprise, descriptionPoste, typeContrat, salaireMensuel, caracteristiquesPoste, secteur);

            loading_anapec dbHandler = new loading_anapec(data);
            dbHandler.insererDonneesDansBase();
            TraitementData.main(new String[]{});
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    
    
   
    
      

}