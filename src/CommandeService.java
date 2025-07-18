import java.util.List;

public class CommandeService {
    private ServiceNotification notificationService;
    private GestionStock gestionStock;

    public CommandeService() {
        this.notificationService = ServiceNotification.getInstance();
        this.gestionStock = GestionStock.getInstance();
    }

    public void passerCommande(Client client, MethodePaiement methodePaiement, StrategieReduction strategieReduction) {
        if (client == null || methodePaiement == null || strategieReduction == null) {
            System.out.println("Erreur: Client, méthode de paiement ou stratégie de réduction invalide.");
            return;
        }

        List<ArticlePanier> articlesPanier = client.getPanier().getArticles();

        if (articlesPanier.isEmpty()) {
            System.out.println("Le panier est vide. Impossible de passer commande.");
            return;
        }

        // 1. Vérification du stock
        if (!verifierStock(articlesPanier)) {
            notificationService.notifier("Commande annulée pour " + client.getNom() + " : stock insuffisant.");
            return;
        }

        // Calcul du total avant réduction
        double totalInitial = client.getPanier().calculerTotal();

        // 2. Application de la réduction (Polymorphisme & DIP)
        double totalApresReduction = strategieReduction.appliquerReduction(totalInitial);
        System.out.println("Montant initial : " + String.format("%.2f", totalInitial) + " €");
        System.out.println("Montant après réduction : " + String.format("%.2f", totalApresReduction) + " €");


        // 3. Exécution du paiement
        try {
            methodePaiement.payer(totalApresReduction);
            System.out.println("Paiement réussi pour la commande.");
        } catch (Exception e) {
            System.out.println("Erreur lors du paiement : " + e.getMessage());
            notificationService.notifier("Paiement échoué pour la commande du client " + client.getNom());
            return;
        }

        // 4. Création de la commande avec le total final (Creator GRASP)
        Commande commande = new Commande(articlesPanier, totalApresReduction); // Passer le total final

        // 5. Réduction du stock
        reduireStockArticles(articlesPanier);

        // 6. Mise à jour du statut de la commande et notification
        commande.changerStatut(StatutCommande.EXPEDIEE);
        notificationService.notifier("Commande " + commande.getIdCommande() + " du client " + client.getNom() + " a été expédiée.");

        // 7. Vider le panier après validation
        client.getPanier().viderPanier();
        System.out.println("Commande validée et envoyée avec succès !");
    }

    private boolean verifierStock(List<ArticlePanier> articles) {
        for (ArticlePanier article : articles) {
            Produit produit = gestionStock.getProduit(article.getProduit().getNom());
            if (produit == null || produit.getStock() < article.getQuantite()) {
                System.out.println("Stock insuffisant pour le produit : " + article.getProduit().getNom() +
                        ". Disponible: " + (produit != null ? produit.getStock() : "N/A") +
                        ", Demandé: " + article.getQuantite());
                return false;
            }
        }
        return true;
    }

    private void reduireStockArticles(List<ArticlePanier> articles) {
        for (ArticlePanier article : articles) {
            gestionStock.mettreAJourStock(article.getProduit(), -article.getQuantite());
        }
    }
}