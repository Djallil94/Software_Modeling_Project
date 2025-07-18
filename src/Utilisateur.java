public abstract class Utilisateur {
    protected String id;
    protected String nom;
    protected String email;
    protected String motDePasse;

    public Utilisateur(String id, String nom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public abstract void afficherProfil();
}