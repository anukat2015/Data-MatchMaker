/*
#
# Copyright 2015 The Trustees of Indiana University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: Matchmaker Service
# File:  PropertyReader.java
# Description:  Utility class for reading properties file
#
# -----------------------------------------------------------------
# 
*/

package edu.indiana.d2i.matchmaker.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Yuan Luo
 *
 */
public class PropertyReader {
    
	private static HashMap<String, PropertyReader> instance = new HashMap<String, PropertyReader>();
	private static Properties properties;
    
    private PropertyReader(String propertiesPath) {
        try {
            InputStream fileInputStream = PropertyReader.class.getResourceAsStream(propertiesPath);
            if (fileInputStream == null)
                fileInputStream = new FileInputStream(propertiesPath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load properties file " + propertiesPath);
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    
    public static PropertyReader getInstance(String propertiesPath) {
    	if (!instance.containsKey(propertiesPath)) {
         instance.put(propertiesPath, new PropertyReader(propertiesPath));
     }
     return instance.get(propertiesPath);
    }
    
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

}

