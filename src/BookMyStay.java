import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Reservation {
    private String reservationId;
    private String roomType;
    private boolean cancelled;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}

class RoomInventory {
    private int singleCount;
    private int doubleCount;
    private int suiteCount;

    public RoomInventory(int singleCount, int doubleCount, int suiteCount) {
        this.singleCount = singleCount;
        this.doubleCount = doubleCount;
        this.suiteCount = suiteCount;
    }

    public void incrementAvailability(String roomType) {
        if (roomType.equals("Single")) {
            singleCount++;
        } else if (roomType.equals("Double")) {
            doubleCount++;
        } else if (roomType.equals("Suite")) {
            suiteCount++;
        }
    }

    public int getAvailability(String roomType) {
        if (roomType.equals("Single")) {
            return singleCount;
        } else if (roomType.equals("Double")) {
            return doubleCount;
        } else if (roomType.equals("Suite")) {
            return suiteCount;
        }
        return 0;
    }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public Reservation findReservationById(String reservationId) {
        for (Reservation reservation : confirmedReservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
}

class CancellationService {
    private Stack<String> rollbackHistory;

    public CancellationService() {
        rollbackHistory = new Stack<>();
    }

    public void cancelBooking(String reservationId, BookingHistory history, RoomInventory inventory) {
        Reservation reservation = history.findReservationById(reservationId);

        if (reservation == null || reservation.isCancelled()) {
            System.out.println("Cancellation failed. Reservation not found or already cancelled.");
            return;
        }

        rollbackHistory.push(reservation.getReservationId());
        inventory.incrementAvailability(reservation.getRoomType());
        reservation.cancel();

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + reservation.getRoomType());
    }

    public void displayRollbackHistory() {
        System.out.println();
        System.out.println("Rollback History (Most Recent First):");
        while (!rollbackHistory.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackHistory.pop());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory(5, 3, 2);
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        Reservation reservation = new Reservation("Single-1", "Single");
        history.addReservation(reservation);

        cancellationService.cancelBooking("Single-1", history, inventory);
        cancellationService.displayRollbackHistory();

        System.out.println();
        System.out.println("Updated Single Room Availability: " + inventory.getAvailability("Single"));
    }
}