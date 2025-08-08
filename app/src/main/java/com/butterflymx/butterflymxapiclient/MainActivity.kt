package com.butterflymx.butterflymxapiclient

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.butterflymx.butterflymxapiclient.databinding.ActivityMainBinding

fun Int.dpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val extraTop = 16.dpToPx()
            v.setPadding(sb.left, sb.top + extraTop, sb.right, sb.bottom)
            insets
        }
    }
}