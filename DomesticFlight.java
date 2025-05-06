public class DomesticFlight extends AbstractFlight implements IChangeable{
    private double domesticTaxRate;

    public DomesticFlight(String flightNumber, String origin, String destination,
                          long departureTime, long arrivalTime, double basePrice,
                          double domesticTaxRate){
        super(flightNumber, origin, destination, departureTime, arrivalTime, basePrice);
        this.domesticTaxRate=domesticTaxRate;
    }

    @Override
    protected Seat[] initializeSeats() {
        Seat[] seats = new Seat[50];
        for (int i = 0; i < 50; i++) {
            String seatNumber = "D" + (i + 1);
            ClassType classType;
            if (i < 10) {
                classType = ClassType.BUSINESS;
            }
            else {
                classType = ClassType.ECONOMY;
            }
            seats[i] = new Seat(seatNumber, classType);
        }
        return seats;
    }

    public double calculateDomesticTax(){
        return basePrice*domesticTaxRate;
    }

    @Override
    public boolean change(ChangeRequest request) {
        if (isChangeAllowed()){
            this.arrivalTime=request.getNewArrivalTime();
            this.departureTime= request.getNewDepartureTime();

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
        return false;
    }

    @Override
    public double calculateChangeFee() {
        return basePrice * 0.1;
    }

    @Override
    public boolean isChangeAllowed() {
        long hoursLeftDeparture = (departureTime - System.currentTimeMillis()) / (1000 * 60 * 60);
        return hoursLeftDeparture > 24;
    }
}
