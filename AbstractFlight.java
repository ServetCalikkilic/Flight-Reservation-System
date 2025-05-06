import java.util.ArrayList;

public abstract class AbstractFlight {
    protected String flightNumber;
    protected String origin;
    protected String destination;
    protected long departureTime;
    protected long arrivalTime;
    protected FlightStatus status;
    protected Seat[] seats;
    protected double basePrice;

    public AbstractFlight(String flightNumber, String origin, String destination, long departureTime, long arrivalTime, double basePrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.seats = initializeSeats();
        this.status = FlightStatus.SCHEDULED;
    }

    protected abstract Seat[] initializeSeats();

    public long calculateDuration(){
        return (arrivalTime-departureTime) / (60 * 1000);
    }

    public Seat[] getAvailableSeats(){
        ArrayList<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                availableSeats.add(seat);
            }
        }

        Seat[] availableSeatsArray = new Seat[availableSeats.size()];
        for (int i = 0; i < availableSeats.size(); i++) {
            availableSeatsArray[i] = availableSeats.get(i);
        }
        return availableSeatsArray;
    }

    public void setStatus(FlightStatus status){
        this.status=status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public double getBasePrice() {
        return basePrice;
    }

}
