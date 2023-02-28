package com.johnmendes.johnflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewMovieAdapter constructor(
    private val getActivity: MainActivity,
    private val movieList: List<Movie>
) :
    RecyclerView.Adapter<RecyclerViewMovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvMovieTitle.text = movieList[position].title
        holder.ivMovieImg.setImageResource(movieList[position].image)
        holder.tvMovieDate.text = movieList[position].date

        holder.cardView.setOnClickListener {
            Toast.makeText(getActivity, movieList[position].title, Toast.LENGTH_LONG).show()
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMovieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val ivMovieImg: ImageView = itemView.findViewById(R.id.ivMovieImg)
        val tvMovieDate: TextView = itemView.findViewById(R.id.tvMovieDate)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

    }

}