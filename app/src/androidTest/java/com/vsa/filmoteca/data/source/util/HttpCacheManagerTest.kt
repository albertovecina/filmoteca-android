package com.vsa.filmoteca.data.source.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Before
import org.junit.Test

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat

/**
 * Created by albertovecinasanchez on 22/7/16.
 */
class HttpCacheManagerTest {

    private var context: Context? = null
    private var cacheDir: File? = null

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        cacheDir = HttpCacheManager.getHttpCacheDir(context!!)
    }

    @Test
    fun testThatHttpCacheDirectoryExist() {
        assertThat(cacheDir!!.exists(), `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun testThatRemovesFilesFromHttpCache() {
        createTestFile()

        HttpCacheManager.removeCacheFilesOlderThan(context!!, 0)

        assertThat(cacheDir!!.list().size, `is`(0))
    }


    @Throws(IOException::class)
    private fun createTestFile(): File {
        val file = File(cacheDir, "temp")
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write("test".toByteArray())
        fileOutputStream.close()
        return file
    }

}