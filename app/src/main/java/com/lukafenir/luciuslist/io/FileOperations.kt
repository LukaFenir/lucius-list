package com.lukafenir.luciuslist.io

import java.io.File
import java.io.IOException

interface FileOperations {
    @Throws(IOException::class)
    fun writeToFile(file: File, content: String)

    @Throws(IOException::class)
    fun readFromFile(file: File): String

    fun fileExists(file: File): Boolean
}