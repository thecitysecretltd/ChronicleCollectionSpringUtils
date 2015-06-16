package com.thecitysecret.util.spring.chronicle

import groovy.transform.PackageScope
import net.openhft.lang.io.serialization.BytesMarshaller
import net.openhft.chronicle.hash.replication.SingleChronicleHashReplication
import net.openhft.chronicle.hash.replication.TcpTransportAndNetworkConfig
import net.openhft.chronicle.hash.serialization.BytesReader
import net.openhft.chronicle.hash.serialization.BytesWriter
import net.openhft.chronicle.map.ChronicleMapBuilder
import org.springframework.beans.factory.FactoryBean

/**
 * A {@see FactoryBean} for configuring Chronicle Map in Spring.
 * @param < K > The type of keys maintained by this map.
 * @param < V > The type of map values.
 */
class ChronicleMapFactoryBean<K, V> implements FactoryBean<Map<K, V>>
{
    String dataFilePath

    BytesMarshaller<? super K> keyMarshaller
    BytesWriter<? super K> keyWriter
    BytesReader<K> keyReader
    BytesMarshaller<? super V> valueMarshaller
    BytesWriter<V> valueWriter
    BytesReader<V> valueReader
    SingleChronicleHashReplication replication
    Byte identifier
    TcpTransportAndNetworkConfig tcpTransportAndNetworkConfig

    private def chronicleMapBuilder
    @Delegate
    private ChronicleMapFactoryBeanPropertyHolder propertyHolder = new ChronicleMapFactoryBeanPropertyHolder()
    private File file

    public ChronicleMapFactoryBean(Class<K> keyClass, Class<V> valueClass)
    {
        this.chronicleMapBuilder = ChronicleMapBuilder.of(keyClass, valueClass)
    }

    @PackageScope
    ChronicleMapFactoryBean(def builder)
    {
        this.chronicleMapBuilder = builder
    }

    public Map<K, V> getObject()
    {
        file = new File(dataFilePath ?: "")
        setBasicProperties()

        if(keyMarshaller)
        {
            chronicleMapBuilder.keyMarshaller(keyMarshaller)
        }
        else if (keyWriter && keyReader)
        {
            chronicleMapBuilder.keyMarshallers(keyWriter, keyReader)
        }

        if(valueMarshaller)
        {
            chronicleMapBuilder.valueMarshaller(valueMarshaller)
        }
        else if (valueWriter && valueReader)
        {
            chronicleMapBuilder.valueMarshallers(valueWriter, valueReader)
        }

        if(replication)
        {
            chronicleMapBuilder.replication(replication)
        }
        else if(identifier != null)
        {
            SingleChronicleHashReplication.Builder builder = SingleChronicleHashReplication.builder()
            if (tcpTransportAndNetworkConfig)
            {
                builder.tcpTransportAndNetwork(tcpTransportAndNetworkConfig)
            }
            chronicleMapBuilder.replication(builder.createWithId(identifier))
        }

        if(dataFilePath && file.isFile())
        {
            return chronicleMapBuilder.createPersistedTo(file)
        }
        else
        {
            return chronicleMapBuilder.create()
        }
    }

    ChronicleMapBuilder<K, V> getChronicleMapBuilder() {
        return chronicleMapBuilder
    }

    @Override
    Class<?> getObjectType() {
        return Map
    }

    @Override
    boolean isSingleton() {
        return false
    }

    private void setBasicProperties()
    {
        propertyHolder.properties.each { String k, Object v ->
            if(!v || k == "class") return
            chronicleMapBuilder.invokeMethod(k, v)
        }
    }
}
