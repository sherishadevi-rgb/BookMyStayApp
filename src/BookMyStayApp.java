public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("Welcome to BookMyStay - Hotel Booking System\n");

        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailability);
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailability);
        System.out.println();

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailability);
    }
}

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 20, 50);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 30, 80);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 50, 150);
    }
}

