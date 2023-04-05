package com.johnmendes.johnflix

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnmendes.johnflix.databinding.ActivityDetailsMovieBinding
import com.johnmendes.johnflix.remote.ListResultsMovieCredits
import com.johnmendes.johnflix.remote.MovieCreditsResponse
import com.johnmendes.johnflix.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMovieBinding
    private var recyclerView: RecyclerView? = null
    private var recyclerViewActorsAdapter: RecyclerViewActorsAdapter? = null
    private var actorsList = mutableListOf<Actors>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvActorsLists)
        recyclerViewActorsAdapter = RecyclerViewActorsAdapter(this@DetailsMovieActivity, actorsList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = recyclerViewActorsAdapter

        val service = RetrofitClient.createService()
        val call: Call<ListResultsMovieCredits> =
            service.movieCredits(movieId = intent.getIntExtra("movie_id", -1).toString())
        call.enqueue(object : Callback<ListResultsMovieCredits> {
            override fun onResponse(call: Call<ListResultsMovieCredits>, response: Response<ListResultsMovieCredits>) {
                response.body()?.let { show(it.castResults) }
            }
            override fun onFailure(call: Call<ListResultsMovieCredits>, t: Throwable) {
                Log.e("Error", t.toString())
            }
        })
    }

    private fun show(actors: List<MovieCreditsResponse>) {
        actorsList.clear()
        actors.forEach {
            val actor = Actors(it.image, it.name, it.character)
            actorsList.add(actor)
        }
        recyclerViewActorsAdapter?.notifyDataSetChanged()
    }
}