package com.vsa.filmoteca.presentation.utils

/**
 * Copyright (C) 2011, Karsten Priegnitz
 *
 *
 * Permission to use, copy, modify, and distribute this piece of software
 * for any purpose with or without fee is hereby granted, provided that
 * the above copyright notice and this permission notice appear in the
 * source code of all copies.
 *
 *
 * It would be appreciated if you mention the author in your change log,
 * contributors list or the like.
 *
 * @author: Karsten Priegnitz
 * @see: http://code.google.com/p/android-change-log/
 */

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.webkit.WebView
import com.vsa.filmoteca.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ChangeLog(private val context: Context) {

    companion object {

        private const val TAG = "ChangeLog"

        private const val VERSION_KEY = "PREFS_VERSION_KEY"
        private const val EOCL = "END_OF_CHANGE_LOG"

    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("${context.packageName}_preferences", Context.MODE_PRIVATE)

    private var lastVersionName: String
    private var currentVersionName: String

    init {
        lastVersionName = sharedPreferences.getString(VERSION_KEY, "") ?: ""
        currentVersionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        Log.d(TAG, "lastVersion: $lastVersionName")
        Log.d(TAG, "appVersion: $currentVersionName")
        // save new version number to preferences
        val editor = sharedPreferences.edit()
        editor.putString(VERSION_KEY, currentVersionName)
        editor.apply()
    }


    /**
     * @return an AlertDialog displaying the changes since the previous
     * installed version of your app (what's new).
     */
    val logDialog: AlertDialog
        get() = this.getDialog(isFirstInstall())


    private var listMode = Listmode.NONE
    private var stringBuffer: StringBuffer? = null

    /**
     * @return `true` if this version of your app is started the
     * first time
     */
    fun isUpdated(): Boolean = lastVersionName != currentVersionName

    private fun isFirstInstall(): Boolean = lastVersionName.isEmpty()

    private fun getDialog(full: Boolean): AlertDialog {
        val wv = WebView(this.context)
        wv.setBackgroundColor(0) // transparent
        // wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.loadDataWithBaseURL(null, this.getLog(full), "text/html", "UTF-8", null)

        val builder = AlertDialog.Builder(this.context)
        builder.setTitle(context.resources.getString(if (full)
            R.string.changelog_full_title
        else
            R.string.changelog_title))
                .setView(wv)
                .setCancelable(true)
                .setPositiveButton(
                        context.resources.getString(
                                R.string.changelog_ok_button)
                ) { dialog, _ -> dialog.cancel() }

        return builder.create()
    }

    /**
     * modes for HTML-Lists (bullet, numbered)
     */
    private enum class Listmode {
        NONE,
        ORDERED,
        UNORDERED
    }

    private fun getLog(full: Boolean): String {
        // read changelog.txt file
        stringBuffer = StringBuffer()
        try {
            val ins = context.resources.openRawResource(R.raw.changelog)
            val bufferedReader = BufferedReader(InputStreamReader(ins))

            var line = bufferedReader.readLine()
            var advanceToEOVS = false // if true: ignore further version sections
            while (line != null) {
                line = line.trim { it <= ' ' }
                val marker: Char = if (line.isNotEmpty())
                    line[0]
                else
                    '0'
                if (marker == '$') {
                    // begin of a version section
                    this.closeList()
                    val version = line.substring(1).trim { it <= ' ' }
                    // stop output?
                    if (!full) {
                        if (this.lastVersionName == version) {
                            advanceToEOVS = true
                        } else if (version == EOCL) {
                            advanceToEOVS = false
                        }
                    }
                } else if (!advanceToEOVS) {
                    when (marker) {
                        '%' -> {
                            // line contains version title
                            this.closeList()
                            stringBuffer!!.append("<div class='title'>").append(line.substring(1).trim { it <= ' ' }).append("</div>\n")
                        }
                        '_' -> {
                            // line contains version title
                            this.closeList()
                            stringBuffer!!.append("<div class='subtitle'>").append(line.substring(1).trim { it <= ' ' }).append("</div>\n")
                        }
                        '!' -> {
                            // line contains free text
                            this.closeList()
                            stringBuffer!!.append("<div class='freetext'>").append(line.substring(1).trim { it <= ' ' }).append("</div>\n")
                        }
                        '#' -> {
                            // line contains numbered list item
                            this.openList(Listmode.ORDERED)
                            stringBuffer!!.append("<li>").append(line.substring(1).trim { it <= ' ' }).append("</li>\n")
                        }
                        '*' -> {
                            // line contains bullet list item
                            this.openList(Listmode.UNORDERED)
                            stringBuffer!!.append("<li>").append(line.substring(1).trim { it <= ' ' }).append("</li>\n")
                        }
                        else -> {
                            // no special character: just use line as is
                            this.closeList()
                            stringBuffer!!.append(line).append("\n")
                        }
                    }
                }
                line = bufferedReader.readLine()
            }
            this.closeList()
            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuffer!!.toString()
    }

    private fun openList(listMode: Listmode) {
        if (this.listMode != listMode) {
            closeList()
            if (listMode == Listmode.ORDERED) {
                stringBuffer!!.append("<div class='list'><ol>\n")
            } else if (listMode == Listmode.UNORDERED) {
                stringBuffer!!.append("<div class='list'><ul>\n")
            }
            this.listMode = listMode
        }
    }

    private fun closeList() {
        if (this.listMode == Listmode.ORDERED) {
            stringBuffer!!.append("</ol></div>\n")
        } else if (this.listMode == Listmode.UNORDERED) {
            stringBuffer!!.append("</ul></div>\n")
        }
        this.listMode = Listmode.NONE
    }

}