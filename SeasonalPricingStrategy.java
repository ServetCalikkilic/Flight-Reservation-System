public class SeasonalPricingStrategy extends AbstractPricingStrategy implements IPricingStrategy{
    private final double lowSeasonRate = 0.8;
    private final double highSeasonRate = 1.3;
    private long[] highSeasonStartDates;
    private long[] highSeasonEndDates;

    public SeasonalPricingStrategy(double taxRate, double discountRate){
        super(taxRate, discountRate);
        this.highSeasonStartDates= new long[]{1752556800000L,1765824000000L };
        this.highSeasonEndDates=new long[]{1757875199000L,1789756799000L};

    }

    private boolean isHighSeason(long date){
        long daysSinceEpoch = date / 24L * 60 * 60 * 1000;
        int dayOfYear = (int) (daysSinceEpoch % 365) + 1;

        int summerStart = (int) highSeasonStartDates[0];
        int summerEnd = (int) highSeasonEndDates[0];
        int winterStart = (int) highSeasonStartDates[1];
        int winterEnd = (int) highSeasonEndDates[1];

        boolean inSummer = (dayOfYear >= summerStart && dayOfYear <= summerEnd);
        boolean inWinter = (dayOfYear >= winterStart && dayOfYear <= winterEnd);

        return inSummer || inWinter;
    }

    @Override
    public double getPriceForClass(ClassType type) {
        if (type == ClassType.ECONOMY){
            return basePrice * 1.0;
        }
        else if (type == ClassType.BUSINESS){
            return basePrice * 2.0;
        }
        else {
            return basePrice * 3.0;
        }
    }

    @Override
    public double getPriceForDate(long date) {
        if (isHighSeason(date)){
            return basePrice * highSeasonRate;
        }
        else{
            return basePrice * lowSeasonRate;
        }
    }

    @Override
    public double calculateBasePrice(AbstractFlight flight) {
        return isHighSeason(flight.getDepartureTime())
                ? super.calculateBasePrice(flight) * highSeasonRate
                : super.calculateBasePrice(flight) * lowSeasonRate;
    }

    @Override
    public boolean applyPromoCode(String code) {
        if ("SUMMER2023".equals(code)) {
            discountRate += 0.10;
            return true;
        }
        if ("WINTER2023".equals(code)) {
            discountRate += 0.15;
            return true;
        }
        return false;
    }
}
