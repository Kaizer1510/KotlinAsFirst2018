@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.PI
import kotlin.math.sqrt
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var count = 1
    var number = abs(n)
    while (number >= 10) {
        count++
        number /= 10
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 1
    var b = 0
    var c: Int
    for (i in 2..n) {
        c = a
        a += b
        b = c
    }
    return a
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var factor1 = 1
    var factor2 = 1
    val d = max(m, n)
    val c = min(m, n)
    var a = d
    var b = c
    while (a != b) if (b > a) a = d * factor2++ else b = c * factor1++
    return a
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv = n
    var k = 1
    return if (n % 2 == 0) 2
    else {
        for (div in 3..sqrt(n.toDouble()).toInt() step 2) {
            minDiv = div
            if (n % div == 0) {
                k = 0
                break
            }
        }
        if (k == 0) minDiv else n
    }
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var div = 1
    var i = n / 2
    while (i > 1) {
        if (n % i == 0) break
        div += 2
        i = n / div
    }
    return i
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val k = min(m, n)
    var c = 1
    when {
        k == 1 -> Double.NaN
        m % 2 == 0 && n % 2 == 0 || max(m, n) % k == 0 -> c = 2
        else -> for (i in 3..sqrt(k.toDouble()).toInt() step 2)
            if (m % i == 0 && n % i == 0) {
                c = k
                break
            }
    }
    return c == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var c = 0
    if (0 in m..n) c = 1
    else
        for (i in m..n)
            if (sqr(sqrt(i.toDouble()).toInt()) == i) {
                c = 1
                break
            }
    return c == 1
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var c = x
    var i = 0
    while (c != 1) {
        if (c % 2 == 0) c /= 2 else c = c * 3 + 1
        i++
    }
    return i
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun pow(x: Double, y: Int): Double {
    var result = x
    return when {
        x == 1.0 -> 1.0
        x == 0.0 -> 0.0
        y == 0 -> 1.0
        else -> {
            for (i in 1 until y) result *= x
            result
        }
    }
}

fun sin(x: Double, eps: Double): Double {
    val y = x % (2 * PI)
    var member: Double
    var t = 3
    var sinX = y
    var c = -1
    do {
        member = c * pow(y, t) / factorial(t)
        sinX += member
        t += 2
        if (c == 1) c -= 2 else c += 2
    } while (abs(member) > eps)
    return sinX
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val y = x % (2 * PI)
    var member: Double
    var t = 2
    var sinX = 1.0
    var c = -1
    do {
        member = c * pow(y, t) / factorial(t)
        sinX += member
        t += 2
        if (c == 1) c -= 2 else c += 2
    } while (abs(member) > eps)
    return sinX
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun pow(x: Int, y: Int): Int {
    var result = x
    return when {
        x == 1 -> 1
        x == 0 -> 0
        y == 0 -> 1
        else -> {
            for (i in 1 until y) result *= x
            result
        }
    }
}

fun revert(n: Int): Int {
    val num = digitNumber(n)
    var c = num - 1
    var r = n
    for (i in 0 until num) {
        r = r - n / pow(10, c) % 10 * pow(10, c) + n / pow(10, i) % 10 * pow(10, c)
        c--
    }
    return r
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    val num = digitNumber(n)
    var c = num - 1
    var num1: Int
    var num2: Int
    var k = 1
    for (i in 0 until num) {
        num1 = n / pow(10, c) % 10
        num2 = n / pow(10, i) % 10
        if (num1 != num2) {
            k = 0
            break
        }
        c--
    }
    return k == 1
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val num = digitNumber(n)
    val num1 = n % 10
    var numI: Int
    var k = 0
    for (i in 1 until num) {
        numI = n / pow(10, i) % 10
        if (numI != num1) {
            k = 1
            break
        }
    }
    return k == 1
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var num = 0
    var count = 0
    for (i in 1..n) {
        num = sqr(i)
        count += digitNumber(num)
        if (count >= n) break
    }
    return num / pow(10, (count - n)) % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var num = 0
    var count = 0
    for (i in 1..n) {
        num = fib(i)
        count += digitNumber(num)
        if (count >= n) break
    }
    return num / pow(10, (count - n)) % 10
}
