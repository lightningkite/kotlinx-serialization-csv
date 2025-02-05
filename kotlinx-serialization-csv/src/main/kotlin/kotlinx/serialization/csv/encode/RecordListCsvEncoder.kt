package kotlinx.serialization.csv.encode

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder

/**
 * Encodes list of multiple CSV records/lines.
 */
@OptIn(ExperimentalSerializationApi::class)
internal class RecordListCsvEncoder(
    csv: Csv,
    writer: CsvWriter
) : CsvEncoder(csv, writer, null) {

    override fun beginStructure(
        descriptor: SerialDescriptor
    ): CompositeEncoder {
        // For complex records: Begin a new record and end it in [endChildStructure]
        if (config.hasHeaderRecord && writer.isFirstRecord) {
            printHeaderRecord(descriptor)
        }
        writer.beginRecord()
        return super.beginStructure(descriptor)
    }

    override fun endChildStructure(descriptor: SerialDescriptor) {
        // For complex records: End the record here
        writer.endRecord()
    }

    override fun encodeCollectionSize(collectionSize: Int) {
        // Collection records do not write their size.
        // Instead the size is implicitly determined by reading until end-of-line.
    }

    override fun encodeColumn(value: String, isNumeric: Boolean, isNull: Boolean) {
        // For simple one-column records: Begin and end record here
        writer.beginRecord()
        super.encodeColumn(value, isNumeric, isNull)
        writer.endRecord()
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (config.deferToFormatWhenVariableColumns != null) {
            when (serializer.descriptor.kind) {
                is StructureKind.LIST,
                is StructureKind.MAP,
                is PolymorphicKind.OPEN -> {
                    encodeColumn(
                        value = config.deferToFormatWhenVariableColumns!!.encodeToString(serializer, value),
                        isNumeric = false,
                        isNull = false
                    )
                    return
                }
                else -> {}
            }
        }
        serializer.serialize(this, value)
    }
}
