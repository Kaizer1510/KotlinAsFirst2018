@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf("января", "февраля", "марта", "апреля",
        "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")

fun dateDecomp(string: String, separator: Char): List<Int> {
    if (!string.matches(Regex("""^\d{1,2}((\.\d{2}\.)|(\s[а-я]+\s))\d+$"""))) throw Exception()
    val elements = string.split(separator).toMutableList()
    val m = months.indexOf(elements[1]) + 1
    if (m != 0) elements[1] = m.toString()
    val result = elements.map { it.toInt() }
    if (result[0] > daysInMonth(result[1], result[2])) throw Exception()
    else return result
}

fun dateStrToDigit(str: String): String = try {
    val (d, m, y) = dateDecomp(str, ' ')
    String.format("%02d.%02d.%d", d, m, y)
} catch (e: Exception) {
    ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String = try {
    val (day, m, year) = dateDecomp(digital, '.')
    val month = months[m - 1]
    String.format("%d %s %d", day, month, year)
} catch (e: Exception) {
    ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String =
        if (phone.matches(Regex("""^(\+\d+)?[\s-]*(\([\d\s-]+\))?[\s-]*(\d+[\s-]*)+\d*$""")))
            Regex("""[()\-\s]""").replace(phone, "")
        else ""

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (jumps.contains(Regex("""[^\d\s\-%]"""))) return -1
    var c = -1
    val k = Regex("""[^\d\s]""").replace(jumps, "").split(" ")
    for (i in k) {
        if (i != "" && i.toInt() > c) c = i.toInt()
    }
    return c
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (jumps.contains(Regex("""[^\d\s+%\-]"""))) return -1
    var c = -1
    val k = Regex("""(\d+\s%*-?\s)|(\d+\s%+-?$)""")
            .replace(jumps, "").split(Regex("""\s%*\+\s?"""))
    for (i in k) {
        if (i != "" && i.toInt() > c) c = i.toInt()
    }
    return c
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int =
        if (!expression.matches(Regex("""^(\d+\s[+\-]\s)*\d+$""")))
            throw IllegalArgumentException()
        else Regex("""(?<=[+\-])\s""").replace(expression, "")
                .split(Regex("""\s""")).sumBy { it.toInt() }


/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    if (!str.contains(" ")) return -1
    val listR = Regex("""(\S+(?=\s))|((?<=\s)\S+)""").findAll(str).toList()
    val list = str.split(Regex("""\s""")).map { it.toLowerCase() }
    for (i in 1 until list.size)
        if (list[i - 1] == list[i]) return listR[i - 1].range.first
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""^(\S+\s\d+(\.\d+)?(;\s)?)+$"""))) return ""
    val priceL = description.split(Regex("""(;\s)?\S+(?<!;)\s"""))
    var price = 0.0
    var c = "0"
    for (i in 0 until priceL.size) {
        if (priceL[i] != "" && price < priceL[i].toDouble()) {
            price = priceL[i].toDouble()
            c = priceL[i]
        }
    }
    return Regex("""\S+(?=\s$c)""").find(description)!!.value
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = try {
    val romanSigns = mapOf('I' to 1, 'V' to 5, 'X' to 10,
            'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
    val sign = roman.toList()
    var sum = 0
    for (i in 1 until sign.size) {
        if (romanSigns[sign[i - 1]]!! < romanSigns[sign[i]]!!)
            sum -= romanSigns[sign[i - 1]]!!
        else sum += romanSigns[sign[i - 1]]!!
    }
    val result = sum + romanSigns[sign.last()]!!
    if (roman != lesson4.task1.roman(result)) -1
    else result
} catch (e: Exception) {
    -1
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = Array(cells) { 0 }
    if (commands.isEmpty()) return result.toList()
    if (commands.contains(Regex("""[^\-\s+\[><\]]"""))) throw IllegalArgumentException()
    val indexOpen = mutableListOf<Int>()
    val indexClose = mutableListOf<Int>()
    for (i in 0 until commands.length)
        when {
            commands[i] == '[' -> indexOpen += i
            commands[i] == ']' -> indexClose += i
        }
    val nesting = mutableMapOf<Int, Int>()
    when {
        indexOpen.size != indexClose.size -> throw IllegalArgumentException()
        indexOpen.isEmpty() -> Double.NaN
        indexOpen.size == 1 && indexClose[0] > indexOpen[0] -> nesting[indexClose[0]] = indexOpen[0]
        indexClose[0] < indexOpen[0] -> throw IllegalArgumentException()
        else -> {
            for (i in 0 until indexClose.size)
                loop@ for (c in indexOpen.size - 1 downTo 1)
                    when {
                        indexClose[i] < indexOpen[c - 1] -> Double.NaN
                        indexClose[i] < indexOpen[c] && !nesting.containsValue(indexOpen[c - 1]) -> {
                            nesting[indexClose[i]] = indexOpen[c - 1]
                            break@loop
                        }
                        indexClose[i] < indexOpen[c] -> Double.NaN
                        !nesting.containsValue(indexOpen[c]) -> {
                            nesting[indexClose[i]] = indexOpen[c]
                            break@loop
                        }
                        c == 1 -> nesting[indexClose[i]] = indexOpen[c - 1]
                    }
            if (nesting.size != indexOpen.size) throw IllegalArgumentException()
        }
    }
    val invN = nesting.map { it.value to it.key }.toMap()
    var i = cells / 2
    var k = 0
    for (c in 1..limit) {
        val q = k
        when {
            commands[k] == '>' -> i++
            commands[k] == '<' -> i--
            commands[k] == '+' -> result[i]++
            commands[k] == '-' -> result[i]--
            result[i] == 0 && commands[k] == '[' -> k = invN[k]!! + 1
            result[i] != 0 && commands[k] == ']' -> {
                k = nesting[k]!! + 1
                if (k == q) k--
            }
        }
        if (k == q) k++
        if (i == cells || i < 0) throw IllegalStateException()
        if (k > commands.length - 1) break
    }
    return result.toList()
}
