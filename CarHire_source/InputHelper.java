import java.util.Scanner;

/**
 * Reads console input and keeps asking until the value is valid,
 * so bad input can never crash the program.
 */
public class InputHelper {

    /** Thrown only when the input stream has ended (for example piped input finished). */
    public static class InputEndedException extends RuntimeException {
        public InputEndedException() {
            super("Input ended.");
        }
    }

    private final Scanner scanner = new Scanner(System.in);

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            throw new InputEndedException();
        }
        return scanner.nextLine().trim();
    }

    /** Whole number between min and max (inclusive). */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            String line = readLine(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("  Please enter a number between " + min + " and " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    /** Decimal number between min and max (inclusive). Commas are allowed (e.g. 50,000). */
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            String line = readLine(prompt).replace(",", "");
            try {
                double value = Double.parseDouble(line);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("  Invalid input. Please enter a real number.");
                } else if (value < min || value > max) {
                    System.out.printf("  Please enter an amount between %,.0f and %,.0f.%n", min, max);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    /** Non-blank text of at most maxLength characters. */
    public String readNonBlank(String prompt, int maxLength) {
        while (true) {
            String line = readLine(prompt);
            if (line.isEmpty()) {
                System.out.println("  This cannot be blank.");
            } else if (line.length() > maxLength) {
                System.out.println("  Too long (maximum " + maxLength + " characters).");
            } else {
                return line;
            }
        }
    }

    /** Yes / no question. Accepts y, yes, n, no (any case). */
    public boolean readYesNo(String prompt) {
        while (true) {
            String line = readLine(prompt).toLowerCase();
            if (line.equals("y") || line.equals("yes")) {
                return true;
            }
            if (line.equals("n") || line.equals("no")) {
                return false;
            }
            System.out.println("  Please answer y or n.");
        }
    }
}
