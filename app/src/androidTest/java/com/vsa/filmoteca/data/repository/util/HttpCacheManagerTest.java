package com.vsa.filmoteca.data.repository.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vsa.filmoteca.data.repository.ws.WSClient;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by albertovecinasanchez on 22/7/16.
 */
public class HttpCacheManagerTest {

    private Context mContext;
    private File mCacheDir;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mCacheDir = HttpCacheManager.getHttpCacheDir(mContext);
    }

    @Test
    public void testThatHttpCacheDirectoryExist() {
        assertThat(mCacheDir.exists(), is(true));
    }

    @Test
    public void testThatRemovesFilesFromHttpCache() throws Exception {
        createTestFile();

        HttpCacheManager.removeCacheFilesOlderThan(mContext, 0);

        assertThat(mCacheDir.list().length, is(0));
    }


    private File createTestFile() throws IOException {
        File file = new File(mCacheDir, "temp");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("test".getBytes());
        fileOutputStream.close();
        return file;
    }


}