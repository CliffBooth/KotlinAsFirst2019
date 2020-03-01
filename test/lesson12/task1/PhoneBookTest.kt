package lesson12.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PhoneBookTest {

    @Test
    fun addHuman() {
        val book = PhoneBook()
        assertThrows(IllegalArgumentException::class.java) { book.addHuman("вася") }
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertFalse(book.addHuman("Иванов Петр"))
    }

    @Test
    fun removeHuman() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.removeHuman("Иванов Петр"))
        assertFalse(book.removeHuman("Сидорова Мария"))
        assertFalse(book.removeHuman("Johnny"))
    }

    @Test
    fun addPhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertThrows(IllegalArgumentException::class.java) { book.addPhone("Иванов Петр", "абв") }
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+7921*123#4567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertFalse(book.addPhone("Иванов Петр", "+79211234567"))
        assertFalse(book.addPhone("Васильев Дмитрий", "+79211234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
    }

    @Test
    fun removePhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book.removePhone("Иванов Петр", "+78121234567"))
        assertFalse(book.removePhone("Иванов Петр", "+78121234567"))
        assertFalse(book.removePhone("Вася Васильев", "+7812123#4567"))
        assertTrue(book.removePhone("Васильев Дмитрий", "+79217654321"))
    }

    @Test
    fun phones() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertEquals(setOf("+79211234567", "+78121234567"), book.phones("Иванов Петр"))
        assertEquals(setOf<String>(), book.phones("Васильев Дмитрий"))
        assertEquals(setOf<String>(), book.phones("Mister X"))
    }

    @Test
    fun humanByPhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        assertEquals("Васильев Дмитрий", book.humanByPhone("+79217654321"))
        assertEquals("Иванов Петр", book.humanByPhone("+78121234567"))
        assertEquals("Иванов Петр", book.humanByPhone("+79211234567"))
        assertNull(book.humanByPhone("+78127654321"))
    }

    @Test
    fun testEquals() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        val book2 = PhoneBook()
        assertTrue(book2.addHuman("Васильев Дмитрий"))
        assertTrue(book2.addHuman("Иванов Петр"))
        assertTrue(book2.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book2.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book2.addPhone("Иванов Петр", "+79211234567"))
        val book3 = PhoneBook()
        assertTrue(book3.addHuman("Васильев Дмитрий"))
        assertTrue(book3.addHuman("Иванов Петр"))
        assertTrue(book3.addPhone("Иванов Петр", "+78121234568"))
        assertTrue(book3.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book3.addPhone("Иванов Петр", "+79211234567"))
        assertFalse(book == book3)
        assertTrue(book == book2)
        val book4 = PhoneBook()
        val book5 = PhoneBook()
        book4.addHuman("James J")
        book5.addHuman("James J")
        assertTrue(book4 == book5)
        book4.addPhone("James J", "+11")
        book5.addPhone("James J", "+12")
        assertFalse(book4 == book5)
    }

    @Test
    fun testHashCode() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        val book2 = PhoneBook()
        assertTrue(book2.addHuman("Васильев Дмитрий"))
        assertTrue(book2.addHuman("Иванов Петр"))
        assertTrue(book2.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book2.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book2.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.hashCode() == book2.hashCode())
    }
}