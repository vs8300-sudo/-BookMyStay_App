abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    protected int availableRooms;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight, int availableRooms) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Room Size: " + squareFeet + " sq ft");
        System.out.println("Price per Night: " + pricePerNight);
        System.out.println("Available Rooms: " + availableRooms);
    }
}


class SingleRoom extends Room {

    public SingleRoom(int availableRooms) {
        super(1, 250, 1500.0, availableRooms);
    }

    public void displayRoomDetails() {
        System.out.println("\nSingle Room");
        super.displayRoomDetails();
    }
}


class DoubleRoom extends Room {

    public DoubleRoom(int availableRooms) {
        super(2, 400, 2500.0, availableRooms);
    }

    public void displayRoomDetails() {
        System.out.println("\nDouble Room ");
        super.displayRoomDetails();
    }
}


class SuiteRoom extends Room {

    public SuiteRoom(int availableRooms) {
        super(3, 750, 5000.0, availableRooms);
    }

    public void displayRoomDetails() {
        System.out.println("\nSuite Room");
        super.displayRoomDetails();
    }
}


public class BookMyStay {

    public static void main(String[] args) {

        SingleRoom single = new SingleRoom(5);
        DoubleRoom dbl = new DoubleRoom(3);
        SuiteRoom suite = new SuiteRoom(2);

        single.displayRoomDetails();
        dbl.displayRoomDetails();
        suite.displayRoomDetails();
    }
}