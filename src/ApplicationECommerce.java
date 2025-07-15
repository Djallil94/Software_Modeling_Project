import java.util.*;

public class ApplicationECommerce {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ServiceNotification.getInstance().sAbonner(new NotificationEmail());

        Produit p1 = FabriqueProduit.creerProduit("001", "Livre Java", "Guide complet", 39.99, 10, "Livres");
        Produit p2 = FabriqueProduit.creerProduit("002", "Clé USB", "16Go", 12.50, 5, "Électronique");
        GestionStock.getInstance().ajouterProduit(p1);
        GestionStock.getInstance().ajouterProduit(p2);

        System.out.println("Bienvenue sur la plateforme E-Commerce !");
        System.out.println("Vous êtes : \n1. Client\n2. Administrateur");
        int choixRole = Integer.parseInt(scanner.nextLine());

        if (choixRole == 1) {
            menuClient();
        } else if (choixRole == 2) {
            menuAdmin();
        } else {
            System.out.println("Choix invalide.");
        }
    }

    public static void menuClient() {
        Client client = new Client("C001", "Utilisateur", "client@mail.com");
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== Menu Client ===");
            System.out.println("1. Voir les produits");
            System.out.println("2. Ajouter un produit au panier");
            System.out.println("3. Voir le panier");
            System.out.println("4. Passer commande");
            System.out.println("5. Quitter");

            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    afficherProduits();
                    break;
                case 2:
                    System.out.print("Nom du produit à ajouter : ");
                    String nomProduit = scanner.nextLine();
                    Produit produit = GestionStock.getInstance().getProduit(nomProduit);
                    if (produit != null) {
                        System.out.print("Quantité : ");
                        int qte = Integer.parseInt(scanner.nextLine());
                        client.getPanier().ajouterProduit(produit, qte);
                    } else {
                        System.out.println("Produit non trouvé.");
                    }
                    break;
                case 3:
                    afficherPanier(client);
                    break;
                case 4:
                    passerCommande(client);
                    break;
                case 5:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void menuAdmin() {
        Administrateur admin = new Administrateur();
        admin.nom = "Admin";

        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== Menu Administrateur ===");
            System.out.println("1. Voir produits");
            System.out.println("2. Ajouter produit");
            System.out.println("3. Modifier stock");
            System.out.println("4. Quitter");

            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    afficherProduits();
                    break;
                case 2:
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Description : ");
                    String desc = scanner.nextLine();
                    System.out.print("Prix : ");
                    double prix = Double.parseDouble(scanner.nextLine());
                    System.out.print("Stock : ");
                    int stock = Integer.parseInt(scanner.nextLine());
                    System.out.print("Catégorie : ");
                    String cat = scanner.nextLine();
                    Produit p = FabriqueProduit.creerProduit(UUID.randomUUID().toString(), nom, desc, prix, stock, cat);
                    GestionStock.getInstance().ajouterProduit(p);
                    System.out.println("Produit ajouté.");
                    break;
                case 3:
                    System.out.print("Nom du produit : ");
                    String nomMod = scanner.nextLine();
                    Produit produit = GestionStock.getInstance().getProduit(nomMod);
                    if (produit != null) {
                        System.out.print("Quantité à ajouter : ");
                        int qte = Integer.parseInt(scanner.nextLine());
                        admin.mettreAJourStock(produit, qte);
                        System.out.println("Stock mis à jour.");
                    } else {
                        System.out.println("Produit introuvable.");
                    }
                    break;
                case 4:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void passerCommande(Client client) {
        if (client.getPanier().getItems().isEmpty()) {
            System.out.println("Votre panier est vide !");
            return;
        }

        List<ArticlePanier> articles = client.getPanier().getItems();

        // ✅ Vérification du stock avant de passer la commande
        for (ArticlePanier article : articles) {
            Produit produit = article.getProduit();
            int quantite = article.getQuantite();

            if (produit.getStock() < quantite) {
                System.out.println("Stock insuffisant pour le produit : " + produit.getNom());
                System.out.println("Commande annulée.");
                return;
            }
        }

        // ✅ Création de la commande
        Commande commande = new Commande(articles);

        System.out.println("Total : " + commande.getTotal() + " €");
        System.out.println("Choisissez un mode de paiement :");
        System.out.println("1. Carte bancaire\n2. PayPal\n3. Portefeuille");

        int choix = Integer.parseInt(scanner.nextLine());
        MethodePaiement paiement;

        switch (choix) {
            case 1 -> paiement = new PaiementCarte();
            case 2 -> paiement = new PaiementPayPal();
            case 3 -> paiement = new PaiementPortefeuille();
            default -> {
                System.out.println("Mode de paiement invalide. Paiement par carte par défaut.");
                paiement = new PaiementCarte();
            }
        }

        paiement.payer(commande.getTotal());

        // Réduction du stock après confirmation
        for (ArticlePanier article : articles) {
            article.getProduit().reduireStock(article.getQuantite());
        }

        commande.changerStatut(StatutCommande.EXPEDIEE);
        client.getPanier().viderPanier();
        System.out.println("Commande validée et envoyée !");
    }


    public static void afficherProduits() {
        System.out.println("\n--- Produits disponibles ---");
        GestionStock.getInstance().getProduitMap().values().forEach(p ->
                System.out.println(p.getNom() + " | " + p.getPrix() + "€ | Stock: " + p.getStock())
        );
    }

    public static void afficherPanier(Client client) {
        System.out.println("\n--- Votre panier ---");
        for (ArticlePanier item : client.getPanier().getArticles()) {
            System.out.println(item.getProduit().getNom() + " x" + item.getQuantite() +
                    " = " + item.calculerSousTotal() + " €");
        }
        System.out.println("Total : " + client.getPanier().calculerTotal() + " €");
    }
}
