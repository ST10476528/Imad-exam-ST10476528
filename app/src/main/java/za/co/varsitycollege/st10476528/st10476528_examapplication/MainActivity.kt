package za.co.varsitycollege.st10476528.st10476528_examapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        startButton.setOnClickListener {
            // Navigate to the Quote page
            val intent = Intent(this, MusicList::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}