package kotlinx.serialization.csv.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.decode.CsvReader
import kotlinx.serialization.csv.decode.StringSource
import kotlinx.serialization.csv.records.*
import kotlinx.serialization.test.assertDecode
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class CsvHasHeaderRecordTest {

    @Test
    fun testDefault() = Csv.assertEncodeAndDecode(
        "1",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testWithoutHeaderRecords() = Csv {
        hasHeaderRecord = false
    }.assertEncodeAndDecode(
        "1",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testWithHeaderRecords() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "a\n1",
        IntRecord(1),
        IntRecord.serializer()
    )

    @Test
    fun testListWithoutHeaderRecords() = Csv {
        hasHeaderRecord = false
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
    fun testListWithHeaderRecords() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "a\n1\n2\n3",
        listOf(
            IntRecord(1),
            IntRecord(2),
            IntRecord(3)
        ),
        ListSerializer(IntRecord.serializer())
    )

    @Test
    fun testMultipleColumns() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "a,b\n1,testing",
        IntStringRecord(1, "testing"),
        IntStringRecord.serializer()
    )

    @Test
    fun testMultipleColumnsTrim() = Csv {
        hasHeaderRecord = true
        trimUnquotedWhitespace = true
    }.assertEncodeAndDecode(
        "a,b\n1,\" testing \"",
        IntStringRecord(1, " testing "),
        IntStringRecord.serializer()
    )

    @Test
    fun testSerialName() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "first,second\n1,2",
        SerialNameRecord(1, 2),
        SerialNameRecord.serializer()
    )

    @Test
    fun testMultipleColumnsReordered() = Csv {
        hasHeaderRecord = true
    }.assertDecode(
        "b,a\ntesting,1",
        IntStringRecord(1, "testing"),
        IntStringRecord.serializer()
    )

    @Test
    fun testListMultipleColumnsReordered() = Csv {
        hasHeaderRecord = true
    }.assertDecode(
        "b,a\ntesting,1\nbar,2",
        listOf(
            IntStringRecord(1, "testing"),
            IntStringRecord(2, "bar")
        ),
        ListSerializer(IntStringRecord.serializer())
    )

    @Test
    fun testNestedRecordWithHeaderReordered() = Csv {
        hasHeaderRecord = true
    }.assertDecode(
        "time,data.location.lon,data.location.lat,data.info,data.speed,name\n0,1.0,0.0,info,100,Alice",
        NestedRecord(
            0,
            "Alice",
            Data(
                Location(
                    0.0,
                    1.0
                ), 100, "info"
            )
        ),
        NestedRecord.serializer()
    )

    @Test
    fun testNestedRecordListWithHeaderReordered() = Csv {
        hasHeaderRecord = true
    }.assertDecode(
        "time,name,data.location.lon,data.location.lat,data.speed,data.info\n0,Alice,1.0,0.0,100,info\n1,Bob,20.0,10.0,50,info2",
        listOf(
            NestedRecord(
                0,
                "Alice",
                Data(
                    Location(
                        0.0,
                        1.0
                    ), 100, "info"
                )
            ),
            NestedRecord(
                1,
                "Bob",
                Data(
                    Location(
                        10.0,
                        20.0
                    ), 50, "info2"
                )
            )
        ),
        ListSerializer(NestedRecord.serializer())
    )

    @Serializable
    data class CsvIra(
        @SerialName("Account Number") val accountNumber: String,
        @SerialName("Plan Type") val planType: String,
        @SerialName("Product") val product: String,
        @SerialName("Status") val status: String,
        @SerialName("Issued Effective Date") val issued: String,
        @SerialName("Closed / Redeemed Date") val closed: String? = null,
        @SerialName("Current Balance") val balance: Double,
        @SerialName("Primary Customer Key") val primaryCustomerKey: String,
        @SerialName("Short Name") val shortName: String,
        @SerialName("Referral Code") val referralCode: Int? = null,
        @SerialName("Spousal Consent") val spousalConsent: String? = null,
        @SerialName("Non-Account Owner Spouse") val spousalConsentName: String? = null,
        @SerialName("Relationship Type") val relationshipType: String,
        @SerialName("Ownership Type") val ownershipType: String,
        @SerialName("Related Customer Key") val relatedCustomerKey: String? = null,
        @SerialName("Customer Name") val relatedCustomerName: String,
        @SerialName("Percentage") val ratio: Double? = null,
        @SerialName("Document Role") val documentRole: String? = null,
    )

    @Test
    fun testEndingBlanks() = Csv {
        hasHeaderRecord = true
        delimiter = '\t'
    }.let {
        val data = """
Account Number	Plan Type	Product	Status	Issued Effective Date	Closed / Redeemed Date	Current Balance	Primary Customer Key	Short Name	Related Customer Key	Customer Name	Referral Code	Spousal Consent	Non-Account Owner Spouse	Relationship Type	Ownership Type	Related Customer Key	Percentage	Document Role
11819	Traditional IRA	Self Directed IRA CF	Redeemed Account	2018-07-12 00:00:00	2022-02-25 00:00:00	0	00000000007420	REED, KREIGHTON GRAY	00000000014353	SARAH  REED	0022			Beneficiary Primary	Other		1	PBN
11819	Traditional IRA	Self Directed IRA CF	Redeemed Account	2018-07-12 00:00:00	2022-02-25 00:00:00	0	00000000007420	REED, KREIGHTON GRAY	00000000007420	KREIGHTON GRAY REED	0022			Account Owner	Direct		0	ACO
11819	Traditional IRA	Self Directed IRA CF	Redeemed Account	2018-07-12 00:00:00	2022-02-25 00:00:00	0	00000000007420	REED, KREIGHTON GRAY	00000000009986	ABC TRUST	0022			ENTITY TO INDIVIDUAL	Not Applicable	00000000007420		
11827	Roth IRA	Self Directed IRA CF	Redeemed Account	2018-11-29 00:00:00	2022-12-05 00:00:00	0	00000000007452	MAY, MARTIN PAUL	00000000007452	MARTIN PAUL MAY	0022			Account Owner	Direct		0	ACO
        """.trimIndent()
        CsvReader(StringSource(data), it.config).let {
            var currentRecord = 0
            val records = ArrayList<List<String>>()
            var current = ArrayList<String>()
            while (!it.isDone) {
                if(currentRecord != it.recordNo) {
                    records.add(current)
                    current = ArrayList()
                    currentRecord = it.recordNo
                }
                current.add(it.readColumn())
            }
            records.add(current)
            println(records.map { it.size })
        }
        it.decodeFromString(ListSerializer(CsvIra.serializer()), data)
        Unit
    }
}
