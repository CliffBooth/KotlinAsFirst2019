@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

    private var list = listOf<Double>() //хранит коэфициенты у икса в степени, соответсвующей индексу

    private constructor (l: List<Double>) : this(*l.reversed().toDoubleArray())

    init {
        if (coeffs.isEmpty()) throw IllegalArgumentException("пустой полином")
        val list3 = mutableListOf<Double>()
        for (c in coeffs)
            list3.add(c)
        while (list3.first() == 0.0 && list3.size > 1)
            list3.removeAt(0)
        list = list3.reversed()
    }


    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = list[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var sum = 0.0
        for (i in list.indices)
            sum += x.pow(i) * coeff(i)
        return sum
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = list.size - 1

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val res = if (list.size > other.list.size) list.toMutableList() else other.list.toMutableList()
        val smallerList = if (list.size <= other.list.size) list else other.list
        for (i in smallerList.indices)
            res[i] += smallerList[i]
        return Polynom(res)
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom {
        val res = mutableListOf<Double>()
        for (i in list.indices)
            res += -list[i]
        return Polynom(res)
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        //полином в виде карты, где key = степень икса value = коэфициент перед иксом
        val map = mutableMapOf<Int, Double>()
        for (i in list.indices) {
            for (j in other.list.indices)
                map[i + j] = map.getOrDefault(i + j, 0.0) + list[i] * other.list[j]
        }
        return mapToPolynom(map)
    }

    private fun mapToPolynom(map: MutableMap<Int, Double>): Polynom {
        val l = mutableListOf<Double>()
        for (i in 0..map.keys.max()!!)
            l.add(map.getOrDefault(i, 0.0))
        return Polynom(l)
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        require(other.list != listOf(0.0))
        if (other.list.size > list.size)
            return Polynom(0.0)
        var dividend = this
        val res = mutableMapOf<Int, Double>()
        while (dividend.degree() >= other.degree() && dividend != Polynom(0.0)) {
            val s = mutableMapOf<Int, Double>()
            s[dividend.degree() - other.degree()] = dividend.list.last() / other.list.last()
            res[dividend.degree() - other.degree()] = dividend.list.last() / other.list.last()
            dividend -= (mapToPolynom(s) * other)
        }
        return mapToPolynom(res)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = this - other * (this / other)

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && list == other.list

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        return list.hashCode()
    }
}
