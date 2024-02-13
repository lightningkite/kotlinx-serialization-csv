package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.records.IntRecord
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with different [CsvConfig.recordSeparator]s.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvRecordSeparatorTest {

    @Test
    fun testDefault():Unit = Csv.assertEncodeAndDecode(
        "1\n2\n3",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testWindows():Unit = Csv {
        recordSeparator = "\n"
    }.assertEncodeAndDecode(
        "1\n2\n3",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testUnix():Unit = Csv {
        recordSeparator = "\n"
    }.assertEncodeAndDecode(
        "1\n2\n3",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testHash():Unit = Csv {
        recordSeparator = "#"
    }.assertEncodeAndDecode(
        "1#2#3",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )
}