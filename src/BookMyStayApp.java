import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }

class Room {
    int id;
    RoomType type;
    boolean booked;

    Room(int id, RoomType type) {
        this.id = id;
        this.type = type;
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();

    Hotel() {
        for (int i = 1; i <= 3; i++) rooms.add(new Room(i, RoomType.SINGLE));
        for (int i = 4; i <= 6; i++) rooms.add(new Room(i, RoomType.DOUBLE));
        for (int i = 7; i <= 8; i++) rooms.add(new Room(i, RoomType.DELUXE));
    }

    void showAvailable() {
        for (Room r : rooms)
            if (!r.booked)
                System.out.println(r.id + " - " + r.type);
    }

    void book(RoomType type) {
        for (Room r : rooms) {
            if (r.type == type && !r.booked) {
                r.booked = true;
                System.out.println("Booked Room: " + r.id);
                return;
            }
        }
        System.out.println("No rooms available!");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel h = new Hotel();

        while (true) {
            System.out.println("\n1.View 2.Book 3.Exit");
            int ch = sc.nextInt();

            if (ch == 1) h.showAvailable();
            else if (ch == 2) {
                System.out.println("1.SINGLE 2.DOUBLE 3.DELUXE");
                int t = sc.nextInt();
                if (t >= 1 && t <= 3)
                    h.book(RoomType.values()[t - 1]);
                else
                    System.out.println("Invalid!");
            } else break;
        }
    }
}