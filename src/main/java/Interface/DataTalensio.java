package Interface;

public class DataTalensio {

    private static String[] columns = {"id", "sitename", "publié", "ville", "métier", "contrat"};

    public DataTalensio() {
		super();
	}



	int id;
    String sitename;
    String publié;
    String ville;
    String métier;
    String contrat;
    

    public DataTalensio(int id, String sitename, String publié, String ville, String métier, String contrat) {
        this.id = id;
        this.sitename = sitename;
        this.publié = publié;
        this.ville = ville;
        this.métier = métier;
  
        this.contrat = contrat;

    }

  

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getPublié() {
        return publié;
    }

    public void setPublié(String publié) {
        this.publié = publié;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMétier() {
        return métier;
    }

    public void setMétier(String métier) {
        this.métier = métier;
    }

  
    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }



	@Override
	public String toString() {
		return "DataTalensio [id=" + id + ", sitename=" + sitename + ", publié=" + publié + ", ville=" + ville
				+ ", métier=" + métier + ", contrat=" + contrat + "]";
	}

  
 
}
