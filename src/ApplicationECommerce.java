// ApplicationECommerce.java
import java.util.Scanner;

public class ApplicationECommerce {
    private static Scanner scanner = new Scanner(System.in);
    private static CommandeService commandeService = new CommandeService();

    public static void main(String[] args) {
        ServiceNotification.getInstance().sAbonner(new NotificationEmail());

        GestionStock.getInstance().ajouterProduit(FabriqueProduit.creerProduit("001", "Livre Java", "Guide complet", 39.99, 10, "Livres"));
        GestionStock.getInstance().ajouterProduit(FabriqueProduit.creerProduit("002", "Clé USB", "16Go", 12.50, 5, "Électronique"));
        GestionStock.getInstance().ajouterProduit(FabriqueProduit.creerProduit("003", "Casque Audio", "Haute fidélité", 99.99, 3, "Électronique"));

        System.out.println("Bienvenue sur la plateforme E-Commerce !");
        int choixRole = -1;
        while (choixRole == -1) {
            System.out.println("Vous êtes : \n1. Client\n2. Administrateur\n3. Quitter");
            try {
                choixRole = Integer.parseInt(scanner.nextLine());
                if (choixRole < 1 || choixRole > 3) {
                    System.out.println("Choix invalide. Veuillez entrer 1, 2 ou 3.");
                    choixRole = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                choixRole = -1;
            }
        }

        switch (choixRole) {
            case 1:
                menuClient();
                break;
            case 2:
                menuAdmin();
                break;
            case 3:
                System.out.println("Merci d'avoir utilisé notre plateforme. Au revoir !");
                break;
        }
        scanner.close();
    }

    public static void menuClient() {
        Client client = new Client("C001", "Utilisateur Test", "client@mail.com");
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== Menu Client ===");
            System.out.println("1. Voir les produits");
            System.out.println("2. Ajouter un produit au panier");
            System.out.println("3. Voir le panier");
            System.out.println("4. Passer commande");
            System.out.println("5. Quitter");

            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    afficherProduits();
                    break;
                case 2:
                    System.out.print("Nom du produit à ajouter : ");
                    String nomProduit = scanner.nextLine();
                    Produit produit = GestionStock.getInstance().getProduit(nomProduit);
                    if (produit != null) {
                        int qte = lireEntier("Quantité : ");
                        if (qte > 0) {
                            client.getPanier().ajouterProduit(produit, qte);
                        } else {
                            System.out.println("La quantité doit être supérieure à zéro.");
                        }
                    } else {
                        System.out.println("Produit non trouvé.");
                    }
                    break;
                case 3:
                    afficherPanier(client);
                    break;
                case 4:
                    if (client.getPanier().estVide()) {
                        System.out.println("Votre panier est vide ! Impossible de passer commande.");
                        break;
                    }

                    System.out.println("\n--- Choisir une réduction ---");
                    System.out.println("1. Aucune réduction");
                    System.out.println("2. Réduction fixe (ex: 10€)");
                    System.out.println("3. Réduction en pourcentage (ex: 10%)");
                    int choixReduction = lireEntier("Votre choix de réduction: ");

                    StrategieReduction strategieReduction;
                    switch (choixReduction) {
                        case 1:
                            strategieReduction = new AucuneReduction();
                            System.out.println("Aucune réduction appliquée.");
                            break;
                        case 2:
                            double montantFixe = lireDouble("Montant de la réduction fixe (ex: 10.0) : ");
                            strategieReduction = new ReductionFixe(montantFixe);
                            System.out.println("Réduction fixe de " + String.format("%.2f", montantFixe) + "€ appliquée.");
                            break;
                        case 3:
                            double pourcentage = lireDouble("Pourcentage de réduction (ex: 0.10 pour 10%) : ");
                            strategieReduction = new ReductionPourcentage(pourcentage);
                            System.out.println("Réduction de " + (pourcentage * 100) + "% appliquée.");
                            break;
                        default:
                            System.out.println("Choix de réduction invalide. Aucune réduction appliquée par défaut.");
                            strategieReduction = new AucuneReduction();
                            break;
                    }

                    System.out.println("\n--- Choisir un mode de paiement ---");
                    System.out.println("1. Carte bancaire\n2. PayPal\n3. Portefeuille");
                    int choixPaiement = lireEntier("Votre choix de paiement: ");

                    MethodePaiement methodePaiement;
                    switch (choixPaiement) {
                        case 1 -> methodePaiement = new PaiementCarte();
                        case 2 -> methodePaiement = new PaiementPayPal();
                        case 3 -> methodePaiement = new PaiementPortefeuille();
                        default -> {
                            System.out.println("Mode de paiement invalide. Paiement par carte bancaire sélectionné par défaut.");
                            methodePaiement = new PaiementCarte();
                        }
                    }

                    commandeService.passerCommande(client, methodePaiement, strategieReduction); // Passer la stratégie de réduction
                    break;
                case 5:
                    continuer = false;
                    System.out.println("Au revoir, Client !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    public static void menuAdmin() {
        Administrateur admin = new Administrateur();

        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== Menu Administrateur ===");
            System.out.println("1. Voir produits");
            System.out.println("2. Ajouter produit");
            System.out.println("3. Modifier stock");
            System.out.println("4. Quitter");

            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    afficherProduits();
                    break;
                case 2:
                    System.out.print("SKU (identifiant unique) : ");
                    String sku = scanner.nextLine();
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Description : ");
                    String desc = scanner.nextLine();
                    double prix = lireDouble("Prix : ");
                    int stock = lireEntier("Stock initial : ");
                    System.out.print("Catégorie : ");
                    String cat = scanner.nextLine();
                    Produit p = FabriqueProduit.creerProduit(sku, nom, desc, prix, stock, cat);
                    GestionStock.getInstance().ajouterProduit(p);
                    System.out.println("Produit ajouté avec succès.");
                    break;
                case 3:
                    System.out.print("Nom du produit à modifier : ");
                    String nomMod = scanner.nextLine();
                    Produit produit = GestionStock.getInstance().getProduit(nomMod);
                    if (produit != null) {
                        int qteAjustement = lireEntier("Quantité à ajuster (ex: 5 pour ajouter, -2 pour retirer) : ");
                        admin.mettreAJourStock(produit, qteAjustement);
                    } else {
                        System.out.println("Produit introuvable.");
                    }
                    break;
                case 4:
                    continuer = false;
                    System.out.println("Au revoir, Admin !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    public static void afficherProduits() {
        System.out.println("\n--- Produits disponibles ---");
        if (GestionStock.getInstance().getProduitMap().isEmpty()) {
            System.out.println("Aucun produit en stock pour le moment.");
            return;
        }
        GestionStock.getInstance().getProduitMap().values().forEach(p ->
                System.out.println("SKU: " + p.getSku() + " | Nom: " + p.getNom() + " | Prix: " + String.format("%.2f", p.getPrix()) + "€ | Stock: " + p.getStock() + " | Catégorie: " + p.getCategorie())
        );
    }

    public static void afficherPanier(Client client) {
        System.out.println("\n--- Votre panier ---");
        if (client.getPanier().estVide()) {
            System.out.println("Panier vide.");
            return;
        }
        for (ArticlePanier item : client.getPanier().getArticles()) {
            System.out.println(item.getProduit().getNom() + " (SKU: " + item.getProduit().getSku() + ") x" + item.getQuantite() +
                    " = " + String.format("%.2f", item.calculerSousTotal()) + " €");
        }
        System.out.println("Total du panier : " + String.format("%.2f", client.getPanier().calculerTotal()) + " €");
    }

    private static int lireEntier(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
            }
        }
    }

    private static double lireDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre décimal.");
            }
        }
    }
}