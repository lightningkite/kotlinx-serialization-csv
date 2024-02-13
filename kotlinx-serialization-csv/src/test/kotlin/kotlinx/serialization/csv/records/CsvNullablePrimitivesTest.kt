package kotlinx.serialization.csv.records

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

/**
 * Test [Csv] with simple nullable primitive records.
 */
@OptIn(ExperimentalSerializationApi::class)
class CsvNullablePrimitivesTest {

    @Test
    fun testByte():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Byte.serializer().nullable
    )

    @Test
    fun testShort():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Short.serializer().nullable
    )

    @Test
    fun testInt():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Int.serializer().nullable
    )

    @Test
    fun testLong():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Long.serializer().nullable
    )

    @Test
    fun testFloat():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Float.serializer().nullable
    )

    @Test
    fun testDouble():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Double.serializer().nullable
    )

    @Test
    fun testBooleanTrue():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Boolean.serializer().nullable
    )

    @Test
    fun testBooleanFalse():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Boolean.serializer().nullable
    )

    @Test
    fun testChar():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Char.serializer().nullable
    )

    @Test
    fun testString():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        String.serializer().nullable
    )

    @Test
    fun testUnit():Unit = Csv.assertEncodeAndDecode(
        "",
        null,
        Unit.serializer().nullable
    )

    @Test
    fun testIntList():Unit = Csv {
        ignoreEmptyLines = false
    }.assertEncodeAndDecode(
        "-150\n\n42",
        listOf(-150, null, 42),
        ListSerializer(Int.serializer().nullable)
    )
}
