import java.util.Objects;

public class Produit {
    private String sku; // Stock Keeping Unit - identifiant unique du produit
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

    public String getSku() { return sku; }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }
    public String getCategorie() { return categorie; }
    public String getDescription() { return description; }

    public void reduireStock(int qte) {
        if (this.stock >= qte) {
            this.stock -= qte;
        } else {
            System.out.println("Attention : Stock insuffisant pour réduire de " + qte + ". Stock actuel : " + this.stock);
        }
    }

    public void augmenterStock(int qte) {
        this.stock += qte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return Objects.equals(sku, produit.sku); // Comparaison par SKU pour l'unicité
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}