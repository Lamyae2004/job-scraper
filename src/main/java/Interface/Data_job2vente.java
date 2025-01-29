package Interface;

public class Data_job2vente {
	 int id;
     String sitename;
     String Postname;
     String DateDePublication;
     String ville;
     String Type_de_contrat;
     String secteur;
    
	 String posteDescription;
     String Postuler;
     public Data_job2vente(int id, String sitename, String postname, String dateDePublication, String ville,
 			String type_de_contrat, String secteur, String posteDescription, String postuler) {
 		super();
 		this.id = id;
 		this.sitename = sitename;
 		Postname = postname;
 		DateDePublication = dateDePublication;
 		this.ville = ville;
 		Type_de_contrat = type_de_contrat;
 		this.secteur = secteur;
 		this.posteDescription = posteDescription;
 		Postuler = postuler;
 	}
     

}
