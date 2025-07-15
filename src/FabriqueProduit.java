public class FabriqueProduit {
    public static Produit creerProduit(String sku, String nom, String description, double prix, int stock, String categorie) {
        return new Produit(sku, nom, description, prix, stock, categorie);
    }
}