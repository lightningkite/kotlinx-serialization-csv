package kotlinx.serialization.csv.decode

import kotlin.test.*

class StringSourceTest {

    @Test
    fun testCanRead():Unit {
        val source = StringSource("")
        assertTrue(source.canRead())
    }

    @Test
    fun testNotCanRead():Unit {
        val source = StringSource("")
        source.read()
        assertFalse(source.canRead())
    }

    @Test
    fun testRead():Unit {
        val source = StringSource("abc")
        assertEquals('a', source.read())
        assertEquals('b', source.read())
        assertEquals('c', source.read())
        assertNull(source.read())
    }

    @Test
    fun testReadEof():Unit {
        val source = StringSource("")
        assertNull(source.read())
    }

    @Test
    fun testPeek():Unit {
        val source = StringSource("abc")
        assertEquals('a', source.peek())
    }

    @Test
    fun testPeekMultipleTimes():Unit {
        val source = StringSource("abc")
        assertEquals('a', source.peek())
        assertEquals('a', source.peek())
        assertEquals('a', source.peek())
    }

    @Test
    fun testPeekEof():Unit {
        val source = StringSource("")
        assertNull(source.peek())
    }

    @Test
    fun testMarkUnmark():Unit {
        val source = StringSource("abc")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.read())
        source.unmark()
        assertEquals('c', source.read())
    }

    @Test
    fun testMarkMarkUnmarkReset():Unit {
        val source = StringSource("0123456789")
        assertEquals('0', source.read())
        source.mark()
        assertEquals('1', source.read())
        source.mark()
        assertEquals('2', source.read())
        source.unmark()
        assertEquals('3', source.read())
        source.reset()
        assertEquals('1', source.read())
    }

    @Test
    fun testMarkReset():Unit {
        val source = StringSource("abc")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.read())
        source.reset()
        assertEquals('b', source.read())
    }

    @Test
    fun testMarkResetMultiple():Unit {
        val source = StringSource("0123456789")
        assertEquals('0', source.read())
        source.mark()
        assertEquals('1', source.read())
        source.mark()
        assertEquals('2', source.read())
        source.reset()
        assertEquals('2', source.read())
        source.reset()
        assertEquals('1', source.read())
    }

    @Test
    fun testToString():Unit {
        val source = StringSource("abc")
        assertTrue(source.toString().contains("StringSource"))
    }
}