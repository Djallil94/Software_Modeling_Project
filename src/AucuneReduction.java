// AucuneReduction.java
public class AucuneReduction implements StrategieReduction {
    @Override
    public double appliquerReduction(double montantTotal) {
        return montantTotal; // Aucune réduction, retourne le montant tel quel
    }
}