import java.io.*;
import java.util.HashMap;
import java.util.Map;

class RoomInventory implements Serializable {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "inventory.dat";

    public RoomInventory loadInventory() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (RoomInventory) in.readObject();
        } catch (Exception e) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return new RoomInventory();
        }
    }

    public void saveInventory(RoomInventory inventory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(inventory);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save inventory.");
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("System Recovery");

        PersistenceService persistenceService = new PersistenceService();
        RoomInventory inventory = persistenceService.loadInventory();

        System.out.println();
        System.out.println("Current Inventory:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));

        persistenceService.saveInventory(inventory);
    }
}