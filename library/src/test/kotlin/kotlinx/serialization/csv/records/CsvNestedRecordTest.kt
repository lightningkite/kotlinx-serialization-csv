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
            )
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
            )
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

    @Test
    fun testNestedRecordNullableListWithHeader() = Csv {
        hasHeaderRecord = true
    }.assertEncodeAndDecode(
        "time,name,data,data.location,data.location.lat,data.location.lon,data.speed,data.info,data2.location.lat,data2.location.lon,data2.speed,data2.info,alternative\n" +
                "0,Alice,true,true,0.0,1.0,100,info,0.0,1.0,100,info,Albert\n" +
                "1,Bob,false,,,,,,10.0,20.0,50,info2,Bill\n" +
                "3,Charlie,true,false,,,120,,2.0,3.0,101,info3,Celina",
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
                Data(
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
                Data(
                    Location(
                        10.0,
                        20.0
                    ), 50, "info2"
                ),
                "Bill"
            ),
            NestedRecordWithNullableField(
                3,
                "Charlie",
                DataWithNullableField(
                    null, 120,  null
                ),
                Data(
                    Location(
                        2.0,
                        3.0
                    ), 101, "info3"
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
                "0,Alice,true,true,0.0,1.0,100,info,0.0,1.0,100,info,Albert\n" +
                "1,Bob,false,,,,,,10.0,20.0,50,info2,Bill\n" +
                "3,Charlie,true,false,,,120,info3,2.0,3.0,101,info3,Celina",
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
                Data(
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
                Data(
                    Location(
                        10.0,
                        20.0
                    ), 50, "info2"
                ),
                "Bill"
            ),
            NestedRecordWithNullableField(
                3,
                "Charlie",
                DataWithNullableField(
                    null, 120,  "info3"
                ),
                Data(
                    Location(
                        2.0,
                        3.0
                    ), 101, "info3"
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
