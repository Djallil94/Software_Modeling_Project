public abstract class Utilisateur {
    protected String id;
    protected String nom;
    protected String email;
    protected String motDePasse;

    public abstract void afficherProfil();
}