public class Administrateur extends Utilisateur {
    @Override
    public void afficherProfil() {
        System.out.println("Administrateur : " + nom);
    }

    public void mettreAJourStock(Produit produit, int quantite) {
        GestionStock.getInstance().mettreAJourStock(produit, quantite);
    }
}
