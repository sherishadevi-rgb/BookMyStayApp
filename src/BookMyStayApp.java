import java.util.*;

class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;

        List<Service> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Add-On Service Selection");

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("Enter number of services: ");
        int n = Integer.parseInt(sc.nextLine());

        AddOnServiceManager manager = new AddOnServiceManager();

        for (int i = 0; i < n; i++) {

            System.out.print("Enter service name: ");
            String name = sc.nextLine();

            System.out.print("Enter service cost: ");
            double cost = Double.parseDouble(sc.nextLine());

            Service service = new Service(name, cost);

            manager.addService(reservationId, service);
        }

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
git add .
git commit -m "UC7:Add-On Service Selection "
git push origin feature/UC7