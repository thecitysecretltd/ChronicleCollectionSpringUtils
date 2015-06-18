package com.thecitysecret.util.spring.chronicle

/**
 * Created by jamesl on 18/06/15.
 */
class ConfigurationException extends RuntimeException
{
    ConfigurationException() {
    }

    ConfigurationException(String var1) {
        super(var1)
    }

    ConfigurationException(String var1, Throwable var2) {
        super(var1, var2)
    }

    ConfigurationException(Throwable var1) {
        super(var1)
    }
}
