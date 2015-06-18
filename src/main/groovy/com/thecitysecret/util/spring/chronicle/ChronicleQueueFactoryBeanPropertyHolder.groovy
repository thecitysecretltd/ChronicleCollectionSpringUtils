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

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

@PackageScope @CompileStatic
class ChronicleQueueFactoryBeanPropertyHolder
{
    //Vanilla & Indexed
    Boolean synchronous
    Boolean useCheckedExcerpt
    Boolean useCompressedObjectSerializer

    //Indexed
    Integer indexBlockSizeInt
    Integer dataBlockSizeInt
    Integer cacheLineSize
    Integer messageCapacity

    //Vanilla
    String cycleFormat
    Integer cycleLength
    Long indexBlocksSizeLong
    Long dataBlocksSizeLong
    Long entriesPerCycle
    Integer defaultMessageSize
    Boolean cleanupOnClose
    Integer dataCacheCapacity
    Integer indexCacheCapacity
}
