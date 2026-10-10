/**
 * Anything that can be charged for. Every car hire record promises to
 * be able to work out how much the customer owes.
 */
public interface Chargeable {
    double calculateCharge();
}
