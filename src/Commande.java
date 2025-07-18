import java.util.ArrayList;
import java.util.List;

public class Commande {
    private static int compteur = 0;
    private String idCommande;
    private List<ArticlePanier> articles;
    private double total; // Le total peut être le total après réduction
    private StatutCommande statut;

    public Commande(List<ArticlePanier> articles, double totalFinal) { // Constructeur modifié pour prendre le total final
        this.idCommande = "CMD" + String.format("%04d", ++compteur);
        this.articles = new ArrayList<>(articles);
        this.total = totalFinal; // Utilise le total déjà calculé avec la réduction
        this.statut = StatutCommande.EN_ATTENTE;
    }

    public void changerStatut(StatutCommande nouveauStatut) {
        if (this.statut != nouveauStatut) {
            this.statut = nouveauStatut;
            System.out.println("Statut de la commande " + idCommande + " changé à : " + nouveauStatut);
        }
    }

    public double getTotal() {
        return total;
    }

    public List<ArticlePanier> getArticles() {
        return new ArrayList<>(articles);
    }

    public String getIdCommande() {
        return idCommande;
    }

    public StatutCommande getStatut() {
        return statut;
    }
}