package com.thecitysecret.util.spring.chronicle

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by jamesl on 18/06/15.
 */
class Main
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml")
        def queue = context.getBean("queue")

    }
}
