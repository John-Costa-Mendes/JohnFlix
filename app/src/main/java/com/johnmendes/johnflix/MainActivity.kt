package com.johnmendes.johnflix

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnmendes.johnflix.databinding.ActivityMainBinding
import com.johnmendes.johnflix.remote.ListResultsMovie
import com.johnmendes.johnflix.remote.MovieResponse
import com.johnmendes.johnflix.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val service = RetrofitClient.createService()
    private lateinit var binding: ActivityMainBinding
    private var recyclerView: RecyclerView? = null
    private var recyclerViewMovieAdapter: RecyclerViewMovieAdapter? = null
    private var movieList = mutableListOf<Movie>()
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var mLayoutManager: GridLayoutManager? = null
    private var loading = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvMovieLists)
        recyclerViewMovieAdapter = RecyclerViewMovieAdapter(this@MainActivity, movieList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = recyclerViewMovieAdapter

        binding.Popular.setOnClickListener(this)
        binding.Upcoming.setOnClickListener(this)

        segmentedButtonClicked(R.id.Popular)
        //setupPagination()
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
        var call: Call<ListResultsMovie>? = null

        if (id == R.id.Upcoming) {
            call = service.movieUpcoming()
            binding.Popular.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey))
            binding.Popular.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.Upcoming.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.Upcoming.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else if (id == R.id.Popular) {
            call = service.moviePopular()
            binding.Upcoming.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey))
            binding.Upcoming.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.Popular.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.Popular.setTextColor(ContextCompat.getColor(this, R.color.black))
        }

        call?.enqueue(object : Callback<ListResultsMovie> {
            override fun onResponse(call: Call<ListResultsMovie>, response: Response<ListResultsMovie>) {
                response.body()?.let { show(it.results) }
            }
            override fun onFailure(call: Call<ListResultsMovie>, t: Throwable) {
                Log.e("Error", t.toString())
            }
        })
    }

    private fun setupPagination(){
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                if (dy > 0){
                    visibleItemCount = mLayoutManager!!.childCount
                    totalItemCount = mLayoutManager!!.itemCount
                    pastVisiblesItems = mLayoutManager!!.findFirstVisibleItemPosition()
                    if (loading){
                        if(visibleItemCount + pastVisiblesItems >= totalItemCount){
                            loading = false
                            Toast.makeText(this@MainActivity, "Fim da página", Toast.LENGTH_SHORT).show()
                            recyclerViewMovieAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }
}