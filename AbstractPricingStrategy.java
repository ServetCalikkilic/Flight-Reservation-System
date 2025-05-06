public abstract class AbstractPricingStrategy {
    protected double basePrice;
    protected double taxRate;
    protected double discountRate;

    public AbstractPricingStrategy(double taxRate, double discountRate) {
        this.taxRate = taxRate;
        this.discountRate = discountRate;
    }

    public double calculateBasePrice(AbstractFlight flight){
        this.basePrice = flight.getBasePrice();
        return basePrice;
    }

    public double applyDiscounts(int passengerCount){
        double discount = basePrice * discountRate * passengerCount;
        return (basePrice * passengerCount) - discount;

    }

    public double applyTaxes(double priceAfterDiscount){
        return priceAfterDiscount + (priceAfterDiscount * taxRate);

    }

    public double calculateFinalPrice(AbstractFlight flight, int passengerCount){
        calculateBasePrice(flight);
        double priceAfterDiscount = applyDiscounts(passengerCount);
        double finalPrice = applyTaxes(priceAfterDiscount);
        return finalPrice;
    }
}
