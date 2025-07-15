public class Client extends Utilisateur {
    private Panier panier;

    public Client(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.panier = new Panier();
    }

    public Panier getPanier() {
        return panier;
    }

    @Override
    public void afficherProfil() {
        System.out.println("Client : " + nom + ", Email : " + email);
    }
}
