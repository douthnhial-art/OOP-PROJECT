import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Stores all hires in an ArrayList and provides add, find, remove,
 * totals and the sorted report.
 */
public class HireManager {

    /** Initials (DNK) + last three digits of registration number (648). */
    public static final String ID_PREFIX = "DNK648";

    private final ArrayList<Hire> hires = new ArrayList<>();
    private int counter = 0;

    /** Next ID in the form DNK648-001. IDs are never reused. */
    public String nextId() {
        counter++;
        return String.format("%s-%03d", ID_PREFIX, counter);
    }

    public void addHire(Hire hire) throws InvalidHireException {
        if (hire == null) {
            throw new InvalidHireException("Cannot add an empty hire.");
        }
        for (Hire existing : hires) {
            if (existing.getId().equalsIgnoreCase(hire.getId())) {
                throw new InvalidHireException("A hire with ID " + hire.getId() + " already exists.");
            }
        }
        hires.add(hire);
    }

    public Hire findById(String id) throws HireNotFoundException {
        String wanted = id == null ? "" : id.trim();
        for (Hire hire : hires) {
            if (hire.getId().equalsIgnoreCase(wanted)) {
                return hire;
            }
        }
        throw new HireNotFoundException(wanted);
    }

    public void removeHire(String id) throws HireNotFoundException {
        Hire hire = findById(id);
        hires.remove(hire);
    }

    public int size() {
        return hires.size();
    }

    public boolean isEmpty() {
        return hires.isEmpty();
    }

    public double getTotalCharges() {
        double total = 0;
        for (Chargeable c : hires) {
            total += c.calculateCharge(); // polymorphic call
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public double getTotalDiscountGiven() {
        double total = 0;
        for (Hire h : hires) {
            total += h.getDiscountGiven();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Report order for digit A = 6: charge, highest first.
     * Customers with equal charges are ordered by name.
     */
    public List<Hire> getSortedReport() {
        List<Hire> copy = new ArrayList<>(hires);
        copy.sort(Comparator.comparingDouble(Hire::calculateCharge).reversed()
                .thenComparing(Hire::getCustomerName, String.CASE_INSENSITIVE_ORDER));
        return copy;
    }
}
