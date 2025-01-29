package Interface;

public class Data_OffresEmploi {
    public int id;
    public String sitename;
    public String Postname;
    public String entrepriseName;
    public String DateDePublication;
    public String Ville;
    public String Postuler;
    public String posteDescription;

    public Data_OffresEmploi(int id, String sitename, String postname, String entrepriseName, String dateDePublication, String ville, String postuler, String posteDescription) {
        this.id = id;
        this.sitename = sitename;
        Postname = postname;
        this.entrepriseName = entrepriseName;
        DateDePublication = dateDePublication;
        Ville = ville;
        Postuler = postuler;
        this.posteDescription = posteDescription;
    }
}
