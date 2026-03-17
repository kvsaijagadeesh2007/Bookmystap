import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }
enum Service { WIFI, FOOD, LAUNDRY }

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
    List<Service> services;

    Request(String n, RoomType t, List<Service> s) {
        name = n;
        type = t;
        services = s;
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

    void add(String name, RoomType type, List<Service> services) {
        q.add(new Request(name, type, services));
        System.out.println("Request added: " + name);
    }

    void process() {
        while (!q.isEmpty()) {
            Request r = q.poll();
            boolean done = false;

            for (Room room : rooms) {
                if (!room.booked && room.type == r.type) {
                    room.booked = true;
                    System.out.println("Confirmed: " + r.name +
                            " -> Room " + room.id +
                            " Services: " + r.services);
                    done = true;
                    break;
                }
            }

            if (!done)
                System.out.println("Failed: " + r.name);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel h = new Hotel();

        while (true) {
            System.out.println("\n1.Add 2.Process 3.Exit");
            int ch = sc.nextInt();

            if (ch == 1) {
                String name = sc.next();
                int t = sc.nextInt();

                List<Service> services = new ArrayList<>();
                System.out.println("Add services (1.WIFI 2.FOOD 3.LAUNDRY, 0 to stop)");

                while (true) {
                    int s = sc.nextInt();
                    if (s == 0) break;
                    if (s >= 1 && s <= 3)
                        services.add(Service.values()[s - 1]);
                }

                if (t >= 1 && t <= 3)
                    h.add(name, RoomType.values()[t - 1], services);
            }
            else if (ch == 2) h.process();
            else break;
        }
    }
}