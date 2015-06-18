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

import net.openhft.chronicle.Chronicle
import net.openhft.chronicle.ChronicleQueueBuilder
import org.springframework.beans.factory.FactoryBean

/**
 * A {@link FactoryBean } for configuring Chronicle Queue in Spring.
 */
class ChronicleQueueFactoryBean implements FactoryBean<Chronicle>
{
    static public enum QueueType
    {
        INDEXED, VANILLA
    }

    private def chronicleQueueBuilder
    @Delegate
    private ChronicleQueueFactoryBeanPropertyHolder propertyHolder = new ChronicleQueueFactoryBeanPropertyHolder()
    QueueType queueType
    String path

    @Override
    public Chronicle getObject()
    {
        switch(queueType)
        {
            case QueueType.INDEXED:
                if (path)
                {
                    this.chronicleQueueBuilder = ChronicleQueueBuilder.indexed(path)
                }
                else
                {
                    throw new ConfigurationException("Missing file location")
                }
                break

            case QueueType.VANILLA:
                if (path)
                {
                    this.chronicleQueueBuilder = ChronicleQueueBuilder.vanilla(path)
                }
                else
                {
                    throw new ConfigurationException("Missing file location")
                }
                break

            default:
                throw new NullPointerException("Queue type not set. Must be VANILLA or INDEXED")

        }
        setProperties()
        return chronicleQueueBuilder.build()
    }

    @Override
    Class<?> getObjectType()
    {
        return Chronicle
    }

    @Override
    boolean isSingleton() {
        return false
    }

    private void setProperties()
    {
        propertyHolder.properties.each { String k, Object v ->
            if(!v || k == "class") return
            try {
                chronicleQueueBuilder.invokeMethod(k, v)
            } catch (MissingMethodException mmex) {
                throw new IllegalArgumentException("Couldn't set property ${k} on ${chronicleQueueBuilder.class.simpleName} - wrong queue type?", mmex)
            }
        }
    }
}
