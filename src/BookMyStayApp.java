import java.util.*;

public class BookMyStayApp {

    static Map<String, Set<Integer>> rooms = new HashMap<>();
    static Map<String, Set<Integer>> booked = new HashMap<>();

    static synchronized boolean book(String hotelId, int room) {
        if (!rooms.containsKey(hotelId) || booked.get(hotelId).contains(room)) return false;
        booked.get(hotelId).add(room);
        return true;
    }

    static void addHotel(String hotelId, Integer... roomNumbers) {
        rooms.put(hotelId, new HashSet<>(Arrays.asList(roomNumbers)));
        booked.put(hotelId, new HashSet<>());
    }

    public static void main(String[] args) throws InterruptedException {
        addHotel("H1", 101, 102, 103);

        Runnable user1 = () -> System.out.println("User1 booking 101: " + book("H1", 101));
        Runnable user2 = () -> System.out.println("User2 booking 101: " + book("H1", 101));

        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Booked rooms in H1: " + booked.get("H1"));
    }
}