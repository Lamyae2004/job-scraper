package Interface;

public class Data_rekrute {
	 public Data_rekrute() {
		super();
	}

	int id;
     String sitename;
     String Postname;
     String DateDePublication;
     String DateLimitePourPostuler;
     String entrepriseName;
     String EntrepriseDes;
     String addressEntreprise;
     String region;
     String Type_de_contrat;
     String secteur;
     String Niveau_etude;
     String experience;
     String langue;
     String nombrePostes;
     String posteDescription;
     String profilRecherche;
     String Télétravail; // Added Teletravail attribute
     String Postuler;
     String TraitePersonaliter;
     String HardSkills ;

     public Data_rekrute(int id, String sitename, String Postname, String DateDePublication, String DateLimitePourPostuler,
                    String entrepriseName, String EntrepriseDes, String addressEntreprise, String region,
                    String Type_de_contrat, String secteur, String Niveau_etude, String experience,
                    String langue, String nombrePostes, String posteDescription,
                    String profilRecherche, String Télétravail, String Postuler, String TraitePersonaliter,String HardSkills) {
         this.id = id;
         this.sitename = sitename;
         this.Postname = Postname;
         this.DateDePublication = DateDePublication;
         this.DateLimitePourPostuler = DateLimitePourPostuler;
         this.entrepriseName = entrepriseName;
         this.EntrepriseDes = EntrepriseDes;
         this.addressEntreprise = addressEntreprise;
         this.region = region;
         this.Type_de_contrat = Type_de_contrat;
         this.secteur = secteur;
         this.Niveau_etude = Niveau_etude;
         this.experience = experience;
         this.langue = langue;
         this.nombrePostes = nombrePostes;
         this.posteDescription = posteDescription;
         this.profilRecherche = profilRecherche;
         this.Télétravail = Télétravail;  
         this.Postuler = Postuler;
         this.TraitePersonaliter = TraitePersonaliter;
         this.HardSkills = HardSkills;
     }
}
