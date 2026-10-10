/**
 * Thrown when a hire ID is searched for or removed but does not exist.
 */
public class HireNotFoundException extends Exception {
    public HireNotFoundException(String id) {
        super("No hire found with ID: " + id);
    }
}
