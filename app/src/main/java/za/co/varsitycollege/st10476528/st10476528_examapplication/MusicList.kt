package za.co.varsitycollege.st10476528.st10476528_examapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MusicList : AppCompatActivity() {

        // ArrayLists to store song data
        var musicRecommend: ArrayList<String> = ArrayList()
        var artistName: ArrayList<String> = ArrayList()
        var ratings: ArrayList<Int> = ArrayList()
        var musicComment: ArrayList<String> = ArrayList()

        // UI Elements for input
        private lateinit var musicRecommendEditText: EditText
        private lateinit var artistNameEditText: EditText
        private lateinit var ratingEditText: EditText
        private lateinit var musicCommentEditText: EditText
        private lateinit var addSongButton: Button // Renamed from addToPlaylistButton for this context

        // Existing UI Elements
        private lateinit var averageRatingTextView: TextView
        private lateinit var allDetailsButton: Button
        private lateinit var exitButton: Button


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_music_list) // Make sure this matches your layout file

            // Initialize Input UI Elements
            musicRecommendEditText = findViewById(R.id.musicRecommendEditText)
            artistNameEditText = findViewById(R.id.artistNameEditText)
            ratingEditText = findViewById(R.id.ratingEditText)
            musicCommentEditText = findViewById(R.id.musicCommentEditText)
            addSongButton = findViewById(R.id.addSongButton) // Changed ID from addToPlaylist

            // Initialize Other UI Elements
            averageRatingTextView = findViewById(R.id.ratingEditText)
            allDetailsButton = findViewById(R.id.allDetailsButton)
            exitButton = findViewById(R.id.exitButton)

            // Remove pre-loaded data


            displayAverageRating() // Initially display N/A or 0.00

            addSongButton.setOnClickListener {
                addSongFromInput()
            }

            allDetailsButton.setOnClickListener {
                if (musicRecommend.isEmpty()) {
                    Toast.makeText(this, "No music data to show. Add songs first!", Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putStringArrayListExtra("MUSIC_RECOMMEND_KEY", musicRecommend)
                    intent.putStringArrayListExtra("ARTIST_NAME_KEY", artistName)
                    intent.putIntegerArrayListExtra("RATINGS_KEY", ratings)
                    intent.putStringArrayListExtra("MUSIC_COMMENT_KEY", musicComment)
                    startActivity(intent)
                }
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

        private fun addSongFromInput() {
            val title = musicRecommendEditText.text.toString().trim()
            val artist = artistNameEditText.text.toString().trim()
            val ratingStr = ratingEditText.text.toString().trim()
            val comment = musicCommentEditText.text.toString().trim()

            if (title.isEmpty()) {
                musicRecommendEditText.error = "Song title cannot be empty"
                musicRecommendEditText.requestFocus() // Focus on the problematic field
                Toast.makeText(this, "Please enter the song title.", Toast.LENGTH_SHORT).show()
                return
            }

            if (artist.isEmpty()) {
                artistNameEditText.error = "Artist name cannot be empty"
                artistNameEditText.requestFocus()
                Toast.makeText(this, "Please enter the artist name.", Toast.LENGTH_SHORT).show()
                return
            }

            val rating: Int
            if (ratingStr.isEmpty()) {
                ratingEditText.error = "Rating cannot be empty"
                ratingEditText.requestFocus()
                Toast.makeText(this, "Please enter a rating.", Toast.LENGTH_SHORT).show()
                return
            } else {
                try {
                    rating = ratingStr.toInt()
                    if (rating < 1 || rating > 5) {
                        ratingEditText.error = "Rating must be between 1 and 5"
                        ratingEditText.requestFocus()
                        Toast.makeText(this, "Rating must be between 1 and 5.", Toast.LENGTH_SHORT).show()
                        return
                    }
                } catch (e: NumberFormatException) {
                    ratingEditText.error = "Invalid rating format"
                    ratingEditText.requestFocus()
                    Toast.makeText(this, "Please enter a valid number for rating.", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            // All input is valid, add to lists
            musicRecommend.add(title)
            artistName.add(artist)
            ratings.add(rating)
            musicComment.add(comment) // Add comment even if it's empty

            Toast.makeText(this, "\"$title\" added to playlist!", Toast.LENGTH_SHORT).show()

            // Clear input fields for next entry
            musicRecommendEditText.text.clear()
            artistNameEditText.text.clear()
            ratingEditText.text.clear()
            musicCommentEditText.text.clear()

            // Optionally, hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            musicRecommendEditText.requestFocus() // Set focus back to the first field or remove focus

            // Update the average rating display
            displayAverageRating()
        }

        private fun calculateAverageRating(currentRatings: List<Int>): Double {
            if (currentRatings.isEmpty()) {
                return 0.0
            }
            var total = 0
            for (ratingInList in currentRatings) { // Renamed loop variable for clarity
                total += ratingInList
            }
            return total.toDouble() / currentRatings.size
        }

        private fun displayAverageRating() {
            if (ratings.isNotEmpty()) {
                val average = calculateAverageRating(ratings)
                averageRatingTextView.text = "Average Rating: %.2f".format(average)
            } else {
                averageRatingTextView.text = "Average Rating: N/A"
            }
        }
    }