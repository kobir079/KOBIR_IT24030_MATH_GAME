package service;

/**
 * Reusable mathematical functions for the Math Whiz game.
 * All answers are calculated here - no answers are stored manually.
 */
public class MathService {

    /** Tolerance used when comparing decimal answers. */
    public static final double TOLERANCE = 0.01;

    /** Returns 1 if number is prime, otherwise 0. Numbers less than 2 are not prime. */
    public static int isPrime(int number) {
        if (number < 2) return 0;
        if (number == 2) return 1;
        if (number % 2 == 0) return 0;
        for (int i = 3; (long) i * i <= number; i += 2) {
            if (number % i == 0) return 0;
        }
        return 1;
    }

    /** Returns a % b. The divisor must not be zero. */
    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero.");
        }
        return a % b;
    }

    public static double calculateSin(double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double calculateCos(double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    /** Returns tan(degree); Double.NaN where tangent is undefined (90, 270, ...). */
    public static double calculateTan(double degree) {
        double normalized = ((degree % 180) + 180) % 180;
        if (Math.abs(normalized - 90) < 1e-9) {
            return Double.NaN;
        }
        return Math.tan(Math.toRadians(degree));
    }

    /** Calculates sin, cos or tan by name. */
    public static double calculateTrig(String function, double degree) {
        switch (function.toLowerCase()) {
            case "sin": return calculateSin(degree);
            case "cos": return calculateCos(degree);
            case "tan": return calculateTan(degree);
            default: throw new IllegalArgumentException("Unknown function: " + function);
        }
    }

    /** Compares decimal values using the tolerance. */
    public static boolean isClose(double expected, double actual) {
        if (Double.isNaN(expected) || Double.isNaN(actual)) return false;
        return Math.abs(expected - actual) <= TOLERANCE;
    }
}
