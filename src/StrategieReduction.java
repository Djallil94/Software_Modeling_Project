public interface StrategieReduction {
    /**
     * Applique une réduction sur le montant total donné.
     *
     * @param montantTotal Le montant initial avant réduction.
     * @return Le montant après application de la réduction.
     */
    double appliquerReduction(double montantTotal);
}