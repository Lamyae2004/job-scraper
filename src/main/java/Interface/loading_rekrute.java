package Interface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class loading_rekrute {
    private Data_rekrute data;

    

    public loading_rekrute(Data_rekrute data) {
		super();
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
            preparedStatement.setString(4, data.profilRecherche);
            preparedStatement.setString(5, data.entrepriseName);
            preparedStatement.setString(6, data.EntrepriseDes);
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
            preparedStatement.setString(8, data.region);
            preparedStatement.setNull(9, java.sql.Types.INTEGER);
            preparedStatement.setString(10, data.secteur);
            preparedStatement.setNull(11, java.sql.Types.INTEGER);
            preparedStatement.setString(12, data.experience);
            preparedStatement.setString(13, data.Niveau_etude);
            preparedStatement.setString(14, data.langue);
            preparedStatement.setString(15,data.HardSkills);
            preparedStatement.setString(16, data.TraitePersonaliter);
            preparedStatement.setString(17, data.DateDePublication);
            preparedStatement.setString(18, data.DateLimitePourPostuler);
            preparedStatement.setString(19, data.nombrePostes);
            preparedStatement.setString(20, data.Type_de_contrat);
            preparedStatement.setString(21, data.Télétravail);
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