public class Produit {
    private String sku;
    private String nom;
    private String description;
    private double prix;
    private int stock;
    private String categorie;

    public Produit(String sku, String nom, String description, double prix, int stock, String categorie) {
        this.sku = sku;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.categorie = categorie;
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }
    public void reduireStock(int qte) { this.stock -= qte; }
}
