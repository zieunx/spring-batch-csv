package com.danal.publicdataprocessor.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FileUtils {

    public static String getAbsolutePath(String path) {
        try {
            return new ClassPathResource(path).getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Fail to get absolute Path. path:" + path, e);
        }
    }
}
