package kotlinx.serialization.csv.encode

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder

/**
 * Initial entry point for encoding.
 *
 * This root encoder handles the case that the first level is a list
 * (which is interpreted as multiple CSV records/lines). If this is the case, encoding continues in
 * [RecordListCsvEncoder].
 */
@OptIn(ExperimentalSerializationApi::class)
internal class RootCsvEncoder(
    csv: Csv,
    writer: CsvWriter
) : CsvEncoder(csv, writer, null) {

    internal constructor(csv: Csv, output: Appendable) :
            this(csv, CsvWriter(output, csv.config))

    override fun beginCollection(
        descriptor: SerialDescriptor,
        collectionSize: Int
    ): CompositeEncoder {
        return if (descriptor.kind == StructureKind.LIST) {
            RecordListCsvEncoder(csv, writer)
        } else {
            super.beginCollection(descriptor, collectionSize)
        }
    }

    override fun beginStructure(
        descriptor: SerialDescriptor
    ): CompositeEncoder {
        if (config.hasHeaderRecord && writer.isFirstRecord) {
            printHeaderRecord(descriptor)
        }
        writer.beginRecord()
        return super.beginStructure(descriptor)
    }

    override fun endChildStructure(descriptor: SerialDescriptor) {
        writer.endRecord()
    }

    override fun encodeColumn(value: String, isNumeric: Boolean, isNull: Boolean) {
        writer.beginRecord()
        super.encodeColumn(value, isNumeric, isNull)
        writer.endRecord()
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (config.deferToFormatWhenVariableColumns != null) {
            when (serializer.descriptor.kind) {
                is PolymorphicKind.OPEN -> {
                    encodeColumn(
                        value = config.deferToFormatWhenVariableColumns!!.encodeToString(serializer, value),
                        isNumeric = false,
                        isNull = false
                    )
                }
                else -> serializer.serialize(this, value)
            }
        } else serializer.serialize(this, value)
    }
}
