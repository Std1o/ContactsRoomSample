package com.ermakov.datastoring

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ermakov.datastoring.databinding.ActivityFilesSavingBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class FilesSavingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilesSavingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilesSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        deleteFile()
    }

    private fun deleteFile() {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(dir, "external_file.txt")
        file.delete()
    }

    private fun showStorageUsage() {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Toast.makeText(this, "${dir.freeSpace}/${dir.totalSpace}", Toast.LENGTH_LONG).show()
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun writeFileOnExternalStorage() {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val appSpecificExternalDir = File(dir, "external_file.txt")
        appSpecificExternalDir.createNewFile()
        val fileExists = appSpecificExternalDir.exists().toString()
        val path = appSpecificExternalDir.absolutePath
        val snackbar = Snackbar.make(binding.root, "$fileExists\n$path", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun writeFileOnInternalStorage() {
        val filename = "file_example.txt"
        val fileContents = "Hello world!"
    }

    private fun showInternalFiles() {
        var files = ""
        for (file in fileList()) {
            files += "$file\n"
        }
        val snackbar = Snackbar.make(binding.root, files, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}