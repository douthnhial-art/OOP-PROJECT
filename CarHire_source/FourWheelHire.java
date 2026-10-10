/**
 * A four-wheel-drive hire: 200,000 UGX per day plus a 100,000 UGX fuel deposit.
 * The 9% discount (7 days or more) applies to the daily hire cost only;
 * the fuel deposit is always charged in full.
 */
public class FourWheelHire extends Hire {

    public static final double DAILY_RATE = 200_000;
    public static final double FUEL_DEPOSIT = 100_000;

    public FourWheelHire(String id, String customerName, int days) throws InvalidHireException {
        super(id, customerName, days);
    }

    @Override
    public String getHireType() {
        return "FourWheelHire";
    }

    @Override
    public double calculateFullCharge() {
        return round2(getDays() * DAILY_RATE + FUEL_DEPOSIT);
    }

    @Override
    public double calculateCharge() {
        double hireCost = getDays() * DAILY_RATE;
        return round2(applyDiscount(hireCost) + FUEL_DEPOSIT);
    }
}
