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

import net.openhft.chronicle.IndexedChronicle
import net.openhft.chronicle.VanillaChronicle
import spock.lang.Specification

/**
 * Created by jamesl on 17/06/15.
 */
class ChronicleQueueFactoryBeanSpec extends Specification
{
    private static final String TEST_FILE_PATH = "/tmp/file.dat"

    void "VanillaChronicleQueue created via path property"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.VANILLA
        def queue = underTest.getObject()

        then:
        queue.getClass() == VanillaChronicle

    }

    void "VanillaChronicleQueue created with some non-default values"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.VANILLA
        underTest.dataCacheCapacity = 64
        underTest.cleanupOnClose = true
        VanillaChronicle queue = underTest.getObject()

        then:
        assert queue.builder.dataCacheCapacity() == 64
        assert queue.builder.cleanupOnClose()
    }

    void "No file location specified for VanillaChronicleBuilder"()
    {
        setup:
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.VANILLA
        VanillaChronicle queue = underTest.getObject()

        then:
        thrown(ConfigurationException)
    }

    void "Property doesn't belong to VanillaChronicle"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.VANILLA
        underTest.cacheLineSize = 128
        underTest.getObject()

        then:
        thrown(IllegalArgumentException)
    }

    void "IndexedChronicleQueue created via path property"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.INDEXED
        def queue = underTest.getObject()

        then:
        assert queue.getClass() == IndexedChronicle

    }

    void "IndexedChronicleQueue created with some non-default values"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.INDEXED
        underTest.cacheLineSize = 128
        underTest.synchronous = true
        IndexedChronicle queue = underTest.getObject()

        then:
        assert queue.builder.cacheLineSize() == 128
        assert queue.builder.synchronous()
    }

    void "No file location specified for IndexedChronicleBuilder"()
    {
        setup:
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.INDEXED
        VanillaChronicle queue = underTest.getObject()

        then:
        thrown(ConfigurationException)
    }

    void "Property doesn't belong to IndexedChronicle"()
    {
        setup:
        new File(TEST_FILE_PATH).createNewFile()
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.path = TEST_FILE_PATH
        underTest.queueType = ChronicleQueueFactoryBean.QueueType.INDEXED
        underTest.cleanupOnClose = true
        underTest.getObject()

        then:
        thrown(IllegalArgumentException)
    }

    void "No queue type supplied, throws NullPointerException"()
    {
        setup:
        ChronicleQueueFactoryBean underTest = new ChronicleQueueFactoryBean()

        when:
        underTest.getObject()

        then:
        thrown(NullPointerException)
    }
}
