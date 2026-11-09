package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathServiceTest {

    @Test void primeInputs() {
        assertEquals(1, MathService.isPrime(7));
        assertEquals(0, MathService.isPrime(8));
        assertEquals(0, MathService.isPrime(1));
        assertEquals(0, MathService.isPrime(0));
        assertEquals(0, MathService.isPrime(-5));
        assertEquals(1, MathService.isPrime(2));
    }

    @Test void modulus() {
        assertEquals(2, MathService.calculateMod(17, 5));
        assertEquals(-2, MathService.calculateMod(-17, 5));
        assertEquals(0, MathService.calculateMod(0, 5));
    }

    @Test void divisorZeroIsValidationError() {
        assertThrows(IllegalArgumentException.class, () -> MathService.calculateMod(10, 0));
    }

    @Test void trigonometry() {
        assertEquals(0.5, MathService.calculateSin(30), 1e-9);
        assertEquals(0.5, MathService.calculateCos(60), 1e-9);
        assertEquals(1.0, MathService.calculateTan(45), 1e-9);
        assertTrue(Double.isNaN(MathService.calculateTan(90)));
        assertTrue(Double.isNaN(MathService.calculateTan(270)));
    }

    @Test void toleranceComparison() {
        assertTrue(MathService.isClose(0.5, 0.50));
        assertTrue(MathService.isClose(0.866025, 0.87));
        assertFalse(MathService.isClose(0.5, 0.6));
        assertFalse(MathService.isClose(Double.NaN, 1));
    }
}
