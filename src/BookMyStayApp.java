import java.util.*;

public class BookMyStayApp {

    static Map<String, Set<Integer>> rooms = new HashMap<>();
    static Map<String, Set<Integer>> booked = new HashMap<>();
    static Map<String, String> bookings = new HashMap<>();

    static void addHotel(String h, Integer... r) {
        rooms.put(h, new HashSet<>(Arrays.asList(r)));
        booked.put(h, new HashSet<>());
    }

    static void book(String id, String h, int r) {
        if (!rooms.containsKey(h) || booked.get(h).contains(r))
            throw new RuntimeException("Room unavailable");
        booked.get(h).add(r);
        bookings.put(id, h + "-" + r);
    }

    static void cancel(String id) {
        String[] d = bookings.get(id).split("-");
        booked.get(d[0]).remove(Integer.parseInt(d[1]));
        bookings.remove(id);
    }

    public static void main(String[] args) {
        addHotel("H1", 101, 102, 103);
        book("B1", "H1", 101);
        book("B2", "H1", 102);
        cancel("B1");
        System.out.println(bookings);
    }
}