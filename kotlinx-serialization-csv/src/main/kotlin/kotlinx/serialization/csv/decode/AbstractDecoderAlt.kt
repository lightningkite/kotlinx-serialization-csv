package kotlinx.serialization.csv.decode

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder

@ExperimentalSerializationApi
public abstract class AbstractDecoderAlt : Decoder, CompositeDecoder {

    /**
     * Invoked to decode a value when specialized `encode*` method was not overridden.
     */
    public open fun decodeValue(): Any = throw SerializationException("${this::class} can't retrieve untyped values")

    override fun decodeNotNullMark(): Boolean = true
    override fun decodeNull(): Nothing? = null
    override fun decodeBoolean(): Boolean = decodeValue() as Boolean
    override fun decodeByte(): Byte = decodeValue() as Byte
    override fun decodeShort(): Short = decodeValue() as Short
    override fun decodeInt(): Int = decodeValue() as Int
    override fun decodeLong(): Long = decodeValue() as Long
    override fun decodeFloat(): Float = decodeValue() as Float
    override fun decodeDouble(): Double = decodeValue() as Double
    override fun decodeChar(): Char = decodeValue() as Char
    override fun decodeString(): String = decodeValue() as String
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = decodeValue() as Int

    // overwrite by default
    public open fun <T : Any?> decodeSerializableValue(
        deserializer: DeserializationStrategy<T>,
        previousValue: T? = null
    ): T = decodeSerializableValue(deserializer)

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = this

    override fun endStructure(descriptor: SerialDescriptor) {
    }

    final override fun decodeBooleanElement(descriptor: SerialDescriptor, index: Int): Boolean = decodeBoolean()
    final override fun decodeByteElement(descriptor: SerialDescriptor, index: Int): Byte = decodeByte()
    final override fun decodeShortElement(descriptor: SerialDescriptor, index: Int): Short = decodeShort()
    final override fun decodeIntElement(descriptor: SerialDescriptor, index: Int): Int = decodeInt()
    final override fun decodeLongElement(descriptor: SerialDescriptor, index: Int): Long = decodeLong()
    final override fun decodeFloatElement(descriptor: SerialDescriptor, index: Int): Float = decodeFloat()
    final override fun decodeDoubleElement(descriptor: SerialDescriptor, index: Int): Double = decodeDouble()
    final override fun decodeCharElement(descriptor: SerialDescriptor, index: Int): Char = decodeChar()
    final override fun decodeStringElement(descriptor: SerialDescriptor, index: Int): String = decodeString()

    final override fun <T> decodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T>,
        previousValue: T?
    ): T = decodeSerializableValue(deserializer, previousValue)

    override fun <T : Any> decodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T?>,
        previousValue: T?
    ): T? {
        val isNullabilitySupported = deserializer.descriptor.isNullable
        return if (isNullabilitySupported || decodeNotNullMark()) decodeSerializableValue(deserializer, previousValue) else decodeNull()
    }
}