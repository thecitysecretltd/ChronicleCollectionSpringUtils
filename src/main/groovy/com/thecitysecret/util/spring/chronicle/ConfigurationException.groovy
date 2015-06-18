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
