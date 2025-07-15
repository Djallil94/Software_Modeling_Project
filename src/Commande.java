import java.util.*;

public class Commande {
    private static int compteur = 0;
    private String idCommande;
    private List<ArticlePanier> articles;
    private double total;
    private StatutCommande statut;

    public Commande(List<ArticlePanier> articles) {
        this.idCommande = "CMD" + (++compteur);
        this.articles = new ArrayList<>(articles);
        this.total = articles.stream().mapToDouble(ArticlePanier::calculerSousTotal).sum();
        this.statut = StatutCommande.EN_ATTENTE;
    }

    public void changerStatut(StatutCommande statut) {
        this.statut = statut;
        ServiceNotification.getInstance().notifier("Commande " + idCommande + " mise à jour : " + statut);
    }

    public double getTotal() {
        return total;
    }
}
