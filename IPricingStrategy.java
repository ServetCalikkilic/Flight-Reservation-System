public interface IPricingStrategy {
    double getPriceForClass(ClassType type);
    double getPriceForDate(long date);
    boolean applyPromoCode(String code);
}
