package com.benhu.megasena

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ReturnThis
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.benhu.megasena.ui.theme.MegaSenaTheme
import java.util.Random


class MainActivity : ComponentActivity() {

    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.bnt_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let {
            txtResult.text = "Última aposta: $it"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Infome um número entre 6 e 15!", Toast.LENGTH_SHORT).show()
            return
        }

        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {

            Toast.makeText(this, "Infome um número entre 6 e 15!", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.sorted().joinToString(" - ")


        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}





