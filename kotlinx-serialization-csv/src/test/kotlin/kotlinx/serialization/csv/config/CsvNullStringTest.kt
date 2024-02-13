package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.records.NullRecord
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with different [CsvConfig.nullString]s.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvNullStringTest {

    @Test
    fun testDefault():Unit = Csv.assertEncodeAndDecode(
        "",
        NullRecord(null),
        NullRecord.serializer()
    )

    @Test
    fun testEmpty():Unit = Csv {
        nullString = ""
    }.assertEncodeAndDecode(
        "",
        NullRecord(null),
        NullRecord.serializer()
    )

    @Test
    fun testNull():Unit = Csv {
        nullString = "null"
    }.assertEncodeAndDecode(
        "null",
        NullRecord(null),
        NullRecord.serializer()
    )

    @Test
    fun testNA():Unit = Csv {
        nullString = "N/A"
    }.assertEncodeAndDecode(
        "N/A",
        NullRecord(null),
        NullRecord.serializer()
    )
}
