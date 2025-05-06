public abstract class AbstractPaymentProcessor {
    protected String paymentId;
    protected AbstractBooking booking;
    protected double amount;
    protected long paymentTime;
    protected PaymentStatus status;

    public AbstractPaymentProcessor(AbstractBooking booking) {
        this.booking = booking;
        this.amount = booking.getTotalPrice();
        this.status = PaymentStatus.PENDING;
        this.paymentId = generatePaymentId();

    }

    protected String generatePaymentId(){
        return "PAY" + System.currentTimeMillis();
    }

    public abstract boolean processPayment();

    public abstract boolean validatePaymentDetails();

    public boolean refundPayment(){
        if (status == PaymentStatus.COMPLETED) {
            status = PaymentStatus.REFUNDED;
            return true;
        }
        return false;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public AbstractBooking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public long getPaymentTime() {
        return paymentTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
