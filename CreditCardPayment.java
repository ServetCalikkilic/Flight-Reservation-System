public class CreditCardPayment extends AbstractPaymentProcessor{
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(AbstractBooking booking, String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        super(booking);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }


    @Override
    public boolean processPayment() {
        if (!validatePaymentDetails()) {
            this.status = PaymentStatus.FAILED;
            return false;
        }
        this.status = PaymentStatus.COMPLETED;
        this.paymentTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean validatePaymentDetails() {
        if (cardNumber == null || cardNumber.length() >= 20 || cardNumber.length() <= 12){
            return false;
        }
        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        if (!cvv.matches("\\d{3,4}")){
            return false;
        }
        if (cardHolderName.isEmpty() || cardHolderName == null){
            return false;
        }
        return true;
    }
}
