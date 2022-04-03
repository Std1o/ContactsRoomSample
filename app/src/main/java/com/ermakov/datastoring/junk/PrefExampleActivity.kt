package com.ermakov.datastoring.junk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ermakov.datastoring.R
import com.ermakov.datastoring.databinding.ActivityPrefExampleBinding

class PrefExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrefExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrefExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefManager = PrefManager(this)
        setInfo(prefManager)
        binding.btnSave.setOnClickListener() {
            prefManager.setName(binding.etName.text.toString())
            prefManager.setAge(binding.etAge.text.toString().toInt())
            setInfo(prefManager)
        }
        startActivity(Intent(this, FilesSavingActivity::class.java))
    }

    private fun setInfo(prefManager: PrefManager) {
        binding.tvInfo.text = getString(R.string.info, prefManager.getName(), prefManager.getAge())
    }
}