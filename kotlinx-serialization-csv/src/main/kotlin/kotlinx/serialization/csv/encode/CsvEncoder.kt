package kotlinx.serialization.csv.encode

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.HeadersNotSupportedForSerialDescriptorException
import kotlinx.serialization.csv.UnsupportedSerialDescriptorException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule

/**
 * Default CSV encoder.
 */
@OptIn(ExperimentalSerializationApi::class)
internal abstract class CsvEncoder(
    protected val csv: Csv,
    protected val writer: CsvWriter,
    private val parent: CsvEncoder?
) : AbstractEncoder() {

    override val serializersModule: SerializersModule = csv.serializersModule

    protected val config
        get() = csv.config

    override fun beginCollection(
        descriptor: SerialDescriptor,
        collectionSize: Int,
    ): CompositeEncoder {
        encodeCollectionSize(collectionSize)
        return super.beginCollection(descriptor, collectionSize)
    }

    override fun beginStructure(
        descriptor: SerialDescriptor
    ): CompositeEncoder {
        return when (descriptor.kind) {
            StructureKind.LIST,
            StructureKind.MAP ->
                SimpleCsvEncoder(csv, writer, this)

            StructureKind.CLASS ->
                SimpleCsvEncoder(csv, writer, this)

            StructureKind.OBJECT ->
                ObjectCsvEncoder(csv, writer, this)

            PolymorphicKind.SEALED ->
                SealedCsvEncoder(csv, writer, this, descriptor)

            PolymorphicKind.OPEN ->
                SimpleCsvEncoder(csv, writer, this)

            else -> throw UnsupportedSerialDescriptorException(descriptor)
        }
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        parent?.endChildStructure(descriptor)
    }

    protected open fun endChildStructure(descriptor: SerialDescriptor) {
    }

    override fun encodeByte(value: Byte) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeShort(value: Short) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeInt(value: Int) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeLong(value: Long) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeFloat(value: Float) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeDouble(value: Double) {
        encodeColumn(value.toString(), isNumeric = true)
    }

    override fun encodeBoolean(value: Boolean) {
        encodeColumn(value.toString(), isNumeric = false)
    }

    override fun encodeChar(value: Char) {
        encodeColumn(value.toString())
    }

    override fun encodeString(value: String) {
        encodeColumn(value)
    }

    override fun encodeNull() {
        encodeColumn(config.nullString, isNull = true)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        encodeColumn(enumDescriptor.getElementName(index))
    }

    protected fun printHeaderRecord(descriptor: SerialDescriptor) {
        writer.beginRecord()
        printHeader("", descriptor)
        writer.endRecord()
    }

    private fun printHeader(prefix: String, descriptor: SerialDescriptor) {
        for (i in 0 until descriptor.elementsCount) {
            val name = prefix + descriptor.getElementName(i)
            val childDesc = descriptor.getElementDescriptor(i)

            when {
                (childDesc.kind is StructureKind.LIST ||
                childDesc.kind is StructureKind.MAP ||
                childDesc.kind is PolymorphicKind.OPEN) && config.deferToFormatWhenVariableColumns != null -> {
                    writer.printColumn(name)
                }

                // TODO Check
                childDesc.kind is SerialKind.CONTEXTUAL ->
                    writer.printColumn(name)

                // TODO Check
                childDesc.kind is SerialKind.ENUM ->
                    writer.printColumn(name)

                childDesc.kind is PolymorphicKind.SEALED -> {
                    writer.printColumn(name)
                    val headerSeparator = config.headerSeparator
                    printHeader("$name$headerSeparator", childDesc.getElementDescriptor(1))
                }
                childDesc.kind is StructureKind.OBJECT ->
                    Unit

                childDesc.elementsCount > 0 -> {
                    if(childDesc.isNullable) writer.printColumn(name)
                    val headerSeparator = config.headerSeparator
                    printHeader("$name$headerSeparator", childDesc)
                }

                else ->
                    writer.printColumn(name)
            }
        }
    }

    protected open fun encodeCollectionSize(collectionSize: Int) {
        writer.printColumn(collectionSize.toString(), isNumeric = true)
    }

    protected open fun encodeColumn(
        value: String,
        isNumeric: Boolean = false,
        isNull: Boolean = false
    ) {
        writer.printColumn(value, isNumeric, isNull)
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
        if(serializer.descriptor.isNullable && serializer.descriptor.kind == StructureKind.CLASS) {
            if(value == null) {
                encodeBoolean(false)
                encodeNulls(serializer.descriptor)
            } else {
                encodeBoolean(true)
                serializer.serialize(this, value)
            }
        } else {
            serializer.serialize(this, value)
        }
    }

    private fun encodeNulls(serializer: SerialDescriptor) {
        if(serializer.kind == StructureKind.CLASS) {
            for(index in (0 until serializer.elementsCount)) {
                val sub = serializer.getElementDescriptor(index)
                if(sub.isNullable && sub.kind == StructureKind.CLASS) {
                    encodeNull()
                }
                encodeNulls(sub)
            }
        } else {
            encodeNull()
        }
    }

    override fun <T : Any> encodeNullableSerializableValue(serializer: SerializationStrategy<T>, value: T?) {
        val isNullabilitySupported = serializer.descriptor.isNullable
        if (isNullabilitySupported) {
            // Instead of `serializer.serialize` to be able to intercept this
            return encodeSerializableValue(serializer as SerializationStrategy<T?>, value)
        }
        return encodeSerializableValue((serializer as KSerializer<T>).nullable, value)
    }
}
