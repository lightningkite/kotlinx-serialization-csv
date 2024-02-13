package kotlinx.serialization.csv.records

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with simple primitive records.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvPrimitivesTest {

    @Test
    fun testByte():Unit = Csv.assertEncodeAndDecode(
        "-123",
        -123,
        Byte.serializer()
    )

    @Test
    fun testShort():Unit = Csv.assertEncodeAndDecode(
        "-150",
        -150,
        Short.serializer()
    )

    @Test
    fun testInt():Unit = Csv.assertEncodeAndDecode(
        "-150",
        -150,
        Int.serializer()
    )

    @Test
    fun testLong():Unit = Csv.assertEncodeAndDecode(
        "-150",
        -150,
        Long.serializer()
    )

    @Test
    fun testFloat():Unit = Csv.assertEncodeAndDecode(
        "-150.0",
        -150f,
        Float.serializer()
    )

    @Test
    fun testDouble():Unit = Csv.assertEncodeAndDecode(
        "-150.0",
        -150.0,
        Double.serializer()
    )

    @Test
    fun testBooleanTrue():Unit = Csv.assertEncodeAndDecode(
        "true",
        true,
        Boolean.serializer()
    )

    @Test
    fun testBooleanFalse():Unit = Csv.assertEncodeAndDecode(
        "false",
        false,
        Boolean.serializer()
    )

    @Test
    fun testChar():Unit = Csv.assertEncodeAndDecode(
        "a",
        'a',
        Char.serializer()
    )

    @Test
    fun testString():Unit = Csv.assertEncodeAndDecode(
        "testing",
        "testing",
        String.serializer()
    )

    @Test
    fun testUnit():Unit = Csv.assertEncodeAndDecode(
        "kotlin.Unit",
        Unit,
        Unit.serializer()
    )

    @Test
    fun testIntList():Unit = Csv.assertEncodeAndDecode(
        "-150\n150\n42",
        listOf(-150, 150, 42),
        ListSerializer(Int.serializer())
    )
}
