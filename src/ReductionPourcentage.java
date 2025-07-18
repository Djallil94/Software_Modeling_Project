public class ReductionPourcentage implements StrategieReduction {
    private double pourcentage; // Ex: 0.10 pour 10%

    public ReductionPourcentage(double pourcentage) {
        if (pourcentage < 0 || pourcentage > 1) {
            throw new IllegalArgumentException("Le pourcentage de réduction doit être entre 0 et 1 (ex: 0.10 pour 10%).");
        }
        this.pourcentage = pourcentage;
    }

    @Override
    public double appliquerReduction(double montantTotal) {
        double montantApresReduction = montantTotal * (1 - pourcentage);
        return Math.max(0, montantApresReduction);
    }
}