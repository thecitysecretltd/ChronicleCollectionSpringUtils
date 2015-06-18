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

import net.openhft.chronicle.map.ChronicleMap
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by jamesl on 11/06/15.
 */
class MapMain
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/mapBeans.xml")
        ChronicleMap<Integer, String> map = context.getBean("mapFactory")
//        ChronicleMapFactoryBean<Integer, String> builder = context.getBean("mapFactory")
//        println(builder.chronicleMapBuilder.toString())
//        1048576.times {
//            map[it] = "Stuff"
//        }
//        assert map.size() == 1048576
        System.out.println("Map ready")
    }
}
