package com.vsa.filmoteca.data.repository.util;

import android.support.test.InstrumentationRegistry;

import com.vsa.filmoteca.data.repository.ws.WSClient;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by albertovecinasanchez on 22/7/16.
 */
public class HttpCacheManagerTest {

    @Test
    public void testThatRemovesFilesFromHttpCache() throws Exception {
        createTestFile();

        HttpCacheManager.removeCacheFilesOlderThan(InstrumentationRegistry.getTargetContext(), 0);
        assertTrue(getHttpCacheDir().listFiles().length == 0);
    }

    @Test
    public void testThatReturnsTheCorrectHttpCacheDirectory() {
        assertEquals(HttpCacheManager.getHttpCacheDir(InstrumentationRegistry.getTargetContext()), getHttpCacheDir());
    }

    private File createTestFile() throws IOException {
        File file = new File(getHttpCacheDir(), "temp");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("test".getBytes());
        fileOutputStream.close();
        return file;
    }

    private File getHttpCacheDir() {
        return new File(InstrumentationRegistry.getTargetContext().getCacheDir(), WSClient.CACHE_DIRECTORY);
    }

}