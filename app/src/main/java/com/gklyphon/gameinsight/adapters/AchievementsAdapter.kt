package com.gklyphon.gameinsight.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.models.Achievement
import org.w3c.dom.Text

class AchievementsAdapter (private val AchievementsList: List<Achievement>) : RecyclerView.Adapter<AchievementsAdapter.AchievementsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.achievements_card, parent, false)
        return AchievementsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementsViewHolder, position: Int) {
        val achievement = AchievementsList[position]
        holder.bind(achievement)
    }

    override fun getItemCount(): Int = AchievementsList.size

    class AchievementsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val achievementsTitle: TextView = itemView.findViewById(R.id.achievementsTitle)
        private val achievementsIcon: ImageView = itemView.findViewById(R.id.achievementsIcon)
        private val achievementsPercent: TextView = itemView.findViewById(R.id.achievementsPercent)
        private val achievementsDescription: TextView = itemView.findViewById(R.id.achievementsDescription)

        @SuppressLint("SetTextI18n")
        fun bind(achievement: Achievement) {
            achievementsTitle.text = achievement.name
            achievementsPercent.text = achievement.percent
            achievementsDescription.text = achievement.description + "%"
            achievement.image.let {
                Glide.with(itemView.context).load(it).into(achievementsIcon)
            } ?: achievementsIcon.setImageResource(R.drawable.dlcicon)
        }
    }

}
data class AchievementsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Achievement>  // La lista de DLCs
)
