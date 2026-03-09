import java.util.LinkedList;
import java.util.Queue;

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        BookingQueue bookingQueue = new BookingQueue();

        // Simulate guest booking requests
        bookingQueue.addBookingRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addBookingRequest(new Reservation("Charlie", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Dana", "Suite Room"));

        System.out.println("Booking Requests Queue (First-Come-First-Served):\n");
        bookingQueue.displayQueue();
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

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price per night: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 20, 50); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 30, 80); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 50, 150); }
}

class RoomInventory {

    private final java.util.HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new java.util.HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class Reservation {

    private final String guestName;
    private final String roomType;

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

class BookingQueue {

    private final Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addBookingRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation processNextRequest() {
        return queue.poll();
    }

    public void displayQueue() {
        int position = 1;
        for (Reservation r : queue) {
            System.out.println(position + ". " + r.getGuestName() + " requested " + r.getRoomType());
            position++;
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
