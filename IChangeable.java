public interface IChangeable {
    boolean change(ChangeRequest request);
    double calculateChangeFee();
    boolean isChangeAllowed();
}

