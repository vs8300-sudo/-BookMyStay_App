import java.util.*;

/**
 * ==========================================================
 * CLASS - Reservation
 * ==========================================================
 * Use Case 5: Booking Request (FIFO)
 * Represents a booking request made by a guest.
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
 * Manages booking requests using FIFO queue.
 */

class BookingRequestQueue {

    /** Queue that stores booking requests. */
    private Queue<Reservation> requestQueue;

    /** Initializes an empty booking queue. */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /** Adds a booking request to the queue. */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for "
                + reservation.getGuestName()
                + " (" + reservation.getRoomType() + ")");
    }

    /** Retrieves and removes the next request (FIFO). */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /** Checks if requests are pending. */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }

    /** Displays all queued requests. */
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            System.out.println("Guest: " + r.getGuestName()
                    + " | Room Type: " + r.getRoomType());
        }
    }
}

/**
 * ==========================================================
 * MAIN CLASS
 * ==========================================================
 * Compile: javac UseCase5BookingRequestQueue.java
 * Run: java UseCase5BookingRequestQueue
 */

public class BookMyStay {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookingRequestQueue queue = new BookingRequestQueue();

        System.out.println("Hotel Booking Request System (FIFO)\n");

        while (true) {

            System.out.println("\n1. Add Booking Request");
            System.out.println("2. Process Next Request");
            System.out.println("3. View Queue");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String type = scanner.nextLine();

                    Reservation reservation = new Reservation(name, type);
                    queue.addRequest(reservation);

                    break;

                case 2:

                    if (queue.hasPendingRequests()) {
                        Reservation next = queue.getNextRequest();
                        System.out.println("\nProcessing request for "
                                + next.getGuestName()
                                + " (" + next.getRoomType() + ")");
                    } else {
                        System.out.println("No pending booking requests.");
                    }

                    break;

                case 3:
                    queue.displayQueue();
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}