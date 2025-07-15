public class PaiementPayPal implements MethodePaiement {
    @Override
    public void payer(double montant) {
        System.out.println("Payé " + montant + " € via PayPal.");
    }
}
