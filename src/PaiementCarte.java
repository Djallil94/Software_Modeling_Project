public class PaiementCarte implements MethodePaiement {
    @Override
    public void payer(double montant) {
        System.out.println("Payé " + montant + " € par carte bancaire.");
    }
}
