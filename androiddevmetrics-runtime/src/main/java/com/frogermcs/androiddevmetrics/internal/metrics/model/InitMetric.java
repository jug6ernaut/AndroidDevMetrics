package com.frogermcs.androiddevmetrics.internal.metrics.model;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Miroslaw Stanek on 23.01.2016.
 */
public class InitMetric {

    public Class<?> cls;
    public long initTimeMillis = 0;
    public int instanceNo = 0;
    public Set<InitMetric> args = new HashSet<>();

    public long getTotalInitTime() {
        long total = initTimeMillis;
        for (InitMetric initMetric : args) {
            total += initMetric.getTotalInitTime();
        }
        return total;
    }

    public long getInitTimeWithoutArgs() {
        return initTimeMillis;
    }

    public String getClassName() {
        String className;
        if (Proxy.isProxyClass(cls)) {
            final Class<?>[] interfaces = cls.getInterfaces();
            if (interfaces.length == 1) {
                className = interfaces[0].getSimpleName();
            } else {
                className = Arrays.asList(interfaces).toString();
            }
        } else {
            className = cls.getSimpleName();
        }

        if (instanceNo > 0) {
            return className + "#" + Integer.toString(instanceNo);
        }
        return className;
    }

    @Override
    public String toString() {
        if (Proxy.isProxyClass(cls)) {
            return "InitMetric{" +
                    "initTimeMillis=" + initTimeMillis +
                    ", cls=" + Arrays.asList(cls.getInterfaces()) +
                    ", args=" + args +
                    '}';
        } else {
            return "InitMetric{" +
                    "initTimeMillis=" + initTimeMillis +
                    ", cls=" + cls.getSimpleName() +
                    ", args=" + args +
                    '}';
        }
    }
}
