import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }

class Hotel {
    private Map<RoomType, Integer> inventory = new HashMap<>();

    public Hotel() {
        inventory.put(RoomType.SINGLE, 3);
        inventory.put(RoomType.DOUBLE, 3);
        inventory.put(RoomType.DELUXE, 2);
    }

    // Show all availability
    public void showAll() {
        System.out.println("\nRoom Availability:");
        for (RoomType type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }

    // Search specific room type
    public void searchRoom(RoomType type) {
        int count = inventory.get(type);
        if (count > 0) {
            System.out.println(type + " rooms available: " + count);
        } else {
            System.out.println(type + " rooms NOT available!");
        }
    }

    // Book room after checking availability
    public void bookRoom(RoomType type) {
        if (inventory.get(type) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            System.out.println(type + " room booked!");
        } else {
            System.out.println("Booking failed! No " + type + " rooms.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n1.View All 2.Search Room 3.Book Room 4.Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                hotel.showAll();
            }
            else if (choice == 2 || choice == 3) {
                System.out.println("1.SINGLE 2.DOUBLE 3.DELUXE");
                int t = sc.nextInt();

                if (t < 1 || t > 3) {
                    System.out.println("Invalid!");
                    continue;
                }

                RoomType type = RoomType.values()[t - 1];

                if (choice == 2)
                    hotel.searchRoom(type);
                else
                    hotel.bookRoom(type);
            }
            else if (choice == 4) {
                System.out.println("Thank you!");
                break;
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }
}