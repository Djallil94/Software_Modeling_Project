import java.util.HashMap;
import java.util.Map;

public class GestionStock {
    private static GestionStock instance;
    private Map<String, Produit> produits; // Utilisation du nom du produit comme clé pour la simplicité d'accès via l'UI

    private GestionStock() {
        produits = new HashMap<>();
    }

    public static GestionStock getInstance() {
        if (instance == null) {
            instance = new GestionStock();
        }
        return instance;
    }

    public void ajouterProduit(Produit produit) {
        if (!produits.containsKey(produit.getNom())) {
            produits.put(produit.getNom(), produit);
        } else {
            System.out.println("Le produit avec le nom '" + produit.getNom() + "' existe déjà. Utilisez 'mettreAJourStock' pour modifier.");
        }
    }

    public void mettreAJourStock(Produit produit, int quantityDelta) {
        Produit p = produits.get(produit.getNom());
        if (p != null) {
            if (quantityDelta > 0) {
                p.augmenterStock(quantityDelta);
                System.out.println("Stock de " + p.getNom() + " augmenté de " + quantityDelta + ". Nouveau stock: " + p.getStock());
            } else if (quantityDelta < 0) {
                p.reduireStock(Math.abs(quantityDelta));
                System.out.println("Stock de " + p.getNom() + " réduit de " + Math.abs(quantityDelta) + ". Nouveau stock: " + p.getStock());
            } else {
                System.out.println("Aucun changement de stock pour " + p.getNom());
            }
        } else {
            System.out.println("Produit " + produit.getNom() + " introuvable pour la mise à jour du stock.");
        }
    }

    public Produit getProduit(String nom) {
        return produits.get(nom);
    }

    public Map<String, Produit> getProduitMap() {
        return produits;
    }
}