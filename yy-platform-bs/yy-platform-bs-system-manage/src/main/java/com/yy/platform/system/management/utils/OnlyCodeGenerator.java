package com.yy.platform.system.management.utils;

import java.util.UUID;

public class OnlyCodeGenerator {
    public static String distriId() {
        return UUID.randomUUID().toString();
    }
}
