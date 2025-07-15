public class ArticlePanier {
    private Produit produit;
    private int quantite;

    public ArticlePanier(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public double calculerSousTotal() {
        return produit.getPrix() * quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }
}
