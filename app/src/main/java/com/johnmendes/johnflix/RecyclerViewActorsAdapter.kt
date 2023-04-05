package com.johnmendes.johnflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnmendes.johnflix.util.Constants
import kotlinx.android.synthetic.main.actors.view.*

class RecyclerViewActorsAdapter constructor(
    private val getActivity: DetailsMovieActivity, private val actorsList: List<Actors>
) : RecyclerView.Adapter<RecyclerViewActorsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actors, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindMovie(actorsList.get(position))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindMovie(actors: Actors) {
            itemView.name_actors.text = actors.name
            itemView.character_actors.text = actors.character
            Glide.with(itemView).load(Constants.IMAGE_BASE_URL + actors.image).into(itemView.image_actors)
        }
        val cardView: CardView = itemView.findViewById(R.id.card_actors)
    }
}