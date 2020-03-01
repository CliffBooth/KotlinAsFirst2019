@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    private val entries = mutableSetOf<Person>()

    private val namePattern = Regex("""^[A-ZА-Я][а-яa-z]* [A-ZА-Я][а-яa-z]*$""")
    private val phonePattern = Regex("""^\+?[\d*#-]+$""")

    private data class Person(val name: String) {
        val phones = mutableSetOf<String>()
    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        require(name.matches(namePattern))
        for (person in entries) {
            if (person.name == name)
                return false
        }
        entries.add(Person(name))
        return true
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        for (person in entries) {
            if (person.name == name)
                break
            return false
        }
        entries.remove(Person(name))
        return true
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        require(phone.matches(phonePattern))
        for (person in entries) {
            if (person.phones.contains(phone))
                return false
        }
        for (person in entries) {
            if (person.name == name) {
                person.phones.add(phone)
                break
            }
        }
        return true
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        for (person in entries) {
            if (person.name == name) {
                if (!person.phones.contains(phone))
                    return false
                person.phones.remove(phone)
                return true
            }
        }
        return false
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        for (person in entries) {
            if (person.name == name)
                return person.phones
        }
        return setOf()
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for (person in entries) {
            if (person.phones.contains(phone))
                return person.name
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneBook || entries.size != other.entries.size) return false
        var n = false
        for (person in entries) {
            for (person2 in other.entries) {
                if (person.name == person2.name && person.phones == person2.phones) {
                    n = true
                    break
                }
            }
            if (!n) return n
        }
        return true
    }

    override fun hashCode(): Int {
        return entries.hashCode()
    }
}