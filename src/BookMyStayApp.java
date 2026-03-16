import java.io.*;
import java.util.*;

class RoomInventory {

    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new LinkedHashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRoom(String type, int count) {
        rooms.put(type, count);
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    inventory.setRoom(roomType, count);
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading inventory data.");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        persistence.loadInventory(inventory, filePath);

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        persistence.saveInventory(inventory, filePath);
    }
}
