package com.example.qrcodescanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {

    private lateinit var scanButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanButton = findViewById(R.id.scanButton)
        resultTextView = findViewById(R.id.resultTextView)

        scanButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setPrompt("Scan a QR code")
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                resultTextView.text = "Cancelled"
            } else {
                resultTextView.text = "Scanned: ${result.contents}"
                handleScannedResult(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleScannedResult(result: String) {
        // Here you can handle the scanned result, e.g., open a link if it's a URL
        if (result.startsWith("http")) {
            val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(result))
            startActivity(browserIntent)
        }
    }
}
