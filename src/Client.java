public class Client extends Utilisateur {
    private Panier panier;

    public Client(String id, String nom, String email) {
        super(id, nom, email, "default_password"); // Mot de passe par défaut pour simplifier
        this.panier = new Panier();
    }

    @Override
    public void afficherProfil() {
        System.out.println("--- Profil Client ---");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Email: " + email);
        System.out.println("---------------------");
    }

    public Panier getPanier() {
        return panier;
    }
}