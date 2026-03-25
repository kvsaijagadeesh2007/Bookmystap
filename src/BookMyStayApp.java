import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}

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

    public void setStatus(String status) { this.status = status; }

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

class BookingValidator {
    public static void validateBooking(Booking booking) {
        if (booking == null) {
            throw new InvalidBookingException("Booking cannot be null");
        }
        if (booking.getBookingId() == null || booking.getBookingId().isEmpty()) {
            throw new InvalidBookingException("Invalid booking ID");
        }
        if (booking.getUserId() == null || booking.getUserId().isEmpty()) {
            throw new InvalidBookingException("Invalid user ID");
        }
        if (booking.getCheckIn().isAfter(booking.getCheckOut())) {
            throw new InvalidBookingException("Check-in date must be before check-out date");
        }
    }
}

class BookingManager {

    private Map<String, Booking> activeBookings = new HashMap<>();
    private List<Booking> bookingHistory = new ArrayList<>();

    public void confirmBooking(Booking booking) {
        BookingValidator.validateBooking(booking);

        if (activeBookings.containsKey(booking.getBookingId())) {
            throw new InvalidBookingException("Duplicate booking ID");
        }

        booking.setStatus("CONFIRMED");
        activeBookings.put(booking.getBookingId(), booking);
        bookingHistory.add(booking);
    }

    public void cancelBooking(String bookingId) {
        if (bookingId == null || bookingId.isEmpty()) {
            throw new InvalidBookingException("Invalid booking ID");
        }

        Booking booking = activeBookings.get(bookingId);

        if (booking == null) {
            throw new BookingNotFoundException("Booking not found");
        }

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new InvalidBookingException("Booking already cancelled");
        }

        booking.setStatus("CANCELLED");
        activeBookings.remove(bookingId);
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
        bookingHistory.forEach(System.out::println);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingManager manager = new BookingManager();

        try {
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

            System.out.println(manager.getTotalBookings());
            System.out.println(manager.getBookingsByUser("U001"));
            System.out.println(manager.getBookingStatusSummary());
            System.out.println(manager.getBookingsPerDay());

            manager.printAllBookings();

        } catch (InvalidBookingException | BookingNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}