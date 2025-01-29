package Interface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class loading_anapec {
    private data_anapec data;

    public loading_anapec(data_anapec data) {
        this.data = data;
    }

    public void insererDonneesDansBase() {
        String url = "jdbc:mysql://localhost:3306/data";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
       	 String query = "INSERT INTO dataset (Sitename, Postename, PostDescription, "
                 + "ProfilDescription, EntrepriseName, EntrepriseDescription, EntreprisAdress, "
                 + "region, Ville, SecteurActivite, Metier, Experience, NiveauEtude, Langue, "
                 + "hardSkills, SoftSkills, DatePublication, DateLimitePostuler, NombrePoste, TypeContrat, "
                 + "Teletravail, LienPostuler, SalaireMensuel, Reference, EntrepriseSite, Spécialité, "
                 + "ProfilRecherché, TraitsPersonnalite, CompétenceRecommandées, NiveauLangue, AvantagesSociaux) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

       
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.sitename);
            preparedStatement.setString(2, data.intitule);
            preparedStatement.setString(3, data.caracteristiquesPoste);
            preparedStatement.setNull(4, java.sql.Types.INTEGER);
            preparedStatement.setString(5, data.entreprise);
            preparedStatement.setString(6, data.descriptionEntreprise);
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
            preparedStatement.setString(8, data.lieuTravail);
            preparedStatement.setNull(9, java.sql.Types.INTEGER);
            preparedStatement.setString(10, data.secteur);
            preparedStatement.setNull(11, java.sql.Types.INTEGER);
            preparedStatement.setNull(12, java.sql.Types.INTEGER);
            preparedStatement.setNull(13, java.sql.Types.INTEGER);
            preparedStatement.setNull(14, java.sql.Types.INTEGER);
            preparedStatement.setNull(15, java.sql.Types.INTEGER);
            preparedStatement.setNull(16, java.sql.Types.INTEGER);
            preparedStatement.setString(17, data.date);
            preparedStatement.setNull(18, java.sql.Types.INTEGER);
            preparedStatement.setString(19, data.nombrePostes);
            preparedStatement.setString(20, data.typeContrat);
            preparedStatement.setNull(21, java.sql.Types.INTEGER);
            preparedStatement.setString(22, data.jobDetailsUrl);
            preparedStatement.setString(23, data.salaireMensuel);
            preparedStatement.setString(24, data.reference);
            preparedStatement.setNull(25, java.sql.Types.INTEGER);
            preparedStatement.setNull(26, java.sql.Types.INTEGER);
            preparedStatement.setNull(27, java.sql.Types.INTEGER);
            preparedStatement.setNull(28, java.sql.Types.INTEGER);
            preparedStatement.setNull(29, java.sql.Types.INTEGER);
            preparedStatement.setNull(30, java.sql.Types.INTEGER);
            preparedStatement.setNull(31, java.sql.Types.INTEGER);


            preparedStatement.executeUpdate();
            System.out.println("Données insérées avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
