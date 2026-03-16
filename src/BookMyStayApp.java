import java.util.HashMap;

public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        System.out.println("Welcome to BookMyStay - Hotel Booking System\n");

        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));
        System.out.println();

        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));
        System.out.println();

        System.out.println("Current Inventory:");
        inventory.displayInventory();
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

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

