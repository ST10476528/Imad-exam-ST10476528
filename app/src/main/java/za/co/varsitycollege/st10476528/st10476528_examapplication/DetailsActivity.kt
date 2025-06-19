package za.co.varsitycollege.st10476528.st10476528_examapplication

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class DetailsActivity : AppCompatActivity() {
    // Data lists for songs - Initialize with some sample data
    private val songTitles: ArrayList<String> = ArrayList()
    private val songArtists: ArrayList<String> = ArrayList()
    private val songRatings: ArrayList<Int> = ArrayList() // Ratings from 1 to 5, for example

    // UI Elements
    private lateinit var showSongsButton: Button
    private lateinit var songsDetailTextView: TextView
    private lateinit var calculateAverageButton: Button
    private lateinit var averageRatingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Your layout file name

        // Initialize UI elements
        showSongsButton = findViewById<Button>(R.id.showSongsButton)
        songsDetailTextView = findViewById<TextView>(R.id.songsDetailTextView)
        calculateAverageButton = findViewById<Button>(R.id.calculateAverageButton)
        averageRatingTextView = findViewById<TextView>(R.id.ratingEditText)

        // Make the songsDetailTextView scrollable
        songsDetailTextView.movementMethod = ScrollingMovementMethod()

        // Populate initial song data (you can get this from user input, a database, etc.)
        populateSampleSongData()

        // Set up button click listeners
        showSongsButton.setOnClickListener {
            displaySongDetails()
        }

        calculateAverageButton.setOnClickListener {
            calculateAndDisplayAverageRating()
        }

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun populateSampleSongData() {
        // Sample Song 1
        songTitles.add("Bohemian Rhapsody")
        songArtists.add("Queen")
        songRatings.add(5)

        // Sample Song 2
        songTitles.add("Stairway to Heaven")
        songArtists.add("Led Zeppelin")
        songRatings.add(5)

        // Sample Song 3
        songTitles.add("Imagine")
        songArtists.add("John Lennon")
        songRatings.add(4)

        // Sample Song 4
        songTitles.add("Like a Rolling Stone")
        songArtists.add("Bob Dylan")
        songRatings.add(4)

        // Sample Song 5
        songTitles.add("Hey Jude")
        songArtists.add("The Beatles")
        songRatings.add(3) // Example of a different rating
    }

    private fun displaySongDetails() {
        if (songTitles.isEmpty()) {
            songsDetailTextView.text = "No songs in the playlist."
            return
        }

        val detailsBuilder = StringBuilder() // Efficiently build the string

        // Loop through the songs and append their details
        // Assuming songTitles, songArtists, and songRatings have the same size
        // and corresponding elements.
        for (i in songTitles.indices) {
            detailsBuilder.append("Title: ${songTitles[i]}\n")
            detailsBuilder.append("Artist: ${songArtists[i]}\n")
            detailsBuilder.append("Rating: ${songRatings[i]}/5\n")
            detailsBuilder.append("-----------------------------\n\n") // Separator
        }
        songsDetailTextView.text = detailsBuilder.toString()
    }

    private fun calculateAndDisplayAverageRating() {
        if (songRatings.isEmpty()) {
            averageRatingTextView.text = "Average Rating: N/A (No ratings available)"
            return
        }

        var totalRating = 0
        // Loop through the ratings to calculate the sum
        for (rating in songRatings) {
            totalRating += rating
        }

        val average = totalRating.toDouble() / songRatings.size

        // Format the average to two decimal places
        val decimalFormat = DecimalFormat("#.##")
        val formattedAverage = decimalFormat.format(average)

        averageRatingTextView.text = "Average Rating: $formattedAverage/5"
    }
}


