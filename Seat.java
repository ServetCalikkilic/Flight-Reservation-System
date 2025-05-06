public class Seat {
    private final String seatNumber;
    private final ClassType classType;
    private boolean available;

    public Seat(String seatNumber, ClassType classType) {
        this.seatNumber = seatNumber;
        this.classType = classType;
        this.available=true;
    }

    public boolean isAvailable(){
        return available;
    }

    public boolean reserve(){
        if (available) {
            available = false;
            return true;
        }
        return false;
    }

    public boolean release(){
        if (!available) {
            available = true;
            return true;
        }
        return false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public ClassType getClassType() {
        return classType;
    }
}
