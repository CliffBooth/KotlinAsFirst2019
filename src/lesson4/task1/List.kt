@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.digitNumber
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.isEmpty()) return 0.0
    return list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val x = mean(list)
    for (i in 0 until list.size) list[i] -= x
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in 0 until a.size) c += a[i] * b[i]
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty()) return 0
    var y = 0.0
    var a = 0.0
    for (i in 0 until p.size) {
        a += p[i] * (x.toDouble()).pow(y)
        y++
    }
    return a.toInt()
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if ((list.isEmpty()) || (list.size == 1)) return list
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var y = 2
    var x = n
    val a = mutableListOf<Int>()
    if (n <= 3) {
        a.add(n)
        return a
    }
    while (x >= 2) {
        while ((x % y) == 0) {
            x /= y
            a.add(y)
        }
        y += 1
    }
    return a
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String  {
    var y = 2
    var x = n
    val a = mutableListOf<Int>()
    if (n <= 3) {
        a.add(n)
        return a.joinToString (separator = "*")
    }
    while (x >= 2) {
        while ((x % y) == 0) {
            x /= y
            a.add(y)
        }
        y += 1
    }
    return a.joinToString (separator = "*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val a = mutableListOf<Int>()
    var x = n
    while (x > 1) {
        a.add(x % base)
        x /= base
    }
    if (x == 1) a.add(1)
    return a.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val a = mutableListOf<Int>()
    var x = n
    while (x > 1) {
        a.add(x % base)
        x /= base
    }
    if (x == 1) a.add(1)
    val b = a.reversed().toMutableList<Any>()
    for (i in 0 until a.size) {
        if (a.reversed()[i] > 9) {
            when (a.reversed()[i]) {
                10 -> b[i] = "a"
                11 -> b[i] = "b"
                12 -> b[i] = "c"
                13 -> b[i] = "d"
                14 -> b[i] = "e"
                15 -> b[i] = "f"
                16 -> b[i] = "g"
                17 -> b[i] = "h"
                18 -> b[i] = "i"
                19 -> b[i] = "j"
                20 -> b[i] = "k"
                21 -> b[i] = "l"
                22 -> b[i] = "m"
                23 -> b[i] = "n"
                24 -> b[i] = "o"
                25 -> b[i] = "p"
                26 -> b[i] = "q"
                27 -> b[i] = "r"
                28 -> b[i] = "s"
                29 -> b[i] = "t"
                30 -> b[i] = "u"
                31 -> b[i] = "v"
                32 -> b[i] = "w"
                33 -> b[i] = "x"
                34 -> b[i] = "w"
                35 -> b[i] = "z"
            }
        }
    }
    return b.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var y = 0.0
    val a = digits.reversed().toMutableList()
    var x = 0
    var c = 0
    for (i in 0 until a.size) {
        c = a[i] * (base.toDouble().pow(y)).toInt()
        y++
        x += c
    }
    return x
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val a = mutableListOf<Int>()
    for (i in 0 until str.length) {
            when (str[i]) {
                '0' -> a.add(i, 0)
                '1' -> a.add(i, 1)
                '2' -> a.add(i, 2)
                '3' -> a.add(i, 3)
                '4' -> a.add(i, 4)
                '5' -> a.add(i, 5)
                '6' -> a.add(i, 6)
                '7' -> a.add(i, 7)
                '8' -> a.add(i, 8)
                '9' -> a.add(i, 9)
                'a' -> a.add(i, 10)
                'b' -> a.add(i, 11)
                'c' -> a.add(i, 12)
                'd' -> a.add(i, 13)
                'e' -> a.add(i, 14)
                'f' -> a.add(i, 15)
                'g' -> a.add(i, 16)
                'h' -> a.add(i, 17)
                'i' -> a.add(i, 18)
                'j' -> a.add(i, 19)
                'k' -> a.add(i, 20)
                'l' -> a.add(i, 21)
                'm' -> a.add(i, 22)
                'n' -> a.add(i, 23)
                'o' -> a.add(i, 24)
                'p' -> a.add(i, 25)
                'q' -> a.add(i, 26)
                'r' -> a.add(i, 27)
                's' -> a.add(i, 28)
                't' -> a.add(i, 29)
                'u' -> a.add(i, 30)
                'v' -> a.add(i, 31)
                'w' -> a.add(i, 32)
                'x' -> a.add(i, 33)
                'y' -> a.add(i, 34)
                'z' -> a.add(i, 35)
        }
    }
    return decimal(a, base)
}
/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String = TODO()

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String = TODO()