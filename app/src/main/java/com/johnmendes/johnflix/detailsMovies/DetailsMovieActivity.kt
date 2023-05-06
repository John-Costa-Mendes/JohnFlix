package com.johnmendes.johnflix.detailsMovies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnmendes.johnflix.R
import com.johnmendes.johnflix.databinding.ActivityDetailsMovieBinding
import com.johnmendes.johnflix.detailsMovies.models.Actors
import com.johnmendes.johnflix.detailsMovies.viewmodel.DetailsMovieViewModel
import com.johnmendes.johnflix.remote.MovieDetailsResponse
import com.johnmendes.johnflix.remote.RetrofitClient
import com.johnmendes.johnflix.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMovieBinding
    private lateinit var viewModel: DetailsMovieViewModel

    private var recyclerView: RecyclerView? = null
    private var recyclerViewActorsAdapter: RecyclerViewActorsAdapter? = null
    private var actorsList = mutableListOf<Actors>()
    private val service = RetrofitClient.createDetailsMovieService()
    private var titleTextView: TextView? = null
    private var posterImageView: ImageView? = null
    private var yearTextView: TextView? = null
    private var timeTextView: TextView? = null
    private var genresTextView: TextView? = null
    private var synopsisTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailsMovieViewModel::class.java)

        titleTextView = findViewById(R.id.movie_title_details)
        posterImageView = findViewById(R.id.image_movie_details)
        yearTextView = findViewById(R.id.movie_year)
        timeTextView = findViewById(R.id.movie_time)
        genresTextView = findViewById(R.id.movie_genres)
        synopsisTextView = findViewById(R.id.movie_synopsis)

        recyclerviewActors()
        loadActors()
        loadDetails()

    }

    private fun show(actors: List<MovieCreditsResponse>) {
        actorsList.clear()
        actors.forEach {
            val actor = Actors(it.image, it.name, it.character)
            actorsList.add(actor)
        }
        recyclerViewActorsAdapter?.notifyDataSetChanged()
    }

    private fun show(details: MovieDetailsResponse){
        var genres = ""
        details.genres.forEach {
            genres = genres.plus(it.name).plus(", ")
        }

        titleTextView?.text = details.title
        yearTextView?.text = details.releaseDate.split('-')[0]
        timeTextView?.text = "${details.runtime} m"
        genresTextView?.text = genres.trimEnd(' ', ',')
        synopsisTextView?.text = details.overview
        posterImageView?.let {
            Glide.with(this).load(Constants.IMAGE_BASE_URL + details.posterPath).into(it)
        }
    }

    private fun loadActors() {
        binding.progressBarActors.visibility = View.VISIBLE
        val call: Call<ListResultsMovieCredits> =
            service.movieCredits(movieId = intent.getIntExtra("movie_id", -1).toString())
        call.enqueue(object : Callback<ListResultsMovieCredits> {
            override fun onResponse(call: Call<ListResultsMovieCredits>, response: Response<ListResultsMovieCredits>) {
                response.body()?.let { show(it.castResults) }
            }
            override fun onFailure(call: Call<ListResultsMovieCredits>, t: Throwable) {
                Log.e("Error", t.stackTraceToString())
            }
        })
    }

    private fun loadDetails() {
        val call : Call<MovieDetailsResponse> =
            service.movieDetails(movieId = intent.getIntExtra("movie_id", -1).toString())
        call.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                response.body()?.let { show(it) }
            }
            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Log.e("Error", t.stackTraceToString())
            }
        })
    }

    private fun recyclerviewActors() {
        recyclerView = findViewById(R.id.rvActorsLists)
        recyclerViewActorsAdapter = RecyclerViewActorsAdapter(this@DetailsMovieActivity, actorsList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = recyclerViewActorsAdapter
        binding.progressBarActors.visibility = View.GONE
    }
}