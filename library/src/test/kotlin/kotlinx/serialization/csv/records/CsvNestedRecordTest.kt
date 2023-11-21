package kotlinx.serialization.csv.records

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.test.assertEncodeAndDecode
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class CsvNestedRecordTest {

    @Test
    fun testNestedRecord() = Csv.assertEncodeAndDecode(
        "0,Alice,0.0,1.0,100,info",
        NestedRecord(
            0,
            "Alice",
            Data(
                Location(
                    0.0,
                    1.0
                ), 100, "info"
            ),
            "Albert"
        ),
        NestedRecord.serializer()
    )

    @Test
    fun testNestedRecordList() = Csv.assertEncodeAndDecode(
        "0,Alice,0.0,1.0,100,info\n1,Bob,10.0,20.0,50,info2",
        listOf(
            NestedRecord(
                0,
                "Alice",
                Data(
                    Location(
                        0.0,
                        1.0
                    ), 100, "info"
                ),
                "Albert"
            ),
            NestedRecord(
                1,
                "Bob",
                Data(
                    Location(
                        10.0,
                        20.0
                    ), 50, "info2"
                ),
                "Bob"
            )
        ),
        ListSerializer(NestedRecord.serializer())
    )

    @Test
    fun testNestedRecordWithHeader() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "time,name,data.location.lat,data.location.lon,data.speed,data.info\n0,Alice,0.0,1.0,100,info",
        NestedRecord(
            0,
            "Alice",
            Data(
                Location(
                    0.0,
                    1.0
                ), 100, "info"
            ),
            "Albert"
        ),
        NestedRecord.serializer()
    )

    @Test
    fun testNestedRecordListWithHeader() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "time,name,data.location.lat,data.location.lon,data.speed,data.info\n0,Alice,0.0,1.0,100,info\n1,Bob,10.0,20.0,50,info2",
        listOf(
            NestedRecord(
                0,
                "Alice",
                Data(
                    Location(
                        0.0,
                        1.0
                    ), 100, "info"
                ),
                "Albert"
            ),
            NestedRecord(
                1,
                "Bob",
                Data(
                    Location(
                        10.0,
                        20.0
                    ), 50, "info2"
                ),
                "Bill"
            )
        ),
        ListSerializer(NestedRecord.serializer())
    )

    @Test
    fun testNestedRecordNullableListWithHeader() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "time,name,data,data.location,data.location.lat,data.location.lon,data.speed,data.info,alternative\n0,Alice,true,true,0.0,1.0,100,info,Albert\n1,Bob,false,,,,,,Bill\n3,Charlie,true,false,,,120,info3,Celina",
        listOf(
            NestedRecordWithNullableField(
                0,
                "Alice",
                DataWithNullableField(
                    Location(
                        0.0,
                        1.0
                    ), 100, "info"
                ),
                "Albert"
            ),
            NestedRecordWithNullableField(
                1,
                "Bob",
                null,
                "Bill"
            ),
            NestedRecordWithNullableField(
                3,
                "Charlie",
                DataWithNullableField(
                    null, 120,  "info3"
                ),
                "Celina"
            )
        ),
        ListSerializer(NestedRecordWithNullableField.serializer()),
        printResult = true
    )

    @Test
    fun testNestedRecordNullableListWithoutHeader() = Csv {
        hasHeaderRecord = false
    }.assertEncodeAndDecode(
        "0,Alice,true,true,0.0,1.0,100,info,Albert\n1,Bob,false,,,,,,Bill\n3,Charlie,true,false,,,120,info3,Celina",
        listOf(
            NestedRecordWithNullableField(
                0,
                "Alice",
                DataWithNullableField(
                    Location(
                        0.0,
                        1.0
                    ), 100, "info"
                ),
                "Albert"
            ),
            NestedRecordWithNullableField(
                1,
                "Bob",
                null,
                "Bill"
            ),
            NestedRecordWithNullableField(
                3,
                "Charlie",
                DataWithNullableField(
                    null, 120,  "info3"
                ),
                "Celina"
            )
        ),
        ListSerializer(NestedRecordWithNullableField.serializer()),
        printResult = true
    )
}

//Headers(map={0=0, 1=1, 2=2}, subHeaders={2=Headers(map={0=0, 1=1, 2=2}, subHeaders={0=Headers(map={0=0, 1=1}, subHeaders={})})})
//Headers(map={0=0, 1=1, 2=2, 3=2}, subHeaders={2=Headers(map={0=0, 1=0, 2=1, 3=2}, subHeaders={0=Headers(map={0=0, 1=1}, subHeaders={})})})
