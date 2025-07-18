import java.util.ArrayList;
import java.util.List;

public class ServiceNotification {
    private static ServiceNotification instance;
    private List<Observateur> observateurs;

    private ServiceNotification() {
        observateurs = new ArrayList<>();
    }

    public static ServiceNotification getInstance() {
        if (instance == null) {
            instance = new ServiceNotification();
        }
        return instance;
    }

    public void sAbonner(Observateur o) {
        if (o != null && !observateurs.contains(o)) {
            observateurs.add(o);
            System.out.println("Observateur abonné : " + o.getClass().getSimpleName());
        }
    }

    public void seDesabonner(Observateur o) {
        observateurs.remove(o);
        System.out.println("Observateur désabonné : " + o.getClass().getSimpleName());
    }

    public void notifier(String message) {
        for (Observateur o : observateurs) {
            o.mettreAJour(message);
        }
    }
}