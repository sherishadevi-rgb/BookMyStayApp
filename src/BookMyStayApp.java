import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize rooms
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Queue booking requests
        BookingQueue bookingQueue = new BookingQueue();
        bookingQueue.addBookingRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addBookingRequest(new Reservation("Charlie", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Dana", "Suite Room"));
        bookingQueue.addBookingRequest(new Reservation("Eve", "Suite Room")); // extra to test allocation failure

        // Process reservations
        BookingService bookingService = new BookingService(inventory);
        System.out.println("Processing Booking Requests...\n");

        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.processNextRequest();
            bookingService.confirmReservation(request);
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();
    }
}

// Room Domain Model
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

// Centralized Inventory
class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

// Booking Request
class Reservation {
    private final String guestName;
    private final String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Booking Queue (FIFO)
class BookingQueue {
    private final Queue<Reservation> queue;

    public BookingQueue() { queue = new LinkedList<>(); }

    public void addBookingRequest(Reservation reservation) { queue.add(reservation); }

    public Reservation processNextRequest() { return queue.poll(); }

    public boolean isEmpty() { return queue.isEmpty(); }
}

// Booking Service: Confirms and Allocates Rooms
class BookingService {

    private final RoomInventory inventory;
    private final Map<String, Set<String>> allocatedRooms; // roomType -> assigned room IDs
    private int roomCounter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        roomCounter = 1;
    }

    public void confirmReservation(Reservation reservation) {
        String type = reservation.getRoomType();

        if (inventory.allocateRoom(type)) {
            String roomID = generateRoomID(type);
            allocatedRooms.computeIfAbsent(type, k -> new HashSet<>()).add(roomID);

            System.out.println("Reservation Confirmed for " + reservation.getGuestName() +
                    " - Room Type: " + type + ", Room ID: " + roomID);
        } else {
            System.out.println("Reservation Failed for " + reservation.getGuestName() +
                    " - No available rooms of type " + type);
        }
    }

    private String generateRoomID(String roomType) {
        return roomType.replaceAll(" ", "").toUpperCase() + "-" + (roomCounter++);
    }
}
