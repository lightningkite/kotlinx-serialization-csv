package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.records.Enum
import kotlinx.serialization.csv.records.IntRecord
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class CsvHasTrailingDelimiterTest {

    @Test
    fun testDefault():Unit = Csv.assertEncodeAndDecode(
        "1",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testWithoutTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = false
    }.assertEncodeAndDecode(
        "1",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "1,",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testWithTrailingDelimiterAndHeaderRecord():Unit = Csv {
        hasTrailingDelimiter = true
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "a,\n1,",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testListWithoutTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = false
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
    fun testListWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "1,\n2,\n3,",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testListWithTrailingDelimiterAndHeaderRecord():Unit = Csv {
        hasTrailingDelimiter = true
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "a,\n1,\n2,\n3,",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testPrimitiveWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "1,",
        1,
        Int.serializer()
    )

    @Test
    fun testPrimitiveListWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "-150,\n150,\n42,",
        listOf(-150, 150, 42),
        ListSerializer(Int.serializer())
    )

    @Test
    fun testEnumWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "FIRST,",
        Enum.FIRST,
        Enum.serializer()
    )

    @Test
    fun testEnumListWithTrailingDelimiter():Unit = Csv {
        hasTrailingDelimiter = true
    }.assertEncodeAndDecode(
        "FIRST,\nFIRST,",
        listOf(
            Enum.FIRST,
            Enum.FIRST
        ),
        ListSerializer(Enum.serializer())
    )
}