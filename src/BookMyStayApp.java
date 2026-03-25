import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class Booking {
    private String bookingId;
    private String userId;
    private String hotelId;
    private int roomNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private LocalDateTime createdAt;

    public Booking(String bookingId, String userId, String hotelId,
                   int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getHotelId() { return hotelId; }
    public int getRoomNumber() { return roomNumber; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", room=" + roomNumber +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}

class BookingManager {

    private Map<String, Booking> activeBookings = new HashMap<>();
    private List<Booking> bookingHistory = new ArrayList<>();

    public void confirmBooking(Booking booking) {
        if (activeBookings.containsKey(booking.getBookingId())) {
            System.out.println("Booking ID already exists!");
            return;
        }

        booking.setStatus("CONFIRMED");
        activeBookings.put(booking.getBookingId(), booking);
        bookingHistory.add(booking);

        System.out.println("Booking confirmed: " + booking.getBookingId());
    }

    public void cancelBooking(String bookingId) {
        Booking booking = activeBookings.get(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        booking.setStatus("CANCELLED");
        activeBookings.remove(bookingId);

        System.out.println("Booking cancelled: " + bookingId);
    }

    public int getTotalBookings() {
        return bookingHistory.size();
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingHistory.stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getBookingStatusSummary() {
        return bookingHistory.stream()
                .collect(Collectors.groupingBy(
                        Booking::getStatus,
                        Collectors.counting()
                ));
    }

    public Map<LocalDate, Long> getBookingsPerDay() {
        return bookingHistory.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));
    }

    public void printAllBookings() {
        System.out.println("\n--- Booking History ---");
        bookingHistory.forEach(System.out::println);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingManager manager = new BookingManager();

        Booking b1 = new Booking("B001", "U001", "H001", 101,
                LocalDate.now(), LocalDate.now().plusDays(2));

        Booking b2 = new Booking("B002", "U002", "H002", 202,
                LocalDate.now(), LocalDate.now().plusDays(3));

        Booking b3 = new Booking("B003", "U001", "H001", 103,
                LocalDate.now(), LocalDate.now().plusDays(1));

        manager.confirmBooking(b1);
        manager.confirmBooking(b2);
        manager.confirmBooking(b3);

        manager.cancelBooking("B002");

        System.out.println("\nTotal Bookings: " + manager.getTotalBookings());

        System.out.println("\nBookings by User U001:");
        manager.getBookingsByUser("U001")
                .forEach(System.out::println);

        System.out.println("\nStatus Summary:");
        System.out.println(manager.getBookingStatusSummary());

        System.out.println("\nBookings Per Day:");
        System.out.println(manager.getBookingsPerDay());

        manager.printAllBookings();
    }
}