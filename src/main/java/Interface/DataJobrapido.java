package Interface;

public class DataJobrapido {

    private static String[] columns = {"id", "sitename", "postname", "entrepriseName", "region", "Postuler"};

     int id;
     String sitename;
     String postname;
     String entrepriseName;
     String region;
     String postuler;

    public DataJobrapido(int id, String sitename, String postname, String entrepriseName, String region, String postuler) {
        this.id = id;
        this.sitename = sitename;
        this.postname = postname;
        this.entrepriseName = entrepriseName;
        this.region = region;
        this.postuler = postuler;
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

    public String getPostuler() {
        return postuler;
    }

    public void setPostuler(String postuler) {
        this.postuler = postuler;
    }

    @Override
    public String toString() {
        return "DataJobrapido{" +
               "id=" + id +
               ", sitename='" + sitename + '\'' +
               ", postname='" + postname + '\'' +
               ", entrepriseName='" + entrepriseName + '\'' +
               ", region='" + region + '\'' +
               ", postuler='" + postuler + '\'' +
               '}';
    }
}
