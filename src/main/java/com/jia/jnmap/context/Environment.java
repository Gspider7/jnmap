package com.jia.jnmap.context;

/**
 * @author xutao@gaffassen.com
 * @version 1.0.0
 * @date 2020-03-19 17:13
 */
public class Environment {

    public static String getProjectPath() {

        return System.getProperty("user.dir");
    }
}
