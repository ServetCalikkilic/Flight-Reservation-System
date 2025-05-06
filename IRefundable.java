public interface IRefundable {
    boolean processRefund();
    double calculateRefundAmount();
    boolean isRefundable();
}
