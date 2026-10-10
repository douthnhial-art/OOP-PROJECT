/**
 * A saloon car hire: 120,000 UGX per day.
 */
public class SaloonHire extends Hire {

    public static final double DAILY_RATE = 120_000;

    public SaloonHire(String id, String customerName, int days) throws InvalidHireException {
        super(id, customerName, days);
    }

    @Override
    public String getHireType() {
        return "SaloonHire";
    }

    @Override
    public double calculateFullCharge() {
        return round2(getDays() * DAILY_RATE);
    }

    @Override
    public double calculateCharge() {
        return applyDiscount(calculateFullCharge());
    }
}
