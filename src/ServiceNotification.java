import java.util.*;

public class ServiceNotification {
    private static ServiceNotification instance;
    private List<Observateur> observateurs = new ArrayList<>();

    private ServiceNotification() {}

    public static ServiceNotification getInstance() {
        if (instance == null) {
            instance = new ServiceNotification();
        }
        return instance;
    }

    public void sAbonner(Observateur o) {
        observateurs.add(o);
    }

    public void notifier(String message) {
        for (Observateur o : observateurs) {
            o.mettreAJour(message);
        }
    }
}
