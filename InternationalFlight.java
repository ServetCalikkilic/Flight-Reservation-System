import java.util.ArrayList;

public class InternationalFlight extends AbstractFlight implements IChangeable{
    private final String[] requiredDocuments = new String[3];

    public InternationalFlight(String flightNumber, String origin, String destination, long departureTime, long arrivalTime, double basePrice){
        super(flightNumber, origin, destination, departureTime, arrivalTime, basePrice);
    }
    @Override
    protected Seat[] initializeSeats() {
        Seat[] seats = new Seat[100];
        for (int i = 0; i < 100; i++) {
            String seatNumber = "I" + (i + 1);
            ClassType classType;
            if (i < 10) {
                classType = ClassType.FIRST;
            }
            else if (i < 30) {
                classType = ClassType.BUSINESS;
            }
            else {
                classType = ClassType.ECONOMY;
            }
            seats[i] = new Seat(seatNumber, classType);
        }
        return seats;
    }

    @Override
    public boolean change(ChangeRequest request) {
        if (!isChangeAllowed()){
            return false;
        }

        this.departureTime = request.getNewDepartureTime();
        this.arrivalTime = request.getNewArrivalTime();

        for (String newSeatNumber : request.getNewSeatNumbers()) {
            boolean seatReserved = false;
            for (Seat seat : seats) {
                if (seat.getSeatNumber().equals(newSeatNumber) && seat.isAvailable()) {
                    seat.reserve();
                    seatReserved = true;
                    break;
                }
            }
            if (!seatReserved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double calculateChangeFee() {
        return basePrice * 0.2;
    }

    @Override
    public boolean isChangeAllowed() {
        long hoursLeftDeparture = (departureTime - System.currentTimeMillis()) / (1000 * 60 * 60);
        return hoursLeftDeparture > 72;
    }
}
