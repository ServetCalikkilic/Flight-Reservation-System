public interface ICancellable {
    boolean cancel();
    double calculateCancellationFee();
    boolean isCancellationAllowed();
}
