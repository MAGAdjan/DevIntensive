package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.extensions.truncate
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun truncateTest() {
        /* skillBranch tests */
        assertEquals("Bender Bending Ro...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        assertEquals("Bender Bending R...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        assertEquals("A", "A     ".truncate(3))

        /* additional tests */
        assertEquals("too lo...", "   too long line with lots of spaces before".truncate(5))
        assertEquals("too short", "too short".truncate(20))
        assertEquals("12345", "12345".truncate(4))
        assertEquals("1234...", "12345".truncate(3))
        assertEquals("12345", "12345  ".truncate(4))
        assertEquals("tab", "tab    ".truncate(4))
        assertEquals("dots......", "dots... a lot".truncate(6))
    }

    @Test
    fun test_humanizeDiff() {
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND)))
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())


        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())

        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())





        assertEquals("несколько секунд назад", Date().humanizeDiff(Date().add(34, TimeUnits.SECOND)))
        assertEquals("минуту назад", Date().humanizeDiff(Date().add(61, TimeUnits.SECOND)))
        assertEquals("5 минут назад", Date().humanizeDiff(Date().add(5, TimeUnits.MINUTE)))
        assertEquals("20 дней назад", Date().humanizeDiff(Date().add(20, TimeUnits.DAY)))
        assertEquals("90 дней назад", Date().humanizeDiff(Date().add(90, TimeUnits.DAY)))
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-13, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-63, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-1, TimeUnits.MINUTE)))
        assertEquals("через 29 дней", Date().humanizeDiff(Date().add(-29, TimeUnits.DAY)))
        assertEquals("только что", Date().humanizeDiff(Date().add(0, TimeUnits.HOUR)))
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //несколько секунд назад
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff()) //2 часа назад
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff()) //5 дней назад
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff()) //через 2 минуты
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff()) //через 7 дней
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff()) //более года назад
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff()) //более чем через год
        assertEquals("только что", Date().humanizeDiff())
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //через несколько секунд
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())

        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())
    }
}
