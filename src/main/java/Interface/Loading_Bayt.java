package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Loading_Bayt {
    private data_bayt data;

    public Loading_Bayt(data_bayt data) {
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
            preparedStatement.setString(2, data.Postname);
            preparedStatement.setString(3, data.posteDescription);
            preparedStatement.setNull(4, java.sql.Types.INTEGER);
            preparedStatement.setString(5, data.entrepriseName);
            preparedStatement.setNull(6, java.sql.Types.INTEGER);
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
            preparedStatement.setNull(8, java.sql.Types.INTEGER);
            preparedStatement.setString(9, data.Ville);
            preparedStatement.setNull(10, java.sql.Types.INTEGER);
            preparedStatement.setNull(11, java.sql.Types.INTEGER);
            preparedStatement.setNull(12, java.sql.Types.INTEGER);
            preparedStatement.setNull(13, java.sql.Types.INTEGER);
            preparedStatement.setNull(14, java.sql.Types.INTEGER);
            preparedStatement.setNull(15, java.sql.Types.INTEGER);
            preparedStatement.setNull(16, java.sql.Types.INTEGER);
            preparedStatement.setString(17, data.DateDePublication);
            preparedStatement.setNull(18, java.sql.Types.INTEGER);
            preparedStatement.setNull(19, java.sql.Types.INTEGER);
            preparedStatement.setNull(20, java.sql.Types.INTEGER);
            preparedStatement.setNull(21, java.sql.Types.INTEGER);
            preparedStatement.setString(22, data.Postuler);
            preparedStatement.setNull(23, java.sql.Types.INTEGER);
            preparedStatement.setNull(24, java.sql.Types.INTEGER);
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
