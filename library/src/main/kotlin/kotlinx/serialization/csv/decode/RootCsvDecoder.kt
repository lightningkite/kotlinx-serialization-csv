package kotlinx.serialization.csv.decode

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE

/**
 * Initial entry point for decoding.
 *
 * This root decoder handles the case that the first level is a list
 * (which is interpreted as multiple CSV records/lines). If this is the case, decoding continues in
 * [RecordListCsvDecoder].
 */
@OptIn(ExperimentalSerializationApi::class)
internal class RootCsvDecoder(
    csv: Csv,
    reader: CsvReader
) : CsvDecoder(csv, reader, null) {

    private var position = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        return if (reader.isDone) DECODE_DONE else position
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        return when (descriptor.kind) {
            StructureKind.LIST ->
                // Top level list is treated as list of multiple records
                RecordListCsvDecoder(csv, reader)

            else -> {
                // Top level is treated as one single record
                readHeaders(descriptor)
                super.beginStructure(descriptor)
            }
        }
    }

    override fun endChildStructure(descriptor: SerialDescriptor) {
        super.endChildStructure(descriptor)
        readTrailingDelimiter()
    }

    override fun decodeColumn(): String {
        val value = super.decodeColumn()
        position++
        readTrailingDelimiter()
        return value
    }

    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return if (config.deferToFormatWhenVariableColumns != null) {
            when (deserializer.descriptor.kind) {
                is PolymorphicKind.OPEN -> {
                    config.deferToFormatWhenVariableColumns!!.decodeFromString(deserializer, decodeColumn())
                }
                else -> deserializer.deserialize(this)
            }
        } else deserializer.deserialize(this)
    }
}
