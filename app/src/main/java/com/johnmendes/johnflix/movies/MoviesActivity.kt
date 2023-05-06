package com.johnmendes.johnflix.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnmendes.johnflix.R
import com.johnmendes.johnflix.databinding.ActivityMoviesBinding
import com.johnmendes.johnflix.movies.models.Movie
import com.johnmendes.johnflix.movies.viewmodel.MoviesViewModel

class MoviesActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMoviesBinding
    private var recyclerView: RecyclerView? = null
    private var recyclerViewMovieAdapter: RecyclerViewMovieAdapter? = null
    private var movieList = mutableListOf<Movie>()
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Popular.setOnClickListener(this)
        binding.Upcoming.setOnClickListener(this)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        setObserver()
        recyclerviewMovies()
        segmentedButtonClicked(R.id.Popular)
    }

    private fun show(movies: List<MovieResponse>) {
        movieList.clear()
        movies.forEach {
            val movie = Movie(it.title, it.image, it.date, it.idMovie)
            movieList.add(movie)
        }
        recyclerViewMovieAdapter?.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        if (view.id in listOf(R.id.Popular, R.id.Upcoming)) {
            segmentedButtonClicked(view.id)
        }
    }

    private fun segmentedButtonClicked(id: Int) {
        if (id == R.id.Upcoming) {
            viewModel.loadMovies(MoviesViewModel.MoviesType.UPCOMING)
            binding.Popular.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey))
            binding.Popular.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.Upcoming.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.Upcoming.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else if (id == R.id.Popular) {
            viewModel.loadMovies(MoviesViewModel.MoviesType.POPULAR)
            binding.Upcoming.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey))
            binding.Upcoming.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.Popular.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.Popular.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun recyclerviewMovies() {
        recyclerView = findViewById(R.id.rvMovieLists)
        recyclerViewMovieAdapter = RecyclerViewMovieAdapter(this@MoviesActivity, movieList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = recyclerViewMovieAdapter
    }

    private fun setObserver() {
        viewModel.movies().observe(this, Observer {
            show(it)
        })

        viewModel.isLoading().observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}