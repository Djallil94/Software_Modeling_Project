public class Administrateur extends Utilisateur {

    public Administrateur() {
        super("ADMIN001", "AdminPrincipal", "admin@ecommerce.com", "adminpass");
    }

    @Override
    public void afficherProfil() {
        System.out.println("--- Profil Administrateur ---");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Email: " + email);
        System.out.println("-----------------------------");
    }

    public void mettreAJourStock(Produit produit, int quantite) {
        GestionStock.getInstance().mettreAJourStock(produit, quantite);
    }
}