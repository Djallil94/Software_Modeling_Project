public class NotificationEmail implements Observateur {
    @Override
    public void mettreAJour(String message) {
        System.out.println("Email : " + message);
    }
}
