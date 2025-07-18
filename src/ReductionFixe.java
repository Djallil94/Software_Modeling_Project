public class ReductionFixe implements StrategieReduction {
    private double montantReduction;

    public ReductionFixe(double montantReduction) {
        if (montantReduction < 0) {
            throw new IllegalArgumentException("Le montant de réduction fixe ne peut pas être négatif.");
        }
        this.montantReduction = montantReduction;
    }

    @Override
    public double appliquerReduction(double montantTotal) {
        double montantApresReduction = montantTotal - montantReduction;
        // Assurez-vous que le total ne devienne pas négatif
        return Math.max(0, montantApresReduction);
    }
}