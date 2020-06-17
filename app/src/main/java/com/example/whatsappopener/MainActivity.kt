package com.example.whatsappopener

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val number: String = if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            intent?.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        } else if (intent.action == Intent.ACTION_DIAL ||
                intent.action == Intent.ACTION_VIEW
        ) {
            intent?.data?.schemeSpecificPart.toString()
        }else{
            "9582054664"
        }
        startWhatsapp(number.trim())
    }

    private fun startWhatsapp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        var data = when {
            number[0] == '+' -> {
                number.substring(1)
            }
            else -> {
                number
            }
        }
        data = if (data.length == 10) {
            "91$data"
        } else {
            data
        }
        intent.data = Uri.parse("https://wa.me/$data")
        if (packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please install Whatsapp!!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}