package Interface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateNullFields {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/data";
        String user = "root";
        String password = "";


        String updateQuery = "UPDATE dataset "
                + "SET "
                + "Sitename = COALESCE(NULLIF(Sitename, ''), 'Non spécifié'), "
                + "Postename = COALESCE(NULLIF(Postename, ''), 'Non spécifié'), "
                + "PostDescription = COALESCE(NULLIF(PostDescription, ''), 'Non spécifié'), "
                + "ProfilDescription = COALESCE(NULLIF(ProfilDescription, ''), 'Non spécifié'), "
                + "EntrepriseName = COALESCE(NULLIF(EntrepriseName, ''), 'Non spécifié'), "
                + "EntrepriseDescription = COALESCE(NULLIF(EntrepriseDescription, ''), 'Non spécifié'), "
                + "EntreprisAdress = COALESCE(NULLIF(EntreprisAdress, ''), 'Non spécifié'), "
                + "region = COALESCE(NULLIF(region, ''), 'Non spécifié'), "
                + "Ville = COALESCE(NULLIF(Ville, ''), 'Non spécifié'), "
                + "SecteurActivite = COALESCE(NULLIF(SecteurActivite, ''), 'Non spécifié'), "
                + "Metier = COALESCE(NULLIF(Metier, ''), 'Non spécifié'), "
                + "Experience = COALESCE(NULLIF(Experience, ''), 'Non spécifié'), "
                + "NiveauEtude = COALESCE(NULLIF(NiveauEtude, ''), 'Non spécifié'), "
                + "Langue = COALESCE(NULLIF(Langue, ''), 'Non spécifié'), "
                + "hardSkills = COALESCE(NULLIF(hardSkills, ''), 'Non spécifié'), "
                + "SoftSkills = COALESCE(NULLIF(SoftSkills, ''), 'Non spécifié'), "
                + "DatePublication = COALESCE(NULLIF(DatePublication, ''), 'Non spécifié'), "
                + "DateLimitePostuler = COALESCE(NULLIF(DateLimitePostuler, ''), 'Non spécifié'), "
                + "NombrePoste = COALESCE(NULLIF(NombrePoste, ''), 'Non spécifié'), "
                + "TypeContrat = COALESCE(NULLIF(TypeContrat, ''), 'Non spécifié'), "
                + "Teletravail = COALESCE(NULLIF(Teletravail, ''), 'Non spécifié'), "
                + "LienPostuler = COALESCE(NULLIF(LienPostuler, ''), 'Non spécifié'), "
                + "SalaireMensuel = COALESCE(NULLIF(SalaireMensuel, ''), 'Non spécifié'), "
                + "Reference = COALESCE(NULLIF(Reference, ''), 'Non spécifié'), "
                + "EntrepriseSite = COALESCE(NULLIF(EntrepriseSite, ''), 'Non spécifié'), "
                + "Spécialité = COALESCE(NULLIF(Spécialité, ''), 'Non spécifié'), "
                + "ProfilRecherché = COALESCE(NULLIF(ProfilRecherché, ''), 'Non spécifié'), "
                + "TraitsPersonnalite = COALESCE(NULLIF(TraitsPersonnalite, ''), 'Non spécifié'), "
                + "CompétenceRecommandées = COALESCE(NULLIF(CompétenceRecommandées, ''), 'Non spécifié'), "
                + "NiveauLangue = COALESCE(NULLIF(NiveauLangue, ''), 'Non spécifié'), "
                + "AvantagesSociaux = COALESCE(NULLIF(AvantagesSociaux, ''), 'Non spécifié');";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {


            int rowsUpdated = statement.executeUpdate(updateQuery);
            System.out.println("Nombre de lignes mises à jour : " + rowsUpdated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
