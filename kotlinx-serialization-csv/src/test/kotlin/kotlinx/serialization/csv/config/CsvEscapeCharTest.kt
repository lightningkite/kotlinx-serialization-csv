package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.config.QuoteMode.NONE
import kotlinx.serialization.csv.records.StringRecord
import kotlinx.serialization.test.assertDecode
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with different [CsvConfig.escapeChar]s.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvEscapeCharTest {

    @Test
    fun testSlash():Unit = Csv {
        quoteMode = NONE
        escapeChar = '\\'
    }.assertEncodeAndDecode(
        "a\\\"b",
        StringRecord("a\"b"),
        StringRecord.serializer()
    )

    @Test
    fun testBang():Unit = Csv {
        quoteMode = NONE
        escapeChar = '!'
    }.assertEncodeAndDecode(
        "a!\"b",
        StringRecord("a\"b"),
        StringRecord.serializer()
    )

    @Test
    fun testEscapedDelimiter():Unit = Csv {
        quoteMode = NONE
        escapeChar = '\\'
    }.assertEncodeAndDecode(
        """test\,ing""",
        StringRecord("test,ing"),
        StringRecord.serializer()
    )

    @Test
    fun testEscapedEscapeChar():Unit = Csv {
        quoteMode = NONE
        escapeChar = '\\'
    }.assertEncodeAndDecode(
        """test\\ing""",
        StringRecord("""test\ing"""),
        StringRecord.serializer()
    )

    @Test
    fun testParseEscapedEscapeChar():Unit = Csv {
        escapeChar = '\\'
    }.assertDecode(
        """test\\ing""",
        StringRecord("""test\ing"""),
        StringRecord.serializer()
    )

    @Test
    fun testEscapedNewLine():Unit = Csv {
        quoteMode = NONE
        escapeChar = '\\'
    }.assertEncodeAndDecode(
        """test\ning""",
        StringRecord("test\ning"),
        StringRecord.serializer()
    )

    @Test
    fun testParseEscapedNewLine():Unit = Csv {
        escapeChar = '\\'
    }.assertDecode(
        """test\ning""",
        StringRecord("test\ning"),
        StringRecord.serializer()
    )

    @Test
    fun testTab():Unit = Csv {
        delimiter = '\t'
        quoteMode = NONE
        escapeChar = '\\'
    }.assertEncodeAndDecode(
        """test\ting""",
        StringRecord("test\ting"),
        StringRecord.serializer()
    )

    @Test
    fun testParseEscapedTab():Unit = Csv {
        escapeChar = '\\'
    }.assertDecode(
        """test\ting""",
        StringRecord("test\ting"),
        StringRecord.serializer()
    )
}
