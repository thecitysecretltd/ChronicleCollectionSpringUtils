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
