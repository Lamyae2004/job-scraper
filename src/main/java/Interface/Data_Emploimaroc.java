package Interface;

class Data_Emploimaroc {
    public Data_Emploimaroc() {
		super();
	}

	int id;
    String sitename;
    String Postname;
    String posteDescription;
    String profileDescription;
    String entrepriseName;
    String ville;
    String secteur;
    String hard_skills;
    String DateDePublication;
    String DateLimitePourPostuler;
    String Type_de_contrat;
    String Postuler;

    public Data_Emploimaroc(int id, String sitename, String postname, String posteDescription, String profileDescription, String entrepriseName, String ville, String secteur, String hard_skills, String dateDePublication, String dateLimitePourPostuler, String type_de_contrat, String postuler) {
        this.id = id;
        this.sitename = sitename;
        Postname = postname;
        this.posteDescription = posteDescription;
        this.profileDescription = profileDescription;
        this.entrepriseName = entrepriseName;
        this.ville = ville;
        this.secteur = secteur;
        this.hard_skills = hard_skills;
        DateDePublication = dateDePublication;
        DateLimitePourPostuler = dateLimitePourPostuler;
        Type_de_contrat = type_de_contrat;
        Postuler = postuler;
    }
}