package org.amfoss.findme.util

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CacheService(private val context: Context, private val key: String) {
    fun saveData(value: String) {
        val cacheDir: File = context.cacheDir
        val file = File(cacheDir, key)
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val fileWriter = FileWriter(file, false)
            fileWriter.write("$value")
            Log.d("CacheService.$key", value)
            fileWriter.close()
        } catch (e: IOException) {
            Log.d("CacheServiceError", e.toString())
        }
    }

    fun getData(): String{
        val cacheDir: File = context.cacheDir
        val file = File(cacheDir, key)
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val value = file.readText()
            Log.d("CacheService", value)
            return value
        } catch (e: IOException) {
            Log.d("CacheService", e.toString())
            return ""
        }
    }

    fun deleteData() {
        val file = File(context.cacheDir, key)
        if(file.exists()) {
            println("File exists")
            file.delete()
        }

    }
}