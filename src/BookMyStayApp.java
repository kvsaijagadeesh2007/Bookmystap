import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }

class Room {
    int id;
    RoomType type;
    boolean booked = false;

    Room(int id, RoomType type) {
        this.id = id;
        this.type = type;
    }
}

class Request {
    String name;
    RoomType type;

    Request(String n, RoomType t) {
        name = n;
        type = t;
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();
    Queue<Request> q = new LinkedList<>();

    Hotel() {
        for (int i = 1; i <= 2; i++) rooms.add(new Room(i, RoomType.SINGLE));
        for (int i = 3; i <= 4; i++) rooms.add(new Room(i, RoomType.DOUBLE));
        rooms.add(new Room(5, RoomType.DELUXE));
    }

    void add(String name, RoomType type) {
        q.add(new Request(name, type));
        System.out.println("Request added: " + name);
    }

    void process() {
        while (!q.isEmpty()) {
            Request r = q.poll();
            boolean allocated = false;

            for (Room room : rooms) {
                if (!room.booked && room.type == r.type) {
                    room.booked = true;
                    System.out.println("Confirmed: " + r.name + " -> Room " + room.id);
                    allocated = true;
                    break;
                }
            }

            if (!allocated)
                System.out.println("Failed: " + r.name + " (No rooms)");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel h = new Hotel();

        while (true) {
            System.out.println("\n1.Add Request 2.Process 3.Exit");
            int ch = sc.nextInt();

            if (ch == 1) {
                String name = sc.next();
                int t = sc.nextInt();
                if (t >= 1 && t <= 3)
                    h.add(name, RoomType.values()[t - 1]);
            }
            else if (ch == 2) h.process();
            else break;
        }
    }
}