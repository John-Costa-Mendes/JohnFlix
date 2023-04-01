package com.johnmendes.johnflix

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnmendes.johnflix.util.Constants.Companion.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_movie.view.*

class RecyclerViewMovieAdapter constructor(
    private val getActivity: MainActivity, private val movieList: List<Movie>
) : RecyclerView.Adapter<RecyclerViewMovieAdapter.MyViewHolder>() {

    private val MainActivity = MainActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_movie, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindMovie(movieList.get(position))

        holder.cardView.setOnClickListener {
            val intent = Intent(holder.cardView.context, DetailsMovieActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindMovie(movie: Movie) {
            itemView.movie_title.text = movie.title
            itemView.TAG.text = movie.date
            Glide.with(itemView).load(IMAGE_BASE_URL + movie.image).into(itemView.Image_Movie)
        }
        val cardView: CardView = itemView.findViewById(R.id.card_filmes)

    }
}