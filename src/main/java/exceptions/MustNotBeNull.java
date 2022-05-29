package exceptions;

public class MustNotBeNull  extends Exception {
    public MustNotBeNull() {
        super("The value passed for writing must be greater than zero.");
    }
}