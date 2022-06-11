package exceptions;

public class NotNegative extends Exception {
    public NotNegative() {
        super("ERROR. Must be greater than or equal to zero");
    }
}
