public class UseCase3InventorySetup {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        // Define room models to retrieve details
        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Hotel Room Inventory Status\n");

        printStatus("Single", single, inventory);
        printStatus("Double", doubleR, inventory);
        printStatus("Suite", suite, inventory);
    }

    private static void printStatus(String type, Room room, RoomInventory inv) {
        System.out.println(type + " Room:");
        room.displayRoomDetails();
        System.out.println("Available Rooms: " + inv.getRoomAvailability().get(type));
        System.out.println();
    }
}