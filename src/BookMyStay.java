import java.util.*;

/**
 * ==========================================================
 * CLASS - Reservation
 * ==========================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Represents a guest booking request.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * ==========================================================
 * CLASS - BookingRequestQueue
 * ==========================================================
 * Stores booking requests in FIFO order.
 */
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * ==========================================================
 * CLASS - RoomInventory
 * ==========================================================
 * Maintains room availability state.
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            roomAvailability.put(roomType, current - 1);
        }
    }
}

/**
 * ==========================================================
 * CLASS - RoomAllocationService
 * ==========================================================
 * Confirms booking requests and assigns unique room IDs.
 */
class RoomAllocationService {

    /**
     * Stores all allocated room IDs to prevent duplicate assignments.
     */
    private Set<String> allocatedRoomIds;

    /**
     * Stores assigned room IDs by room type.
     * Key -> Room type
     * Value -> Set of assigned room IDs
     */
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No rooms available for Guest: " + reservation.getGuestName()
                    + ", Requested Type: " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        inventory.decrementAvailability(roomType);

        System.out.println("Booking confirmed for Guest: " + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    /**
     * Generates a unique room ID for the given room type.
     */
    private String generateRoomId(String roomType) {
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());

        int number = assignedRoomsByType.get(roomType).size() + 1;
        String roomId = roomType + "-" + number;

        while (allocatedRoomIds.contains(roomId)) {
            number++;
            roomId = roomType + "-" + number;
        }

        return roomId;
    }
}

/**
 * ==========================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * ==========================================================
 * Demonstrates reservation confirmation and room allocation.
 */
public class BookMyStay {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);
        inventory.addRoomType("Suite", 1);

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        System.out.println("Room Allocation Processing:");

        while (queue.hasPendingRequests()) {
            Reservation reservation = queue.getNextRequest();
            allocationService.allocateRoom(reservation, inventory);
        }
    }
}