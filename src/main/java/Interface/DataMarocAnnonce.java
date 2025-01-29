package Interface;

public class DataMarocAnnonce {

    // Déclaration du tableau contenant les noms des colonnes
    private static String[] columns = {
        "id","sitename", "Postname", "entrepriseName", "region", 
        "Niveau_etude", "posteDescription", "Salaire"
    };

    // Déclaration des variables d'instance correspondant à chaque colonne
     int id;
     String sitename;
     String postname;
     String entrepriseName;
     String region;
     String niveauEtude;
     String posteDescription;
     String salaire;
     String DateDePublication;
     String Postuler;
    


    // Constructeur pour initialiser un objet DataMarocAnnonce
    public DataMarocAnnonce(int id, String sitename,String postname, String entrepriseName, String region, 
                            String niveauEtude, String posteDescription, String salaire,String DateDePublication,String Postuler) {
        this.id = id;
        this.sitename=sitename;
        this.postname = postname;
        this.entrepriseName = entrepriseName;
        this.region = region;
        this.niveauEtude = niveauEtude;
        this.posteDescription = posteDescription;
        this.salaire = salaire;
        this.DateDePublication=DateDePublication;
        this.Postuler=Postuler;
    }

    // Getters et setters pour chaque champ

    public String getPostuler() {
		return Postuler;
	}

	public void setPostuler(String postuler) {
		Postuler = postuler;
	}

	public String getDateDePublication() {
		return DateDePublication;
	}

	public void setDateDePublication(String dateDePublication) {
		DateDePublication = dateDePublication;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }

    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getPosteDescription() {
        return posteDescription;
    }

    public void setPosteDescription(String posteDescription) {
        this.posteDescription = posteDescription;
    }

    public String getSalaire() {
		return salaire;
	}

	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}

	// Méthode toString pour afficher les informations de l'annonce
    @Override
    public String toString() {
        return "DataMarocAnnonce{" +
               "id=" + id +
               ",sitename=" + sitename +
               ", postname='" + postname + '\'' +
               ", entrepriseName='" + entrepriseName + '\'' +
               ", region='" + region + '\'' +
               ", niveauEtude='" + niveauEtude + '\'' +
               ", posteDescription='" + posteDescription + '\'' +
               ", salaire=" + salaire +
               ", DateDePublication=" + DateDePublication +
               ", Postuler=" + Postuler +
               
               '}';
    }

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
}
