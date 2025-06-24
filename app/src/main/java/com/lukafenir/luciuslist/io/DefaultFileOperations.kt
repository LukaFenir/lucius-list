package com.lukafenir.luciuslist.io

import java.io.File
import java.io.FileReader
import java.io.FileWriter

class DefaultFileOperations: FileOperations {
    override fun writeToFile(file: File, content: String) {
        FileWriter(file).use { it.write(content) }
    }

    override fun readFromFile(file: File): String {
        return FileReader(file).use { it.readText() }
    }

    override fun fileExists(file: File): Boolean = file.exists()
}