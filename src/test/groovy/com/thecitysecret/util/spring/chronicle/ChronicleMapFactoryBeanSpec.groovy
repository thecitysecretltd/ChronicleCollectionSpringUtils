/*
 * Copyright (C) 2015 - present thecitysecret Ltd
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.thecitysecret.util.spring.chronicle

import net.openhft.chronicle.hash.ChronicleHashBuilder
import net.openhft.chronicle.hash.replication.SingleChronicleHashReplication
import net.openhft.chronicle.hash.replication.TcpTransportAndNetworkConfig
import net.openhft.chronicle.hash.serialization.BytesReader
import net.openhft.chronicle.hash.serialization.BytesWriter
import net.openhft.chronicle.map.ChronicleMapBuilder
import net.openhft.lang.io.serialization.BytesMarshaller
import spock.lang.Specification

/**
 * Created by jamesl on 15/06/15.
 */
class ChronicleMapFactoryBeanSpec extends Specification
{

    private static final String TEST_FILE = "/tmp/file.dat"
    private static final byte TEST_BYTE = 2
    
    void "No file, use in-memory map"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when:
        underTest.getObject()

        then:
        1 * builder.create()
    }

    void "If we pass a file in, use persistent map"()
    {
        setup:
        new File(TEST_FILE).createNewFile()

        def builder = Mock(ChronicleHashBuilder)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when:
        underTest.dataFilePath = TEST_FILE
        underTest.getObject()

        then:
        1 * builder.createPersistedTo(_ as File)
    }

    void "No replication builder method called if nothing supplied"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when:
        underTest.getObject()

        then:
        0 * builder.replication(_ as SingleChronicleHashReplication)
    }

    void "Should use existing instance of SingleChronicleHashReplication if supplied"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        def singleChronicleHashReplication = SingleChronicleHashReplication.builder().createWithId(TEST_BYTE)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when:
        underTest.replication = singleChronicleHashReplication
        underTest.getObject()

        then:
        1 * builder.replication(singleChronicleHashReplication)
    }

    void "No replication, no call"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        def underTest = new ChronicleMapFactoryBean(builder)

        when:
        underTest.getObject()

        then:
        0 * builder.replication(_ as SingleChronicleHashReplication)
    }

    void "Should build SingleChronicleHashReplication if given byte identifier and optional tcp config"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        def tcpTransportAndNetworkConfig = TcpTransportAndNetworkConfig.of(1025, new InetSocketAddress(1025))
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when: "Only byte identifier is supplied"
        underTest.identifier = TEST_BYTE
        underTest.getObject()

        then: "Call Replication builder with just byte identifier"
        1 * builder.replication(_ as SingleChronicleHashReplication)>>{SingleChronicleHashReplication b ->
            assert b.identifier() == TEST_BYTE            
        }

        when: "When we have a tcp config as well"
        underTest.tcpTransportAndNetworkConfig = tcpTransportAndNetworkConfig
        underTest.getObject()

        then: "Call both methods on the builder"
        1 * builder.replication(_ as SingleChronicleHashReplication)>>{SingleChronicleHashReplication b ->
            assert b.identifier() == TEST_BYTE
            assert b.tcpTransportAndNetwork() == tcpTransportAndNetworkConfig
        }
    }

    void "KeyMarshaller method called"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        def keyMarshaller = Mock(BytesMarshaller)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when: "key marshaller is supplied"
        underTest.keyMarshaller = keyMarshaller
        underTest.getObject()

        then: "call keyMarshaller method"
        1 * builder.keyMarshaller(keyMarshaller)
    }

    void "keyMarshallers method called"()
    {
        setup:
        def builder = Mock(ChronicleHashBuilder)
        def keyWriter = Mock(BytesWriter)
        def keyReader = Mock(BytesReader)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when: "keyReader & keyWriter are supplied"
        underTest.keyWriter = keyWriter
        underTest.keyReader = keyReader
        underTest.getObject()

        then: "keyMarshallers is called"
        1 * builder.keyMarshallers(keyWriter, keyReader)
    }

    void "valueMarshaller method called"()
    {
        setup:
        def builder = new ChronicleMapBuilder(Integer, String)
        BytesMarshaller valueMarshaller = Mock(BytesMarshaller)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when: "value marshaller is supplied"
        underTest.valueMarshaller = valueMarshaller
        underTest.getObject()

        then: "call valueMarshaller method"
        assert builder.valueBuilder.interop() == valueMarshaller
    }

    void "valueMarshallers method called"()
    {
        setup:
        def builder = new ChronicleMapBuilder(Integer, String)
        def valueWriter = Mock(BytesWriter)
        def valueReader = Mock(BytesReader)
        ChronicleMapFactoryBean underTest = new ChronicleMapFactoryBean(builder)

        when: "valueReader & valueWriter are supplied"
        underTest.valueWriter = valueWriter
        underTest.valueReader = valueReader
        underTest.getObject()

        then: "valueMarshallers is called"
        assert builder.valueBuilder.reader() == valueReader
        assert builder.valueBuilder.interop() == valueWriter
    }
}
