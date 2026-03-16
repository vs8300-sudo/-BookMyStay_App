public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization\n");

        System.out.println("Single Room:");
        new SingleRoom().displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("Double Room:");
        new DoubleRoom().displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("Suite Room:");
        new SuiteRoom().displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}