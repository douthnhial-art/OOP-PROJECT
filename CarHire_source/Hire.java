/**
 * Abstract base class for every record in the car hire business.
 * Holds the fields shared by all hires and leaves the charging rule
 * to the subclasses.
 *
 * Discount rule (registration VU-BIT-2511-0648-DAY gives B = 4):
 * P = B + 5 = 9% off the hire cost for hires of 7 or more days.
 */
public abstract class Hire implements Chargeable {

    /** 9% discount. */
    public static final double DISCOUNT_RATE = 0.09;
    /** Hires of this many days or more get the discount. */
    public static final int MIN_DAYS_FOR_DISCOUNT = 7;
    public static final int MAX_DAYS = 365;

    private String id;
    private String customerName;
    private int days;
    private boolean discountApplies;

    protected Hire(String id, String customerName, int days) throws InvalidHireException {
        setId(id);
        setCustomerName(customerName);
        setDays(days);
    }

    // ---------- getters ----------
    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getDays() {
        return days;
    }

    public boolean isDiscountApplies() {
        return discountApplies;
    }

    // ---------- setters with validation ----------
    public void setId(String id) throws InvalidHireException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidHireException("Hire ID cannot be blank.");
        }
        this.id = id.trim();
    }

    public void setCustomerName(String customerName) throws InvalidHireException {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidHireException("Customer name cannot be blank.");
        }
        if (customerName.trim().length() > 50) {
            throw new InvalidHireException("Customer name cannot be longer than 50 characters.");
        }
        this.customerName = customerName.trim();
    }

    /** Sets the number of days and decides whether the 9% discount applies. */
    public void setDays(int days) throws InvalidHireException {
        if (days <= 0) {
            throw new InvalidHireException("Number of days must be at least 1.");
        }
        if (days > MAX_DAYS) {
            throw new InvalidHireException("Number of days cannot be more than " + MAX_DAYS + ".");
        }
        this.days = days;
        this.discountApplies = days >= MIN_DAYS_FOR_DISCOUNT;
    }

    // ---------- behaviour ----------

    /** Name of the kind of hire, used for display. */
    public abstract String getHireType();

    /** Charge before any discount. */
    public abstract double calculateFullCharge();

    /** Final charge (with the 9% discount where it applies). */
    @Override
    public abstract double calculateCharge();

    /** Amount of money taken off by the discount. */
    public double getDiscountGiven() {
        return round2(calculateFullCharge() - calculateCharge());
    }

    /** Shared helper: takes 9% off the given hire cost when the discount applies. */
    protected double applyDiscount(double hireCost) {
        if (discountApplies) {
            return round2(hireCost * (1 - DISCOUNT_RATE));
        }
        return round2(hireCost);
    }

    protected static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%-11s %-14s %-20s Days: %-3d | Full: %,.2f | Discount: %,.2f | Charge: %,.2f UGX",
                id, getHireType(), customerName, days,
                calculateFullCharge(), getDiscountGiven(), calculateCharge());
    }
}
