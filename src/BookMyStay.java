import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

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

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public synchronized Reservation getNextRequest() {
        return requestQueue.poll();
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
    }

    public synchronized void addRoomType(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public synchronized int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public synchronized boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            roomAvailability.put(roomType, available - 1);
            return true;
        }
        return false;
    }
}

class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        roomCounters = new HashMap<>();
    }

    public synchronized void allocateReservation(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        if (inventory.allocateRoom(roomType)) {
            int nextNumber = roomCounters.getOrDefault(roomType, 0) + 1;
            roomCounters.put(roomType, nextNumber);

            String roomId = roomType + "-" + nextNumber;
            allocatedRoomIds.add(roomId);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
        }
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue, RoomInventory inventory, RoomAllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    public void run() {
        while (true) {
            Reservation reservation = bookingQueue.getNextRequest();
            if (reservation == null) {
                break;
            }
            allocationService.allocateReservation(reservation, inventory);
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);
        inventory.addRoomType("Suite", 2);

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println();
        System.out.println("Remaining Inventory:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));
    }
}