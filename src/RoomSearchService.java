import java.util.Map;

public class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        // Helper to check and display
        displayIfAvailable("Single", singleRoom, availability.get("Single"));
        displayIfAvailable("Double", doubleRoom, availability.get("Double"));
        displayIfAvailable("Suite", suiteRoom, availability.get("Suite"));
    }

    private void displayIfAvailable(String type, Room room, Integer count) {
        if (count != null && count > 0) {
            System.out.println(type + " Room:");
            room.displayRoomDetails();
            System.out.println("Available: " + count + "\n");
        }
    }
}