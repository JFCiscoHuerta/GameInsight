package com.gklyphon.gameinsight.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.activities.DetailsActivity
import com.gklyphon.gameinsight.models.Game
import com.google.android.material.button.MaterialButton

/**
 * Adapter for displaying a list of games in a RecyclerView.
 *
 * @property games - The list of games to be displayed.
 *
 * @author JFCiscoHuerta
 */
class GameAdapter(private var games: List<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    /**
     * ViewHolder for game items in the RecyclerView.
     *
     * @param view - The view representing a single game item.
     */
    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameTitle: TextView = view.findViewById(R.id.game_title)
        val gameImage: ImageView = view.findViewById(R.id.game_image)
        val gameRating: TextView = view.findViewById(R.id.game_rating)
        val gameGenre: TextView = view.findViewById(R.id.game_genre)
        val detailsButton: MaterialButton = view.findViewById(R.id.view_details_button)
    }

    /**
     * Creates and returns a new ViewHolder for a game item.
     *
     * @param parent - The parent view group.
     * @param viewType - The view type.
     * @return A new instance of GameViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_card, parent, false);
        return GameViewHolder(view)
    }

    /**
     * Binds data from a Game object to a ViewHolder.
     *
     * @param holder - The ViewHolder to bind data to.
     * @param position - The position of the item in the list.
     */
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.gameTitle.text = game.name
        holder.gameRating.text = "Rating: ${game.rating}"
        holder.gameGenre.text = game.genres.joinToString(", ") { genre -> genre.name }

        Glide.with(holder.itemView.context)
            .load(game.background_image)
            .into(holder.gameImage)

        holder.detailsButton.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("GAME_ID", game.id)
            context.startActivity(intent)
        }

    }

    /**
     * Returns the number of items in the dataset.
     *
     * @return The total count of game items.
     */
    override fun getItemCount() = games.size

    /**
     * Updates the game list and notifies the adapter of the data change.
     *
     * @param newGames - The new list of games to be displayed.
     */
    fun updateGames(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}