import java.util.*;

public class GestionStock {
    private static GestionStock instance;
    private Map<String, Produit> produits = new HashMap<>();

    private GestionStock() {}

    public static GestionStock getInstance() {
        if (instance == null) {
            instance = new GestionStock();
        }
        return instance;
    }

    public void ajouterProduit(Produit produit) {
        produits.put(produit.getNom(), produit);
    }

    public void mettreAJourStock(Produit produit, int nouveauStock) {
        produits.get(produit.getNom()).reduireStock(-nouveauStock);
    }

    public Produit getProduit(String nom) {
        return produits.get(nom);
    }

    public Map<String, Produit> getProduitMap() {
        return produits;
    }
}
