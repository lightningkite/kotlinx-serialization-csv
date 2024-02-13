package kotlinx.serialization.csv.decode

import java.io.StringReader
import kotlin.test.*

class FetchSourceTest {
    
    fun make(string: String): FetchSource {
        return FetchSource(StringReader(string))
    }

    @Test
    fun testCanRead():Unit {
        val source = make("")
        assertTrue(source.canRead())
    }

    @Test
    fun testNotCanRead():Unit {
        val source = make("")
        source.read()
        assertFalse(source.canRead())
    }

    @Test
    fun testRead():Unit {
        val source = make("abc")
        assertEquals('a', source.read())
        assertEquals('b', source.read())
        assertEquals('c', source.read())
        assertNull(source.read())
    }

    @Test
    fun testReadEof():Unit {
        val source = make("")
        assertNull(source.read())
    }

    @Test
    fun testPeek():Unit {
        val source = make("abc")
        assertEquals('a', source.peek())
    }

    @Test
    fun testPeekMultipleTimes():Unit {
        val source = make("abc")
        assertEquals('a', source.peek())
        assertEquals('a', source.peek())
        assertEquals('a', source.peek())
    }

    @Test
    fun testPeekEof():Unit {
        val source = make("")
        assertNull(source.peek())
    }

    @Test
    fun testMarkUnmark():Unit {
        val source = make("abc")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.read())
        source.unmark()
        assertEquals('c', source.read())
    }

    @Test
    fun testMarkMarkUnmarkReset():Unit {
        val source = make("0123456789")
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
        val source = make("abc")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.read())
        source.reset()
        assertEquals('b', source.read())
    }

    @Test
    fun testMarkResetMultiple():Unit {
        val source = make("abcdef")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.read())
        source.mark()
        assertEquals('c', source.read())
        source.reset()
        assertEquals('c', source.read())
        source.reset()
        assertEquals('b', source.read())
    }

    @Test
    fun testMarkPeekRead():Unit {
        val source = make("abc")
        assertEquals('a', source.read())
        source.mark()
        assertEquals('b', source.peek())
        assertEquals('b', source.read())
        source.reset()
        assertEquals('b', source.peek())
        assertEquals('b', source.read())
        source.mark()
        assertEquals('c', source.peek())
        assertEquals('c', source.read())
        source.reset()
        assertEquals('c', source.peek())
        assertEquals('c', source.read())
    }
}