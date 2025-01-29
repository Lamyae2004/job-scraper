package Interface;

public  class data_emploi {

    public String sitename;
    public data_emploi() {
        super();
    }

    public String Postname;
    public String description;
    public String DateDePublication;
    public String Entreprise;
    public String region;
    public String Competence;
    public String Type_de_contrat;
    public String experience;
    public String niveau_etude;

    public String Postuler;

    public data_emploi( String sitename, String Postname, String description, String DateDePublication,
                        String Entreprise, String region, String Competence,
                        String Type_de_contrat,String experience, String niveau_etude,
                        String Postuler) {

        this.sitename = sitename;
        this.Postname = Postname;
        this.description = description;
        this.DateDePublication = DateDePublication;
        this.Entreprise = Entreprise;
        this.region = region;
        this.Competence = Competence;
        this.Type_de_contrat = Type_de_contrat;
        this.experience = experience;
        this.niveau_etude = niveau_etude;
        this.Postuler = Postuler;
    }
}