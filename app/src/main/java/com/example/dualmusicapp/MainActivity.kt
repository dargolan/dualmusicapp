package com.example.dualmusicapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val titleText = findViewById<TextView>(R.id.titleText)
        val testButton = findViewById<Button>(R.id.testButton)
        
        titleText.text = "Dual Music App - Minimal Version"
        
        testButton.setOnClickListener {
            titleText.text = "Button clicked! App is working!"
        }
    }
} 