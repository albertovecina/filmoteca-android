package com.vsa.filmoteca.data.source.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import java.io.File
import java.io.FileOutputStream

/**
 * Created by albertovecinasanchez on 22/7/16.
 */
class HttpCacheManagerTest {

    private var context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private var cacheDir: File = HttpCacheManager.getHttpCacheDir(context)

    @Test
    fun testThatHttpCacheDirectoryExist() {
        assertThat(cacheDir.exists(), `is`(true))
    }

    @Test
    fun testThatRemovesFilesFromHttpCache() {
        createTestFile()

        HttpCacheManager.removeCacheFilesOlderThan(context, 0)

        assertThat(cacheDir.list()?.size, `is`(0))
    }


    private fun createTestFile(): File {
        val file = File(cacheDir, "temp")
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write("test".toByteArray())
        fileOutputStream.close()
        return file
    }

}