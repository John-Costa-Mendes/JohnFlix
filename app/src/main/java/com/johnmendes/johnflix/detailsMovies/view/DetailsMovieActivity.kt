package com.johnmendes.johnflix.detailsMovies.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnmendes.johnflix.R
import com.johnmendes.johnflix.databinding.ActivityDetailsMovieBinding
import com.johnmendes.johnflix.detailsMovies.service.RecyclerViewActorsAdapter
import com.johnmendes.johnflix.detailsMovies.models.Actors
import com.johnmendes.johnflix.detailsMovies.models.MovieCreditsResponse
import com.johnmendes.johnflix.detailsMovies.viewmodel.DetailsMovieViewModel
import com.johnmendes.johnflix.remote.MovieDetailsResponse
import com.johnmendes.johnflix.util.Constants

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMovieBinding
    private lateinit var viewModel: DetailsMovieViewModel

    private var recyclerView: RecyclerView? = null
    private var recyclerViewActorsAdapter: RecyclerViewActorsAdapter? = null
    private var actorsList = mutableListOf<Actors>()

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
        setObserver()
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
        details.genres.forEach {genres = genres.plus(it.name).plus(", ")}
        titleTextView?.text = details.title
        yearTextView?.text = details.releaseDate.split('-')[0]
        timeTextView?.text = "${details.runtime} m"
        genresTextView?.text = genres.trimEnd(' ', ',')
        synopsisTextView?.text = details.overview
        posterImageView?.let {
            Glide.with(this).load(Constants.IMAGE_BASE_URL + details.posterPath).into(it)
        }
    }

    private fun setObserver(){
        viewModel.loadActors(movieId = intent.getIntExtra("movie_id", -1).toString())
        viewModel.actors().observe(this, Observer { show(it) })

        viewModel.loadDetails(movieId = intent.getIntExtra("movie_id", -1).toString())
        viewModel.details().observe(this, Observer { show(it) })

        viewModel.isLoading().observe(this, Observer {
            binding.progressBarActors.visibility = if (it) View.VISIBLE else View.GONE })

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