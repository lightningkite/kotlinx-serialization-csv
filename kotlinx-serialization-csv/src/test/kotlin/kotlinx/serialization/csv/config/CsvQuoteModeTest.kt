package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.config.QuoteMode.*
import kotlinx.serialization.csv.records.*
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class CsvQuoteModeTest {

    // TestString

    @Test
    fun test_None_WithString_NotRequiringQuotes():Unit = 
        testString(NONE, "testing", "testing")

    @Test
    fun test_None_WithString_RequiringQuotes():Unit = 
        testString(NONE, "a,b\nc\\d\"e", "a\\,b\\nc\\\\d\\\"e")

    @Test
    fun test_NonNumeric_WithString_NotRequiringQuotes():Unit = 
        testString(ALL_NON_NUMERIC, "testing", "\"testing\"")

    @Test
    fun test_NonNumeric_WithString_RequiringQuotes():Unit = 
        testString(ALL_NON_NUMERIC, "a,b\nc\\d\"e", "\"a,b\nc\\d\"\"e\"")

    @Test
    fun test_AllNonNull_WithString_NotRequiringQuotes():Unit = 
        testString(ALL_NON_NULL, "testing", "\"testing\"")

    @Test
    fun test_AllNonNull_WithString_RequiringQuotes():Unit = 
        testString(ALL_NON_NULL, "a,b\nc\\d\"e", "\"a,b\nc\\d\"\"e\"")

    @Test
    fun test_Minimal_WithString_NotRequiringQuotes():Unit = 
        testString(MINIMAL, "testing", "testing")

    @Test
    fun test_Minimal_WithString_NotRequiringQuotes2():Unit = 
        testString(MINIMAL, "test\\ing", "test\\ing")

    @Test
    fun test_Minimal_WithString_NotRequiringQuotes3():Unit = 
        testString(MINIMAL, "test\ring", "test\ring")

    @Test
    fun test_Minimal_WithString_RequiringQuotes():Unit = 
        testString(MINIMAL, "test,ing", "\"test,ing\"")

    @Test
    fun test_Minimal_WithString_RequiringQuotes2():Unit = 
        testString(MINIMAL, "test\ning", "\"test\ning\"")

    @Test
    fun test_Minimal_WithString_RequiringQuotes3():Unit = 
        testString(MINIMAL, "test\"ing", "\"test\"\"ing\"")

    @Test
    fun test_Minimal_WithString_RequiringQuotes4():Unit = 
        testString(MINIMAL, "a,b\nc\\d\"e", "\"a,b\nc\\d\"\"e\"")

    @Test
    fun test_All_WithString_NotRequiringQuotes():Unit = 
        testString(ALL, "testing", "\"testing\"")

    @Test
    fun test_All_WithString_RequiringQuotes():Unit = 
        testString(ALL, "a,b\nc\\d\"e", "\"a,b\nc\\d\"\"e\"")

    // TestByte

    @Test
    fun test_None_WithByte_NotRequiringQuotes():Unit = 
        testByte(NONE, 44, "44")

    @Test
    fun test_None_WithByte_RequiringQuotes():Unit = 
        testByte(NONE, 123, "\\1\\23")

    @Test
    fun test_NonNumeric_WithByte_NotRequiringQuotes():Unit = 
        testByte(ALL_NON_NUMERIC, 44, "44")

    @Test
    fun test_NonNumeric_WithByte_RequiringQuotes():Unit = 
        testByte(ALL_NON_NUMERIC, 123, "\"123\"")

    @Test
    fun test_AllNonNull_WithByte_NotRequiringQuotes():Unit = 
        testByte(ALL_NON_NULL, 44, "\"44\"")

    @Test
    fun test_AllNonNull_WithByte_RequiringQuotes():Unit = 
        testByte(ALL_NON_NULL, 123, "\"123\"")

    @Test
    fun test_Minimal_WithByte_NotRequiringQuotes():Unit = 
        testByte(MINIMAL, 44, "44")

    @Test
    fun test_Minimal_WithByte_RequiringQuotes():Unit = 
        testByte(MINIMAL, 123, "\"123\"")

    @Test
    fun test_All_WithByte_NotRequiringQuotes():Unit = 
        testByte(ALL, 44, "\"44\"")

    @Test
    fun test_All_WithByte_RequiringQuotes():Unit = 
        testByte(ALL, 123, "\"123\"")

    // TestShort

    @Test
    fun test_None_WithShort_NotRequiringQuotes():Unit = 
        testShort(NONE, 44, "44")

    @Test
    fun test_None_WithShort_RequiringQuotes():Unit = 
        testShort(NONE, 123, "\\1\\23")

    @Test
    fun test_NonNumeric_WithShort_NotRequiringQuotes():Unit = 
        testShort(ALL_NON_NUMERIC, 44, "44")

    @Test
    fun test_NonNumeric_WithShort_RequiringQuotes():Unit = 
        testShort(ALL_NON_NUMERIC, 123, "\"123\"")

    @Test
    fun test_AllNonNull_WithShort_NotRequiringQuotes():Unit = 
        testShort(ALL_NON_NULL, 44, "\"44\"")

    @Test
    fun test_AllNonNull_WithShort_RequiringQuotes():Unit = 
        testShort(ALL_NON_NULL, 123, "\"123\"")

    @Test
    fun test_Minimal_WithShort_NotRequiringQuotes():Unit = 
        testShort(MINIMAL, 44, "44")

    @Test
    fun test_Minimal_WithShort_RequiringQuotes():Unit = 
        testShort(MINIMAL, 123, "\"123\"")

    @Test
    fun test_All_WithShort_NotRequiringQuotes():Unit = 
        testShort(ALL, 44, "\"44\"")

    @Test
    fun test_All_WithShort_RequiringQuotes():Unit = 
        testShort(ALL, 123, "\"123\"")

    // TestInt

    @Test
    fun test_None_WithInt_NotRequiringQuotes():Unit = 
        testInt(NONE, 44, "44")

    @Test
    fun test_None_WithInt_RequiringQuotes():Unit = 
        testInt(NONE, 123, "\\1\\23")

    @Test
    fun test_NonNumeric_WithInt_NotRequiringQuotes():Unit = 
        testInt(ALL_NON_NUMERIC, 44, "44")

    @Test
    fun test_NonNumeric_WithInt_RequiringQuotes():Unit = 
        testInt(ALL_NON_NUMERIC, 123, "\"123\"")

    @Test
    fun test_AllNonNull_WithInt_NotRequiringQuotes():Unit = 
        testInt(ALL_NON_NULL, 44, "\"44\"")

    @Test
    fun test_AllNonNull_WithInt_RequiringQuotes():Unit = 
        testInt(ALL_NON_NULL, 123, "\"123\"")

    @Test
    fun test_Minimal_WithInt_NotRequiringQuotes():Unit = 
        testInt(MINIMAL, 44, "44")

    @Test
    fun test_Minimal_WithInt_RequiringQuotes():Unit = 
        testInt(MINIMAL, 123, "\"123\"")

    @Test
    fun test_All_WithInt_NotRequiringQuotes():Unit = 
        testInt(ALL, 44, "\"44\"")

    @Test
    fun test_All_WithInt_RequiringQuotes():Unit = 
        testInt(ALL, 123, "\"123\"")

    // TestLong

    @Test
    fun test_None_WithLong_NotRequiringQuotes():Unit = 
        testLong(NONE, 44, "44")

    @Test
    fun test_None_WithLong_RequiringQuotes():Unit = 
        testLong(NONE, 123, "\\1\\23")

    @Test
    fun test_NonNumeric_WithLong_NotRequiringQuotes():Unit = 
        testLong(ALL_NON_NUMERIC, 44, "44")

    @Test
    fun test_NonNumeric_WithLong_RequiringQuotes():Unit = 
        testLong(ALL_NON_NUMERIC, 123, "\"123\"")

    @Test
    fun test_AllNonNull_WithLong_NotRequiringQuotes():Unit = 
        testLong(ALL_NON_NULL, 44, "\"44\"")

    @Test
    fun test_AllNonNull_WithLong_RequiringQuotes():Unit = 
        testLong(ALL_NON_NULL, 123, "\"123\"")

    @Test
    fun test_Minimal_WithLong_NotRequiringQuotes():Unit = 
        testLong(MINIMAL, 44, "44")

    @Test
    fun test_Minimal_WithLong_RequiringQuotes():Unit = 
        testLong(MINIMAL, 123, "\"123\"")

    @Test
    fun test_All_WithLong_NotRequiringQuotes():Unit = 
        testLong(ALL, 44, "\"44\"")

    @Test
    fun test_All_WithLong_RequiringQuotes():Unit = 
        testLong(ALL, 123, "\"123\"")

    // TestFloat

    @Test
    fun test_None_WithFloat_NotRequiringQuotes():Unit = 
        testFloat(NONE, 44f, "44.0")

    @Test
    fun test_None_WithFloat_RequiringQuotes():Unit = 
        testFloat(NONE, 123f, "\\1\\23.0")

    @Test
    fun test_NonNumeric_WithFloat_NotRequiringQuotes():Unit = 
        testFloat(ALL_NON_NUMERIC, 44f, "44.0")

    @Test
    fun test_NonNumeric_WithFloat_RequiringQuotes():Unit = 
        testFloat(ALL_NON_NUMERIC, 123f, "\"123.0\"")

    @Test
    fun test_AllNonNull_WithFloat_NotRequiringQuotes():Unit = 
        testFloat(ALL_NON_NULL, 44f, "\"44.0\"")

    @Test
    fun test_AllNonNull_WithFloat_RequiringQuotes():Unit = 
        testFloat(ALL_NON_NULL, 123f, "\"123.0\"")

    @Test
    fun test_Minimal_WithFloat_NotRequiringQuotes():Unit = 
        testFloat(MINIMAL, 44f, "44.0")

    @Test
    fun test_Minimal_WithFloat_RequiringQuotes():Unit = 
        testFloat(MINIMAL, 123f, "\"123.0\"")

    @Test
    fun test_All_WithFloat_NotRequiringQuotes():Unit = 
        testFloat(ALL, 44f, "\"44.0\"")

    @Test
    fun test_All_WithFloat_RequiringQuotes():Unit = 
        testFloat(ALL, 123f, "\"123.0\"")

    // TestDouble

    @Test
    fun test_None_WithDouble_NotRequiringQuotes():Unit = 
        testDouble(NONE, 44.0, "44.0")

    @Test
    fun test_None_WithDouble_RequiringQuotes():Unit = 
        testDouble(NONE, 123.0, "\\1\\23.0")

    @Test
    fun test_NonNumeric_WithDouble_NotRequiringQuotes():Unit = 
        testDouble(ALL_NON_NUMERIC, 44.0, "44.0")

    @Test
    fun test_NonNumeric_WithDouble_RequiringQuotes():Unit = 
        testDouble(ALL_NON_NUMERIC, 123.0, "\"123.0\"")

    @Test
    fun test_AllNonNull_WithDouble_NotRequiringQuotes():Unit = 
        testDouble(ALL_NON_NULL, 44.0, "\"44.0\"")

    @Test
    fun test_AllNonNull_WithDouble_RequiringQuotes():Unit = 
        testDouble(ALL_NON_NULL, 123.0, "\"123.0\"")

    @Test
    fun test_Minimal_WithDouble_NotRequiringQuotes():Unit = 
        testDouble(MINIMAL, 44.0, "44.0")

    @Test
    fun test_Minimal_WithDouble_RequiringQuotes():Unit = 
        testDouble(MINIMAL, 123.0, "\"123.0\"")

    @Test
    fun test_All_WithDouble_NotRequiringQuotes():Unit = 
        testDouble(ALL, 44.0, "\"44.0\"")

    @Test
    fun test_All_WithDouble_RequiringQuotes():Unit = 
        testDouble(ALL, 123.0, "\"123.0\"")

    // TestBoolean

    @Test
    fun test_None_WithBoolean_NotRequiringQuotes():Unit = 
        testBoolean(NONE, true, "true")

    @Test
    fun test_None_WithBoolean_RequiringQuotes():Unit = 
        testBoolean(NONE, false, "\\f\\alse")

    @Test
    fun test_NonNumeric_WithBoolean_NotRequiringQuotes():Unit = 
        testBoolean(ALL_NON_NUMERIC, true, "\"true\"")

    @Test
    fun test_NonNumeric_WithBoolean_RequiringQuotes():Unit = 
        testBoolean(ALL_NON_NUMERIC, false, "\"false\"")

    @Test
    fun test_AllNonNull_WithBoolean_NotRequiringQuotes():Unit = 
        testBoolean(ALL_NON_NULL, true, "\"true\"")

    @Test
    fun test_AllNonNull_WithBoolean_RequiringQuotes():Unit = 
        testBoolean(ALL_NON_NULL, false, "\"false\"")

    @Test
    fun test_Minimal_WithBoolean_NotRequiringQuotes():Unit = 
        testBoolean(MINIMAL, true, "true")

    @Test
    fun test_Minimal_WithBoolean_RequiringQuotes():Unit = 
        testBoolean(MINIMAL, false, "\"false\"")

    @Test
    fun test_All_WithBoolean_NotRequiringQuotes():Unit = 
        testBoolean(ALL, true, "\"true\"")

    @Test
    fun test_All_WithBoolean_RequiringQuotes():Unit = 
        testBoolean(ALL, false, "\"false\"")

    // TestNull

    @Test
    fun test_None_WithNull_NotRequiringQuotes():Unit = 
        testNull(NONE, "null")

    @Test
    fun test_None_WithNull_RequiringQuotes():Unit = 
        testNullRequiringQuotes(NONE, "n\\u\\l\\l")

    @Test
    fun test_NonNumeric_WithNull_NotRequiringQuotes():Unit = 
        testNull(ALL_NON_NUMERIC, "\"null\"")

    @Test
    fun test_NonNumeric_WithNull_RequiringQuotes():Unit = 
        testNullRequiringQuotes(ALL_NON_NUMERIC, "\"null\"")

    @Test
    fun test_AllNonNull_WithNull_NotRequiringQuotes():Unit = 
        testNull(ALL_NON_NULL, "null")

    @Test
    fun test_AllNonNull_WithNull_RequiringQuotes():Unit = 
        testNullRequiringQuotes(ALL_NON_NULL, "\"null\"")

    @Test
    fun test_Minimal_WithNull_NotRequiringQuotes():Unit = 
        testNull(MINIMAL, "null")

    @Test
    fun test_Minimal_WithNull_RequiringQuotes():Unit = 
        testNullRequiringQuotes(MINIMAL, "\"null\"")

    @Test
    fun test_All_WithNull_NotRequiringQuotes():Unit = 
        testNull(ALL, "\"null\"")

    @Test
    fun test_All_WithNull_RequiringQuotes():Unit = 
        testNullRequiringQuotes(ALL, "\"null\"")

    // TestUnit

    @Test
    fun test_None_WithUnit_NotRequiringQuotes():Unit = 
        testUnit(NONE, "kotlin.Unit")

    @Test
    fun test_None_WithUnit_RequiringQuotes():Unit = 
        testUnitRequiringQuotes(NONE, "kotl\\in.\\Un\\it")

    @Test
    fun test_NonNumeric_WithUnit_NotRequiringQuotes():Unit = 
        testUnit(ALL_NON_NUMERIC, "\"kotlin.Unit\"")

    @Test
    fun test_NonNumeric_WithUnit_RequiringQuotes():Unit = 
        testUnitRequiringQuotes(ALL_NON_NUMERIC, "\"kotlin.Unit\"")

    @Test
    fun test_AllNonNull_WithUnit_NotRequiringQuotes():Unit = 
        testUnit(ALL_NON_NULL, "\"kotlin.Unit\"")

    @Test
    fun test_AllNonNull_WithUnit_RequiringQuotes():Unit = 
        testUnitRequiringQuotes(ALL_NON_NULL, "\"kotlin.Unit\"")

    @Test
    fun test_Minimal_WithUnit_NotRequiringQuotes():Unit = 
        testUnit(MINIMAL, "kotlin.Unit")

    @Test
    fun test_Minimal_WithUnit_RequiringQuotes():Unit = 
        testUnitRequiringQuotes(MINIMAL, "\"kotlin.Unit\"")

    @Test
    fun test_All_WithUnit_NotRequiringQuotes():Unit = 
        testUnit(ALL, "\"kotlin.Unit\"")

    @Test
    fun test_All_WithUnit_RequiringQuotes():Unit = 
        testUnitRequiringQuotes(ALL, "\"kotlin.Unit\"")


    private fun testString(mode: QuoteMode, value: String, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
        }.assertEncodeAndDecode(
            expected,
            StringRecord(value),
            StringRecord.serializer()
        )

    private fun testByte(mode: QuoteMode, value: Byte, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            ByteRecord(value),
            ByteRecord.serializer()
        )

    private fun testShort(mode: QuoteMode, value: Short, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            ShortRecord(value),
            ShortRecord.serializer()
        )

    private fun testInt(mode: QuoteMode, value: Int, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            IntRecord(value),
            IntRecord.serializer()
        )

    private fun testLong(mode: QuoteMode, value: Long, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            LongRecord(value),
            LongRecord.serializer()
        )

    private fun testFloat(mode: QuoteMode, value: Float, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            FloatRecord(value),
            FloatRecord.serializer()
        )

    private fun testDouble(mode: QuoteMode, value: Double, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = '1'
            recordSeparator = "2"
        }.assertEncodeAndDecode(
            expected,
            DoubleRecord(value),
            DoubleRecord.serializer()
        )

    private fun testBoolean(
        mode: QuoteMode,
        value: Boolean,
        expected: String
    ) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = 'f'
            recordSeparator = "a"
        }.assertEncodeAndDecode(
            expected,
            BooleanRecord(value),
            BooleanRecord.serializer()
        )

    private fun testNull(mode: QuoteMode, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            nullString = "null"
        }.assertEncodeAndDecode(
            expected,
            NullRecord(null),
            NullRecord.serializer()
        )

    private fun testNullRequiringQuotes(mode: QuoteMode, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            nullString = "null"
            delimiter = 'u'
            recordSeparator = "l"
        }.assertEncodeAndDecode(
            expected,
            NullRecord(null),
            NullRecord.serializer()
        )

    private fun testUnit(mode: QuoteMode, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
        }.assertEncodeAndDecode(
            expected,
            UnitRecord(Unit),
            UnitRecord.serializer()
        )

    private fun testUnitRequiringQuotes(mode: QuoteMode, expected: String) =
        Csv {
            quoteMode = mode
            escapeChar = if (quoteMode == NONE) '\\' else null
            delimiter = 'U'
            recordSeparator = "i"
        }.assertEncodeAndDecode(
            expected,
            UnitRecord(Unit),
            UnitRecord.serializer()
        )
}