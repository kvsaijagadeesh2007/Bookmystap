import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }

class Hotel {
    private Map<RoomType, Integer> inventory = new HashMap<>();

    // Initialize static inventory
    public Hotel() {
        inventory.put(RoomType.SINGLE, 3);
        inventory.put(RoomType.DOUBLE, 3);
        inventory.put(RoomType.DELUXE, 2);
    }

    // Show available rooms
    public void showAvailability() {
        System.out.println("\nAvailable Rooms:");
        for (RoomType type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }

    // Book room using centralized inventory
    public void bookRoom(RoomType type) {
        int count = inventory.get(type);

        if (count > 0) {
            inventory.put(type, count - 1);
            System.out.println(type + " room booked successfully!");
        } else {
            System.out.println("No " + type + " rooms available!");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n1.View Availability 2.Book Room 3.Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    hotel.showAvailability();
                    break;

                case 2:
                    System.out.println("1.SINGLE 2.DOUBLE 3.DELUXE");
                    int t = sc.nextInt();

                    if (t >= 1 && t <= 3) {
                        hotel.bookRoom(RoomType.values()[t - 1]);
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;

                case 3:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}