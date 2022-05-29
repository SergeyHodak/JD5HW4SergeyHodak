package exceptions;

public class AgeOutOfRange extends Exception {
    public AgeOutOfRange(String field, int min, int max, int input) {
        super("The \"" + field + "\" field has a limitation. " + min + " < age < " + max + ". You sent = " + input);
    }
}