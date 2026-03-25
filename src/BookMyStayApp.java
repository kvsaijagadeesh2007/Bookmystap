import java.io.*;
import java.time.LocalDate;
import java.util.*;

class Booking implements Serializable {
    String id, userId, hotelId, status = "PENDING";
    int room;
    LocalDate in, out;

    Booking(String id, String userId, String hotelId, int room, LocalDate in, LocalDate out) {
        this.id = id; this.userId = userId; this.hotelId = hotelId;
        this.room = room; this.in = in; this.out = out;
    }

    @Override
    public String toString() {
        return id + " | " + userId + " | " + hotelId + " | Room:" + room + " | " + status;
    }
}

class BookingSystem implements Serializable {
    Map<String, Set<Integer>> rooms = new HashMap<>();
    Map<String, Set<Integer>> booked = new HashMap<>();
    Map<String, Booking> bookings = new HashMap<>();

    void addHotel(String h, Integer... r) {
        rooms.put(h, new HashSet<>(Arrays.asList(r)));
        booked.put(h, new HashSet<>());
    }

    void book(String id, String h, int r, String user) {
        if (!rooms.containsKey(h) || booked.get(h).contains(r)) throw new RuntimeException("Room unavailable");
        booked.get(h).add(r);
        Booking b = new Booking(id, user, h, r, LocalDate.now(), LocalDate.now().plusDays(2));
        b.status = "CONFIRMED";
        bookings.put(id, b);
    }

    void cancel(String id) {
        Booking b = bookings.get(id);
        if (b == null) throw new RuntimeException("Booking not found");
        booked.get(b.hotelId).remove(b.room);
        b.status = "CANCELLED";
    }

    void save(String filename) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    static BookingSystem load(String filename) throws IOException, ClassNotFoundException {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (BookingSystem) in.readObject();
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws Exception {
        String file = "booking_system.dat";
        BookingSystem system;

        try {
            system = BookingSystem.load(file);
            System.out.println("System state loaded from file.");
        } catch (Exception e) {
            system = new BookingSystem();
            system.addHotel("H1", 101, 102, 103);
            System.out.println("New system initialized.");
        }

        system.book("B1","H1",101,"U1");
        system.book("B2","H1",102,"U2");
        system.cancel("B1");

        system.bookings.values().forEach(System.out::println);

        system.save(file);
        System.out.println("System state saved to file.");
    }
}