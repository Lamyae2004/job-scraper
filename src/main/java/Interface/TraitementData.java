package Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TraitementData {
	 public static void main(String[] args) throws SQLException {
		 String url = "jdbc:mysql://localhost:3306/data";
	     String user = "root";
	     String password = "";
	     
	     
	     Connection connection = null;
	     Statement statement = null;
	     ResultSet resultSet = null;
         
	     try { connection = DriverManager.getConnection(url, user, password);
         statement = connection.createStatement();

         PreparedStatement insertVille = connection.prepareStatement(
                 "UPDATE dataset\r\n"
                 + "SET ville = LOWER(\r\n"
                 + "    CASE\r\n"
                 + "        WHEN region LIKE 'Tout le Maroc%' THEN 'maroc'\r\n"
                 + "        WHEN region LIKE '%et région%' THEN TRIM(SUBSTRING_INDEX(region, ' et région', 1))\r\n"
                 + "        WHEN region LIKE '%Région indifférente%' THEN 'indifferente'\r\n"
                 + "        WHEN region LIKE '%Autres régions%' THEN 'inconnu'\r\n"
                 + "        ELSE 'inconnu'\r\n"
                 + "    END\r\n"
                 + ")\r\n"
                 + "WHERE region IS NOT NULL AND Sitename='rekrute.com';\r\n"
                 + "");
         
         int rowsUpdatedVille = insertVille.executeUpdate();
         if (rowsUpdatedVille > 0) {
             System.out.println("Mise à jour des villes réussie : " + rowsUpdatedVille + " lignes modifiées.");
         }
         PreparedStatement UpSecteurs = connection.prepareStatement(
                 "UPDATE dataset\r\n"
                 + "SET SecteurActivite = CASE\r\n"
                 + "    -- Secteurs industriels\r\n"
                 + "    WHEN SecteurActivite IN ('Aéronautique / Spatial', 'Automobile / Motos / Cycles', 'Electro-mécanique / Mécanique', 'Métallurgie / Sidérurgie', 'Pétrole / Gaz', 'Chimie / Parachimie / Peintures', 'Textile / Cuir', 'Papier / Carton', 'Cosmétique / Parfumerie / Luxe') THEN 'industrie'\r\n"
                 + "    WHEN SecteurActivite IN ('Méthodes / Process / Industrialisation', 'Production / Qualité / Sécurité / Maintenance') THEN 'industrie'\r\n"
                 + "\r\n"
                 + "    -- Secteurs liés à l'étude, à la recherche et à l'innovation\r\n"
                 + "    WHEN SecteurActivite IN ('Gestion projet / Etudes / R&D', 'Conseil / Etudes') THEN 'etudes'\r\n"
                 + "\r\n"
                 + "    -- Secteurs liés au transport et à la logistique\r\n"
                 + "    WHEN SecteurActivite IN ('Logistique / Transport (métiers de)', 'Transport / Messagerie / Logistique', 'Tourisme (métiers du)', 'Commercial / Vente / Export') THEN 'transport'\r\n"
                 + "\r\n"
                 + "    -- Secteurs agroalimentaires et agricoles\r\n"
                 + "    WHEN SecteurActivite IN ('Agroalimentaire', 'Agriculture / Environnement') THEN 'agroalimentaire'\r\n"
                 + "\r\n"
                 + "    -- Secteurs de l'hôtellerie et de la restauration\r\n"
                 + "    WHEN SecteurActivite IN ('Hôtellerie / Restauration', 'Hôtellerie / Restauration (métiers de)', 'Tourisme / Voyage / Loisirs') THEN 'hotellerie'\r\n"
                 + "\r\n"
                 + "    -- Secteurs de l'informatique et du numérique\r\n"
                 + "    WHEN SecteurActivite IN ('Informatique', 'Internet / Multimédia', 'Télécoms / Réseaux', 'Informatique / Electronique', 'Multimédia / Internet') THEN 'informatique'\r\n"
                 + "\r\n"
                 + "    -- Secteurs du commerce et de la distribution\r\n"
                 + "    WHEN SecteurActivite IN ('Commerce', 'Distribution', 'Call Centers (métiers de)', 'Recrutement / Intérim', 'Import / Export / Négoce') THEN 'commerce'\r\n"
                 + "\r\n"
                 + "    -- Secteurs administratifs et de gestion\r\n"
                 + "    WHEN SecteurActivite IN ('Administration des ventes / SAV', 'RH / Personnel / Formation', 'Service public / Administration', 'Assistanat de Direction / Services Généraux') THEN 'administration'\r\n"
                 + "\r\n"
                 + "    -- Secteurs du BTP et de la construction\r\n"
                 + "    WHEN SecteurActivite IN ('BTP / Génie Civil','Urbanisme / architecture','Responsable de Département') THEN 'btp'\r\n"
                 + "\r\n"
                 + "    -- Secteurs financiers et bancaires\r\n"
                 + "    WHEN SecteurActivite IN ('Banque / Finance', 'Assurance / Courtage', 'Immobilier / Promotion (métiers de)') THEN 'finance'\r\n"
                 + "\r\n"
                 + "    -- Secteurs de la communication et du marketing\r\n"
                 + "    WHEN SecteurActivite IN ('Communication / Evénementiel', 'Communication / Publicité / RP', 'Marketing', 'Agence pub / Marketing Direct', 'Communication / Evénementiel') THEN 'communication'\r\n"
                 + "\r\n"
                 + "    -- Secteurs juridiques\r\n"
                 + "    WHEN SecteurActivite IN ('Juridique / Cabinet d’avocats', 'Avocat / Juriste / Fiscaliste', 'Juridique', 'Assurance / Courtage') THEN 'juridique'\r\n"
                 + "\r\n"
                 + "    -- Secteurs immobiliers\r\n"
                 + "    WHEN SecteurActivite IN ('Immobilier / Promoteur / Agence', 'Immobilier / Promoteur / Agence', 'Immobilier') THEN 'immobilier'\r\n"
                 + "\r\n"
                 + "    -- Secteurs des agences de services\r\n"
                 + "    WHEN SecteurActivite IN ('Agences', 'Agence pub / Marketing Direct') THEN 'agences'\r\n"
                 + "\r\n"
                 + "    -- Secteurs de l'éducation et de la formation\r\n"
                 + "    WHEN SecteurActivite IN ('Enseignement', 'Enseignement / Formation') THEN 'enseignement'\r\n"
                 + "\r\n"
                 + "    -- Secteurs généraux et autres\r\n"
                 + "    WHEN SecteurActivite IN ('Publicité', 'Energie', 'Offshoring / Nearshoring', 'Indifférent') THEN 'Services Généraux'\r\n"
                 + "\r\n"
                 + "    -- Secteurs liés à la santé\r\n"
                 + "    WHEN SecteurActivite IN ('Médical / Paramédical') THEN 'sante'\r\n"
                 + "\r\n"
                 + "    -- Secteurs divers (qui ne correspondent pas à des groupes spécifiques)\r\n"
                 + "    WHEN SecteurActivite IN ('Métallurgie / Sidérurgie', 'Textile / Cuir') THEN 'services_generaux'\r\n"
                 + "\r\n"
                 + "    ELSE 'services_generaux'\r\n"
                 + "END\r\n"
                 + "WHERE Sitename = 'rekrute.com';\r\n"
                 + ""
);
         
         int rowsUpdatedSecteurs = UpSecteurs.executeUpdate();
         if (rowsUpdatedSecteurs > 0) {
             System.out.println("Mise à jour des secteurs réussie : " + rowsUpdatedSecteurs + " lignes modifiées.");
         }
         String sqlLangue = "UPDATE dataset SET Langue='Français Anglais' WHERE Langue IS NULL";
         String sqlNiveauLangue = "UPDATE dataset SET NiveauLangue='Bon niveau' WHERE NiveauLangue IS NULL";
         PreparedStatement UpLangue = connection.prepareStatement(sqlLangue);
         int rowsUpdatedLangue = UpLangue.executeUpdate();
         if (rowsUpdatedLangue > 0) {
             System.out.println("Mise à jour des Langues réussie : " + rowsUpdatedLangue + " lignes modifiées.");
         }
         PreparedStatement UpNiveauLangue = connection.prepareStatement(sqlNiveauLangue);
         int rowsUpdatedNiveau = UpNiveauLangue.executeUpdate();
         if (rowsUpdatedNiveau > 0) {
             System.out.println("Mise à jour des NiveauLangues réussie : " + rowsUpdatedNiveau + " lignes modifiées.");
         }
         
         String sqlMetier = "UPDATE dataset SET Metier=Postename WHERE Metier IS NULL";
         PreparedStatement UpMetier = connection.prepareStatement(sqlMetier);
         int rowsUpdatedMetier = UpMetier.executeUpdate();
         if (rowsUpdatedMetier > 0) {
             System.out.println("Mise à jour des Metiers réussie : " + rowsUpdatedMetier + " lignes modifiées.");
         }
         String sqlTraitPerso = "UPDATE dataset SET TraitsPersonnalite=SoftSkills WHERE TraitsPersonnalite IS NULL";
         PreparedStatement UpPerso = connection.prepareStatement(sqlTraitPerso);
         int rowsUpdatedPerso= UpPerso.executeUpdate();
         if (rowsUpdatedPerso > 0) {
             System.out.println("Mise à jour des TraitPerso réussie : " + rowsUpdatedPerso + " lignes modifiées.");
         }
             String sqlSalaire = "UPDATE dataset SET SalaireMensuel='A discuter' WHERE SalaireMensuel IS NULL";
             PreparedStatement UpSalaireMensuel = connection.prepareStatement(sqlSalaire);
             int rowsUpdatedsqlSalaire= UpSalaireMensuel.executeUpdate();
             if (rowsUpdatedsqlSalaire > 0) {
                 System.out.println("Mise à jour des SalaireMensuel réussie : " + rowsUpdatedPerso + " lignes modifiées.");
             }
         String sqlCompRecom = "UPDATE dataset " +
                 "SET CompétenceRecommandées = " +
                 "COALESCE(hardSkills, softSkills) " +
                 "WHERE CompétenceRecommandées IS NULL";
PreparedStatement UpCompRecom = connection.prepareStatement(sqlCompRecom);
int rowsUpdatedCompRecom = UpCompRecom.executeUpdate();
if (rowsUpdatedCompRecom > 0) {
System.out.println("Mise à jour des Compétences recommandées réussie : " + rowsUpdatedCompRecom + " lignes modifiées.");
}

         
         String sqlProfil = "UPDATE dataset SET ProfilRecherché=Postename WHERE ProfilRecherché IS NULL";
         PreparedStatement UpProfil = connection.prepareStatement(sqlProfil);
         int rowsUpdatedProfil = UpProfil.executeUpdate();
         if (rowsUpdatedProfil > 0) {
             System.out.println("Mise à jour des Metiers réussie : " + rowsUpdatedProfil + " lignes modifiées.");
         }
         
         String sqlNbrePoste = "UPDATE dataset SET NombrePoste='1' WHERE NombrePoste IS NULL";
         PreparedStatement UpNbrePoste = connection.prepareStatement(sqlNbrePoste);
         int rowsUpdatedNbrePoste = UpMetier.executeUpdate();
         if (rowsUpdatedNbrePoste > 0) {
             System.out.println("Mise à jour des NbresPostes réussie : " + rowsUpdatedNbrePoste + " lignes modifiées.");
         }
         
         String sqlSpecialite = "UPDATE dataset SET Spécialité = SecteurActivite WHERE Spécialité IS NULL";
         PreparedStatement UpSpecialite = connection.prepareStatement(sqlSpecialite);
         int rowsUpdatesqlSpecialite = UpSpecialite.executeUpdate();
         if (rowsUpdatesqlSpecialite  > 0) {
             System.out.println("Mise à jour des Specialite réussie : " + rowsUpdatesqlSpecialite  + " lignes modifiées.");
         }
         
      //// Étape 1 : Sélectionnez les lignes où EntrepriseSite est NULL
         String sqlSelect = "SELECT id, EntrepriseName FROM dataset WHERE EntrepriseSite IS NULL";
         PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
         ResultSet resultSet2 = selectStmt.executeQuery();

         // Étape 2 : Parcourez les résultats et mettez à jour EntrepriseSite
         String sqlUpdate = "UPDATE dataset SET EntrepriseSite = ? WHERE id = ?";
         PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate);

         int rowsUpdated = 0;

         while (resultSet2.next()) {
             int id = resultSet2.getInt("id");
             String entrepriseName = resultSet2.getString("EntrepriseName");

             // Vérifiez la valeur de EntrepriseName avant de continuer
             if (entrepriseName != null) {
                 String newEntrepriseSite = entrepriseName + ".com"; // Concaténation en Java

                 // Debugging : Affichez la valeur générée
                 System.out.println("ID: " + id + ", EntrepriseSite généré: " + newEntrepriseSite);

                 // Affectation et exécution
                 updateStmt.setString(1, newEntrepriseSite);
                 updateStmt.setInt(2, id);
                 rowsUpdated += updateStmt.executeUpdate();
             } else {
                 System.out.println("ID: " + id + " a une valeur EntrepriseName NULL !");
             }
         }

         // Étape 3 : Affichez le résultat final
         if (rowsUpdated > 0) {
             System.out.println("Mise à jour de siteEntreprise réussie : " + rowsUpdated + " lignes modifiées.");
         } else {
             System.out.println("Aucune ligne mise à jour.");
         }

        
		String sqlAdresseEntreprise = "UPDATE dataset " +
		                "SET EntreprisAdress = Ville " +
		                "WHERE 	EntreprisAdress IS NULL";
		PreparedStatement UpAdresseEntreprise = connection.prepareStatement(sqlAdresseEntreprise);
		int rowsUpdatedAdresseEntreprise = UpAdresseEntreprise.executeUpdate();
		if (rowsUpdatedAdresseEntreprise > 0) {
		System.out.println("Mise à jour de adresseEntreprise réussie : " + rowsUpdatedAdresseEntreprise + " lignes modifiées.");
		}
        
		
         
	    } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	 }

}
