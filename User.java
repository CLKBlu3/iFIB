package marques.ifib;

public class User {
    private String username;
    private String nom;
    private String cognoms;
    private String email;
    private String foto;

    private String assignatures;
    private String avisos;
    private String classes;
    private String practiques;
    private String projectes;

    public String getUsername(){
        return this.username;
    }

    public String getNom(){
        return this.nom;
    }

    public String getCognoms(){
        return this.cognoms;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFoto(){
        return this.foto;
    }

    public String getAssignatures(){
        return this.assignatures;
    }

    public String getAvisos(){
        return this.avisos;
    }

    public String getClasses(){
        return this.classes;
    }

    public String getPractiques(){
        return this.practiques;
    }

    public String getProjectes(){
        return this.projectes;
    }


    public void setUsername(String username){
         this.username = username;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setCognoms(String cognoms){
        this.cognoms = cognoms;
    }

    public void setEmail(String mail){
        this.email = mail;
    }

    public void setFoto(String url){
        this.foto = url;
    }

    public void setAssignatures(String assigs){
        this.assignatures = assigs;
    }

    public void setAvisos(String aviss){
        this.avisos = aviss;
    }

    public void setClasses(String classes){
        this.classes = classes;
    }

    public void setPractiques(String practs){
        this.practiques = practs;
    }

    public void setProjectes(String projs){
        this.projectes = projs;
    }
}
