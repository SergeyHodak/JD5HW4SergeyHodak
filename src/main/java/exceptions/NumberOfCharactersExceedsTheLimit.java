package exceptions;

public class NumberOfCharactersExceedsTheLimit extends Exception {
    public NumberOfCharactersExceedsTheLimit(String field, int limit, String input) {
        super("The value is too long for the field \"" + field +
                "\". Limit = " + limit + ". You are transmitting = " + input.length() + " symbol.");
    }
}