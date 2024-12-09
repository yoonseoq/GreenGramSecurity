package com.green.greengramver3.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MyFileUtilsTest {
    private final String FILE_DIRECTORY = "D:/download/greengram_ver3";
    private MyFileUtils myFileUtils;

    @BeforeEach
    void setUp() {
        myFileUtils = new MyFileUtils(FILE_DIRECTORY);
    }

    @Test
    void deleteFolder(){
        String path = "user/ddd";
        myFileUtils.deleteFolder(path,false);
    }

    @Test
    void testDeleteFolder() {
    }
}