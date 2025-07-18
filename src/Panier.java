import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<ArticlePanier> articles = new ArrayList<>();

    public void ajouterProduit(Produit produit, int quantite) {
        if (produit == null || quantite <= 0) {
            System.out.println("Impossible d'ajouter un produit invalide ou une quantité nulle/négative au panier.");
            return;
        }

        for (ArticlePanier item : articles) {
            if (item.getProduit().equals(produit)) {
                item.setQuantite(item.getQuantite() + quantite);
                System.out.println("Quantité de " + produit.getNom() + " mise à jour dans le panier.");
                return;
            }
        }
        articles.add(new ArticlePanier(produit, quantite));
        System.out.println(produit.getNom() + " (" + quantite + ") ajouté au panier.");
    }

    public void retirerProduit(Produit produit) {
        articles.removeIf(item -> item.getProduit().equals(produit));
        System.out.println(produit.getNom() + " retiré du panier.");
    }

    public double calculerTotal() {
        return articles.stream().mapToDouble(ArticlePanier::calculerSousTotal).sum();
    }

    public void viderPanier() {
        articles.clear();
        System.out.println("Panier vidé.");
    }

    public List<ArticlePanier> getArticles() {
        return new ArrayList<>(articles); // Retourne une copie pour éviter les modifications externes
    }

    public boolean estVide() {
        return articles.isEmpty();
    }
}