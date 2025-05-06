public class ChangeRequest {
    private final String bookingId;
    private long newDepartureTime;
    private long newArrivalTime;
    private String[] newSeatNumbers;

    public ChangeRequest(String bookingId, long newDepartureTime, long newArrivalTime, String[] newSeatNumbers) {
        this.bookingId = bookingId;
        this.newDepartureTime = newDepartureTime;
        this.newArrivalTime = newArrivalTime;
        this.newSeatNumbers = newSeatNumbers;
    }

    public String getBookingId() {
        return bookingId;
    }

    public long getNewDepartureTime() {
        return newDepartureTime;
    }

    public long getNewArrivalTime() {
        return newArrivalTime;
    }

    public String[] getNewSeatNumbers() {
        return newSeatNumbers;
    }
}
