import java.util.List;

/**
 * Menu-driven command-line program for a car hire business.
 * Douth Nhial Kueth - VU-BIT-2511-0648-DAY
 */
public class Main {

    private static final String FULL_NAME = "Douth Nhial Kueth";
    private static final String REG_NUMBER = "VU-BIT-2511-0648-DAY";

    private final HireManager manager = new HireManager();
    private final InputHelper input = new InputHelper();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("==============================================");
        System.out.println("   CAR HIRE CHARGING SYSTEM");
        System.out.println("   Name: " + FULL_NAME);
        System.out.println("   Reg No: " + REG_NUMBER);
        System.out.println("==============================================");

        boolean running = true;
        try {
            while (running) {
                printMenu();
                int choice = input.readInt("Choose an option (1-7): ", 1, 7);
                switch (choice) {
                    case 1: addSaloonHire(); break;
                    case 2: addFourWheelHire(); break;
                    case 3: listReport(); break;
                    case 4: findHire(); break;
                    case 5: removeHire(); break;
                    case 6: showSummary(); break;
                    case 7: running = false; break;
                    default: break;
                }
            }
        } catch (InputHelper.InputEndedException e) {
            System.out.println("Input ended. Closing the program.");
        }
        System.out.println("Goodbye, thank you for using the Car Hire system.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("---------------- MENU ----------------");
        System.out.println("1. Add a saloon hire");
        System.out.println("2. Add a four-wheel hire");
        System.out.println("3. List sorted report");
        System.out.println("4. Find a hire by ID");
        System.out.println("5. Remove a hire");
        System.out.println("6. Show summary");
        System.out.println("7. Exit");
        System.out.println("--------------------------------------");
    }

    private void addSaloonHire() {
        System.out.println("\n--- Add saloon hire (120,000 UGX per day) ---");
        String name = input.readNonBlank("Customer name: ", 50);
        int days = input.readInt("Number of days (1-" + Hire.MAX_DAYS + "): ", 1, Hire.MAX_DAYS);
        try {
            Hire hire = new SaloonHire(manager.nextId(), name, days);
            manager.addHire(hire);
            System.out.println("Hire added successfully.");
            System.out.println(hire);
        } catch (InvalidHireException e) {
            System.out.println("Could not add hire: " + e.getMessage());
        }
    }

    private void addFourWheelHire() {
        System.out.println("\n--- Add four-wheel hire (200,000 UGX per day + 100,000 UGX fuel deposit) ---");
        String name = input.readNonBlank("Customer name: ", 50);
        int days = input.readInt("Number of days (1-" + Hire.MAX_DAYS + "): ", 1, Hire.MAX_DAYS);
        try {
            Hire hire = new FourWheelHire(manager.nextId(), name, days);
            manager.addHire(hire);
            System.out.println("Hire added successfully.");
            System.out.println(hire);
        } catch (InvalidHireException e) {
            System.out.println("Could not add hire: " + e.getMessage());
        }
    }

    private void listReport() {
        System.out.println("\n--- Report (sorted by charge, highest first) ---");
        if (manager.isEmpty()) {
            System.out.println("No hires recorded yet.");
            return;
        }
        List<Hire> sorted = manager.getSortedReport();
        for (Chargeable c : sorted) {          // polymorphism: Chargeable reference
            System.out.println(c);
        }
        System.out.printf("Total of all charges: %,.2f UGX%n", manager.getTotalCharges());
    }

    private void findHire() {
        System.out.println("\n--- Find a hire ---");
        String id = input.readNonBlank("Enter hire ID (e.g. DNK648-001): ", 30);
        try {
            System.out.println(manager.findById(id));
        } catch (HireNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeHire() {
        System.out.println("\n--- Remove a hire ---");
        String id = input.readNonBlank("Enter hire ID to remove: ", 30);
        try {
            Hire hire = manager.findById(id);
            System.out.println(hire);
            if (input.readYesNo("Are you sure you want to remove this hire? (y/n): ")) {
                manager.removeHire(id);
                System.out.println("Hire " + hire.getId() + " removed.");
            } else {
                System.out.println("Removal cancelled.");
            }
        } catch (HireNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSummary() {
        System.out.println("\n--- Summary ---");
        System.out.println("Number of hires: " + manager.size());
        System.out.printf("Total charges: %,.2f UGX%n", manager.getTotalCharges());
        System.out.printf("Total discount given (9%%): %,.2f UGX%n", manager.getTotalDiscountGiven());
    }
}
