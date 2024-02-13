package kotlinx.serialization.csv.records

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.test.assertDecode
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with simple [Serializable] records.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvSimpleRecordTest {

    @Test
    fun testInteger():Unit = Csv.assertEncodeAndDecode(
        "-150",
        IntRecord(-150),
        IntRecord.serializer()
    )

    @Test
    fun testByte():Unit = Csv.assertEncodeAndDecode(
        "-15",
        ByteRecord(-15),
        ByteRecord.serializer()
    )

    @Test
    fun testShort():Unit = Csv.assertEncodeAndDecode(
        "-150",
        ShortRecord(-150),
        ShortRecord.serializer()
    )

    @Test
    fun testLong():Unit = Csv.assertEncodeAndDecode(
        "-150",
        LongRecord(-150),
        LongRecord.serializer()
    )

    @Test
    fun testFloat():Unit = Csv.assertEncodeAndDecode(
        "-150.0",
        FloatRecord(-150.0f),
        FloatRecord.serializer()
    )

    @Test
    fun testDouble():Unit = Csv.assertEncodeAndDecode(
        "-150.0",
        DoubleRecord(-150.0),
        DoubleRecord.serializer()
    )

    @Test
    fun testBoolean():Unit = Csv.assertEncodeAndDecode(
        "true",
        BooleanRecord(true),
        BooleanRecord.serializer()
    )

    @Test
    fun testChar():Unit = Csv.assertEncodeAndDecode(
        "t",
        CharRecord('t'),
        CharRecord.serializer()
    )

    @Test
    fun testString():Unit = Csv.assertEncodeAndDecode(
        "testing",
        StringRecord("testing"),
        StringRecord.serializer()
    )

    @Test
    fun testNull():Unit = Csv.assertEncodeAndDecode(
        "",
        NullRecord(null),
        NullRecord.serializer()
    )

    @Test
    fun testUnit():Unit = Csv.assertEncodeAndDecode(
        "kotlin.Unit",
        UnitRecord(Unit),
        UnitRecord.serializer()
    )

    @Test
    fun testEnum():Unit = Csv.assertEncodeAndDecode(
        "FIRST",
        EnumRecord(Enum.FIRST),
        EnumRecord.serializer()
    )

    @Test
    fun testComplex():Unit = Csv.assertEncodeAndDecode(
        "-150,-1,42,9223372036854775807,-2.0,24.24,true,testing,,kotlin.Unit,FIRST",
        ComplexRecord(
            -150,
            -1,
            42,
            Long.MAX_VALUE,
            -2f,
            24.24,
            true,
            "testing",
            null,
            Unit,
            Enum.FIRST
        ),
        ComplexRecord.serializer()
    )

    @Test
    fun testIntList():Unit = Csv.assertEncodeAndDecode(
        "-150\n150",
        listOf(
            IntRecord(-150),
            IntRecord(150)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testIntListWithLastLineEmpty() = Csv.assertDecode(
        "-150\n150\n",
        listOf(
            IntRecord(-150),
            IntRecord(150)
        ),
        ListSerializer(IntRecord.serializer())
    )
}
