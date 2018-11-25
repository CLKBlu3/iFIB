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
}
