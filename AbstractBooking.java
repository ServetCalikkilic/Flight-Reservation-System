public abstract class AbstractBooking {
    protected String bookingId;
    protected AbstractFlight flight;
    protected Passenger[] passengers;
    protected Seat[] assignedSeats;
    protected long bookingTime;
    protected BookingStatus status;
    protected AbstractPricingStrategy pricingStrategy;
    protected double totalPrice;

    public AbstractBooking(AbstractFlight flight, Passenger[] passengers, AbstractPricingStrategy pricingStrategy) {
        this.flight = flight;
        this.passengers = passengers;
        this.pricingStrategy = pricingStrategy;
        this.status = BookingStatus.PENDING;
        this.bookingId = generateBookingId();
    }

    protected String generateBookingId(){
        return "BK" + System.currentTimeMillis();
    }

    public boolean createBooking(){
        this.bookingTime = System.currentTimeMillis();
        this.status = BookingStatus.CONFIRMED;
        this.totalPrice = calculateTotalPrice();
        return true;
    }

    public double calculateTotalPrice(){
        return pricingStrategy.calculateFinalPrice(flight, passengers.length);
    }

    public boolean assignSeats(){
        Seat[] availableSeats = flight.getAvailableSeats();
        if (availableSeats.length < passengers.length) {
            return false;
        }

        assignedSeats = new Seat[passengers.length];
        int index = 0;
        for (Seat seat : availableSeats) {
            if (seat.isAvailable()) {
                seat.reserve();
                assignedSeats[index++] = seat;
                if (index == passengers.length) {
                    break;
                }
            }
        }

        return index == passengers.length;
    }

    public String getBookingId() {
        return bookingId;
    }

    public AbstractFlight getFlight() {
        return flight;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public Seat[] getAssignedSeats() {
        return assignedSeats;
    }

    public long getBookingTime() {
        return bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public AbstractPricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
