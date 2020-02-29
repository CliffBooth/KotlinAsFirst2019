package lesson11.task1

import lesson6.task1.calculator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class PolynomTest {

    private fun assertApproxEquals(expected: Polynom, actual: Polynom, eps: Double) {
        assertEquals(expected.degree(), actual.degree())
        for (i in 0..expected.degree()) {
            assertEquals(expected.coeff(i), actual.coeff(i), eps)
        }
    }

    @Test
    @Tag("Easy")
    fun getValue() {
        val p = Polynom(1.0, 3.0, 2.0)
        assertEquals(42.0, p.getValue(5.0), 1e-10)
        assertEquals(20.0, Polynom(0.0, 0.0, 20.0).getValue(5.0), 1e-10)
        assertEquals(0.0, Polynom(0.0, 0.0, 0.0, 0.0, 0.0).getValue(5.0), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun degree() {
        val p = Polynom(1.0, 1.0, 1.0)
        assertEquals(2, p.degree())
        val q = Polynom(0.0)
        assertEquals(0, q.degree())
        val r = Polynom(0.0, 1.0, 2.0)
        assertEquals(1, r.degree())
        assertEquals(3, Polynom(0.0, 0.0, 10.0, 1.0, 2.0, 0.0).degree())
    }

    @Test
    @Tag("Easy")
    fun plus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -1.0, 2.0, 6.0)
        assertApproxEquals(r, p1 + p2, 1e-10)
        assertApproxEquals(r, p2 + p1, 1e-10)
        val pol1 = Polynom(3.0, -5.0, 3.0, 4.0)
        val pol2 = Polynom(-3.0, 5.0, 3.0, 0.0)
        val res = Polynom(6.0, 4.0)
        assertApproxEquals(res, pol1 + pol2, 1e-10)
    }

    @Test
    @Tag("Easy")
    operator fun unaryMinus() {
        val p = Polynom(1.0, -1.0, 2.0)
        val r = Polynom(-1.0, 1.0, -2.0)
        assertApproxEquals(r, -p, 1e-11)
        assertApproxEquals(Polynom(-5.0, -3.0, 2.0), -Polynom(0.0, 0.0, 5.0, 3.0, -2.0), 1e-11)
    }

    @Test
    @Tag("Easy")
    fun minus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -3.0, -4.0, 2.0)
        val pol1 = Polynom(5.0, 4.0, 0.0, 0.0, 20.5)
        val pol2 = Polynom(0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0, 0.0, 0.5)
        val res = Polynom(5.0, 0.0, 0.0, 0.0, 20.0)
        assertApproxEquals(r, p1 - p2, 1e-10)
        assertApproxEquals(-r, p2 - p1, 1e-10)
        assertApproxEquals(res, pol1 - pol2, 1e-10)
    }

    @Test
    @Tag("Normal")
    fun times() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0)
        assertApproxEquals(r, p1 * p2, 1e-10)
        assertApproxEquals(r, p2 * p1, 1e-10)
        val pol1 = Polynom(50.0, 0.0, 0.0)
        val pol2 = Polynom(50.0, 0.0, 0.0)
        val res = Polynom(2500.0, 0.0, 0.0, 0.0, 0.0)
        assertApproxEquals(Polynom(0.0), Polynom(50.0, 20.0, -5.0) * Polynom(0.0), 1e-10)
        assertApproxEquals(res, pol1 * pol2, 1e-10)
    }

    @Test
    @Tag("Hard")
    fun div() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        assertApproxEquals(r, p1 / p2, 1e-10)
        assertApproxEquals(Polynom(1.0, 2.0), Polynom(2.0, 4.0) / Polynom(0.0, 2.0), 1e-10)
        assertThrows(IllegalArgumentException::class.java) { Polynom(1.0, 1.0, 25.0) / Polynom(0.0) }
        assertApproxEquals(Polynom(0.0), Polynom(1.0, 2.0) / Polynom(1.0, 2.0, 3.0), 1e-10)
    }

    @Test
    @Tag("Hard")
    fun rem() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        val q = Polynom(12.0, 14.0)
        assertApproxEquals(q, p1 % p2, 1e-10)
        assertApproxEquals(p1, p2 * r + q, 1e-10)
        val pol1 = Polynom(1.0, -12.0, 0.0, -42.0)
        val pol2 = Polynom(1.0, -3.0)
        assertApproxEquals(Polynom(-123.0), pol1 % pol2, 1e-10)
        assertApproxEquals(Polynom(0.0), Polynom(2.0, 5.0, 1.0) % Polynom(2.0), 1e-10)
        assertApproxEquals(Polynom(1.0, 2.0), Polynom(1.0, 2.0) % Polynom(1.0, 2.0, 3.0), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun equals() {
        assertEquals(Polynom(1.0, 2.0, 3.0), Polynom(1.0, 2.0, 3.0))
        assertEquals(Polynom(0.0, 2.0, 3.0), Polynom(2.0, 3.0))
        assertNotEquals(Polynom(0.0, 2.0, 3.0, 0.0), Polynom(2.0, 3.0))
    }

    @Test
    @Tag("Normal")
    fun hashCodeTest() {
        assertEquals(Polynom(1.0, 2.0, 3.0).hashCode(), Polynom(1.0, 2.0, 3.0).hashCode())
        assertNotEquals(Polynom(0.0, 2.0, 3.0, 0.0).hashCode(), Polynom(2.0, 3.0).hashCode())
    }
}