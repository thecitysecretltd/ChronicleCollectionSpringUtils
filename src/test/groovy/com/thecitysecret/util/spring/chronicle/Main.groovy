package com.thecitysecret.util.spring.chronicle

import net.openhft.chronicle.map.ChronicleMap
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by jamesl on 11/06/15.
 */
class Main
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml")
        ChronicleMap<Integer, String> map = context.getBean("myChronMap")
        ChronicleMapFactoryBean<Integer, String> builder = context.getBean("mapFactory")
        println(builder.chronicleMapBuilder.toString())
//        1048576.times {
//            map[it] = "Stuff"
//        }
//        assert map.size() == 1048576
        System.out.println("Map ready")
    }
}
