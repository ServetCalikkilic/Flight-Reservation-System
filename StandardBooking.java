public class StandardBooking extends AbstractBooking implements ICancellable, IRefundable{
    private boolean insuranceIncluded;

    public StandardBooking(AbstractFlight flight, Passenger[] passengers, AbstractPricingStrategy pricingStrategy, boolean insuranceIncluded){
        super(flight, passengers, pricingStrategy);
        this.insuranceIncluded=insuranceIncluded;
    }

    @Override
    public boolean cancel() {
        if (isCancellationAllowed()) {
            for (Seat seat : assignedSeats) {
                if (seat != null) {
                    seat.release();
                }
            }
            this.status = BookingStatus.CANCELLED;
            processRefund();
            return true;
        }
        return false;

    }

    @Override
    public double calculateCancellationFee() {
        if (insuranceIncluded){
            return 0;
        }
        long diff = flight.getDepartureTime() - System.currentTimeMillis();
        long days = diff / (1000 * 60 * 60 * 24);
        if (days > 30){
            return totalPrice * 0.10;
        }
        else if (days >= 7){
            return totalPrice * 0.30;
        }
        else{
            return totalPrice * 0.50;
        }
    }

    @Override
    public boolean isCancellationAllowed() {
        long twentyFourHoursInMillis = 24 * 60 * 60 * 1000;
        return flight.getDepartureTime() - System.currentTimeMillis() > twentyFourHoursInMillis;
    }

    @Override
    public boolean processRefund() {
        if (isRefundable()) {
            double refundAmount = calculateRefundAmount();
            return true;
        }
        return false;
    }

    @Override
    public double calculateRefundAmount() {
        return pricingStrategy.calculateFinalPrice(flight, passengers.length) - calculateCancellationFee();
    }

    @Override
    public boolean isRefundable() {
        return this.status == BookingStatus.CANCELLED;
    }
}
