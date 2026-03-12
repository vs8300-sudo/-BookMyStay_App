import java.util.*;

/**
 * CLASS - AddOnService
 * Represents an optional service.
 */
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * CLASS - AddOnServiceManager
 * Manages services linked to reservations.
 */
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services =
                reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }
}

/**
 * MAIN CLASS
 */
public class BookMyStay{

    public static void main(String[] args) {

        String reservationId = "Single-1";

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example services
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 1000));

        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}