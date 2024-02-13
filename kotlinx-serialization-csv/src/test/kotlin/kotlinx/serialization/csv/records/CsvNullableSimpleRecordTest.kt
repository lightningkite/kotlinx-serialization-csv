package kotlinx.serialization.csv.records

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.test.assertDecode
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with simple nullable [Serializable] records.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvNullableSimpleRecordTest {

    @Test
    fun testInteger():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        IntRecord.serializer().nullable
    )

    @Test
    fun testByte():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        ByteRecord.serializer().nullable
    )

    @Test
    fun testShort():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        ShortRecord.serializer().nullable
    )

    @Test
    fun testLong():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        LongRecord.serializer().nullable
    )

    @Test
    fun testFloat():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        FloatRecord.serializer().nullable
    )

    @Test
    fun testDouble():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        DoubleRecord.serializer().nullable
    )

    @Test
    fun testBoolean():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        BooleanRecord.serializer().nullable
    )

    @Test
    fun testChar():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        CharRecord.serializer().nullable
    )

    @Test
    fun testString():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        StringRecord.serializer().nullable
    )

    @Test
    fun testNull():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        NullRecord.serializer().nullable
    )

    @Test
    fun testUnit():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        UnitRecord.serializer().nullable
    )

    @Test
    fun testEnum():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        EnumRecord.serializer().nullable
    )

    @Test
    fun testComplex():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        ComplexRecord.serializer().nullable
    )

    @Test
    fun testIntList():Unit = Csv {
        ignoreEmptyLines = false
    }.assertEncodeAndDecode(
        "-150\n\n150",
        listOf(
            IntRecord(-150),
            null,
            IntRecord(150)
        ),
        ListSerializer(IntRecord.serializer().nullable)
    )

    @Test
    fun testIntListWithLastLineEmpty():Unit = Csv {
        ignoreEmptyLines = false
    }.assertDecode(
        "-150\n\n150\n",
        listOf(
            IntRecord(-150),
            null,
            IntRecord(150)
        ),
        ListSerializer(IntRecord.serializer().nullable)
    )

    @Test
    fun testIntListWithLastTwoLinesEmpty():Unit = Csv {
        ignoreEmptyLines = false
    }.assertDecode(
        "-150\n\n150\n\n",
        listOf(
            IntRecord(-150),
            null,
            IntRecord(150),
            null
        ),
        ListSerializer(IntRecord.serializer().nullable)
    )
}
