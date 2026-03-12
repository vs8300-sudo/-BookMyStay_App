import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

public class BookMyStay {

    // Queue for booking requests (FIFO)
    private static Queue<BookingRequest> requestQueue = new LinkedList<>();

    // Inventory of room types
    private static Map<String, Integer> inventory = new HashMap<>();

    // Map of room type -> allocated room IDs
    private static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Set to track all assigned room IDs (global uniqueness)
    private static Set<String> allRoomIds = new HashSet<>();

    public static void main(String[] args) {

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        // Initialize allocated rooms map
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        // Add booking requests to queue
        requestQueue.add(new BookingRequest("Alice", "Single"));
        requestQueue.add(new BookingRequest("Bob", "Double"));
        requestQueue.add(new BookingRequest("Charlie", "Single"));
        requestQueue.add(new BookingRequest("David", "Suite"));
        requestQueue.add(new BookingRequest("Eva", "Suite")); // may fail if no inventory

        processBookings();
    }

    public static void processBookings() {

        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll(); // FIFO
            String roomType = request.roomType;

            System.out.println("\nProcessing request for " + request.customerName + " (" + roomType + ")");

            if (inventory.getOrDefault(roomType, 0) > 0) {

                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                if (!allRoomIds.contains(roomId)) {

                    allRoomIds.add(roomId);
                    allocatedRooms.get(roomType).add(roomId);

                    // Update inventory immediately
                    inventory.put(roomType, inventory.get(roomType) - 1);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Assigned Room ID: " + roomId);

                } else {
                    System.out.println("Error: Duplicate Room ID detected.");
                }

            } else {
                System.out.println("Reservation Failed: No rooms available for " + roomType);
            }
        }

        printSummary();
    }

    // Generate unique room ID
    private static String generateRoomId(String roomType) {
        return roomType.substring(0,1).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    private static void printSummary() {

        System.out.println("\n===== Allocation Summary =====");

        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " Rooms Allocated: " + allocatedRooms.get(type));
        }

        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}