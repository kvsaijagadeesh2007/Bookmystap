import java.util.*;

enum RoomType { SINGLE, DOUBLE, DELUXE }

class Request {
    String name;
    RoomType type;

    Request(String n, RoomType t) {
        name = n;
        type = t;
    }
}

class Hotel {
    Map<RoomType, Integer> inv = new HashMap<>();
    Queue<Request> q = new LinkedList<>();

    Hotel() {
        inv.put(RoomType.SINGLE, 2);
        inv.put(RoomType.DOUBLE, 2);
        inv.put(RoomType.DELUXE, 1);
    }

    void add(String n, RoomType t) {
        q.add(new Request(n, t));
        System.out.println("Added: " + n);
    }

    void process() {
        while (!q.isEmpty()) {
            Request r = q.poll();
            if (inv.get(r.type) > 0) {
                inv.put(r.type, inv.get(r.type) - 1);
                System.out.println("Booked: " + r.name);
            } else {
                System.out.println("Failed: " + r.name);
            }
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
                if (t >= 1 && t <= 3)
                    h.add(name, RoomType.values()[t - 1]);
            }
            else if (ch == 2) h.process();
            else break;
        }
    }
}
