/**
 * Thrown when a car hire record is given invalid data
 * (blank name, non-positive number of days, duplicate ID...).
 */
public class InvalidHireException extends Exception {
    public InvalidHireException(String message) {
        super(message);
    }
}
