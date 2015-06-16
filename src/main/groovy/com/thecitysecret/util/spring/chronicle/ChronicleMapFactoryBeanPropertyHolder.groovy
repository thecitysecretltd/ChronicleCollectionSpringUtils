package com.thecitysecret.util.spring.chronicle

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import net.openhft.chronicle.hash.replication.TimeProvider
import net.openhft.chronicle.hash.serialization.SizeMarshaller
import net.openhft.chronicle.map.Alignment
import net.openhft.chronicle.map.MapEntryOperations
import net.openhft.chronicle.map.MapMethods
import net.openhft.lang.io.serialization.BytesMarshallerFactory
import net.openhft.lang.io.serialization.ObjectFactory
import net.openhft.lang.io.serialization.ObjectSerializer

/**
 * Created by jamesl on 15/06/15.
 */
@PackageScope @CompileStatic
class ChronicleMapFactoryBeanPropertyHolder<K, V>
{
    Long entries
    Double averageKeySize
    K constantKeySizeBySample
    Double averageValueSize
    V constantValueSizeBySample
    Integer actualChunkSize
    Integer maxChunksPerEntry
    Alignment entryAndValueAlignment
    Long entriesPerSegment
    Long actualChunksPerSegment
    Integer minSegments
    Integer actualSegments
    Boolean putReturnsNull
    Boolean removeReturnsNull
    TimeProvider timeProvider
    BytesMarshallerFactory bytesMarshallerFactory
    ObjectSerializer objectSerializer
    SizeMarshaller keySizeMarshaller
    SizeMarshaller valueSizeMarshaller
    ObjectFactory<? extends K> keyDeserializationFactory
    ObjectFactory<V> valueDeserializationFactory
    V defaultValue
    MapEntryOperations<K, V, ?> entryOperations
    MapMethods<K, V, ?> mapMethods
}
