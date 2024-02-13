package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.records.IntStringRecord
import kotlinx.serialization.csv.records.NullRecord
import kotlinx.serialization.test.assertDecode
import kotlin.test.Test

/**
 * Test [Csv] with simple primitive records.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvEmptyLinesTest {

    @Test
    fun testIntList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n-150\n150\n\n42\n",
        listOf(-150, 150, 42),
        ListSerializer(Int.serializer())
    )

    @Test
    fun testEmptyIntList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n\n\n\n",
        listOf(),
        ListSerializer(Int.serializer())
    )

    @Test
    fun testLastLineAlwaysIgnored():Unit = Csv {
        ignoreEmptyLines = false
    }.assertDecode(
        "1\n2\n3\n4\n",
        listOf(1, 2, 3, 4),
        ListSerializer(Int.serializer())
    )

    @Test
    fun testRecordList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n\n1,testing\n\n2,bar\n\n",
        listOf(
            IntStringRecord(1, "testing"),
            IntStringRecord(2, "bar")
        ),
        ListSerializer(IntStringRecord.serializer())
    )

    @Test
    fun testEmptyRecordList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n\n\n\n",
        listOf(),
        ListSerializer(IntStringRecord.serializer()),
    )

    @Test
    fun testRecordListWithHeaders():Unit = Csv {
        ignoreEmptyLines = true
        hasHeaderRecord = true
    }.assertDecode(
        "\na,b\n\n1,testing\n\n2,bar\n\n",
        listOf(
            IntStringRecord(1, "testing"),
            IntStringRecord(2, "bar")
        ),
        ListSerializer(IntStringRecord.serializer())
    )

    @Test
    fun testEmptyRecordListWithHeaders():Unit = Csv {
        ignoreEmptyLines = true
        hasHeaderRecord = true
    }.assertDecode(
        "\na,b\n\n\n\n",
        listOf(),
        ListSerializer(IntStringRecord.serializer())
    )

    @Test
    fun testListOfIntList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n\n1,2\n\n3,4\n\n5,6,7\n",
        listOf(
            listOf(1, 2),
            listOf(3, 4),
            listOf(5, 6, 7)
        ),
        ListSerializer(ListSerializer(Int.serializer()))
    )

    @Test
    fun testEmptyListOfIntList():Unit = Csv {
        ignoreEmptyLines = true
    }.assertDecode(
        "\n\n\n\n",
        listOf(),
        ListSerializer(ListSerializer(Int.serializer()))
    )

    @Test
    fun testNullRecordList():Unit = Csv {
        ignoreEmptyLines = true
        nullString = "null"
    }.assertDecode(
        "\n\nnull\n\nnull\n\n",
        listOf(
            NullRecord(null),
            NullRecord(null)
        ),
        ListSerializer(NullRecord.serializer())
    )

    @Test
    fun testNullRecordListWithEmptyNullString():Unit = Csv {
        ignoreEmptyLines = true
        nullString = ""
    }.assertDecode(
        "\n\n\n\n\n\n",
        listOf(),
        ListSerializer(NullRecord.serializer())
    )
}
