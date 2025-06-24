package com.lukafenir.luciuslist.io

import java.io.File

interface FileOperations {
    fun writeToFile(file: File, content: String)
    fun readFromFile(file: File): String
    fun fileExists(file: File): Boolean
}