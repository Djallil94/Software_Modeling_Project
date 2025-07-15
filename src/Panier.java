import java.util.*;

public class Panier {
    private List<ArticlePanier> articles = new ArrayList<>();

    public void ajouterProduit(Produit produit, int quantite) {
        articles.add(new ArticlePanier(produit, quantite));
        ServiceNotification.getInstance().notifier("Produit ajouté au panier : " + produit.getNom());
    }

    public double calculerTotal() {
        return articles.stream().mapToDouble(ArticlePanier::calculerSousTotal).sum();
    }

    public void viderPanier() {
        articles.clear();
    }

    public List<ArticlePanier> getItems() {
        return articles;
    }

    public List<ArticlePanier> getArticles() {
        return articles;
    }
}
