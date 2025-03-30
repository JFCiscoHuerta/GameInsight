package com.gklyphon.gameinsight.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.BuildConfig

class DlcsAdapter(private val dlcList: List<DlcItem>) : RecyclerView.Adapter<DlcsAdapter.DlcViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DlcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dlcs_card, parent, false)
        return DlcViewHolder(view)
    }

    override fun onBindViewHolder(holder: DlcViewHolder, position: Int) {
        val dlc = dlcList[position]
        holder.bind(dlc)
    }

    override fun getItemCount(): Int = dlcList.size

    class DlcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dlcTitle: TextView = itemView.findViewById(R.id.dlcTitle)
        private val dlcPrice: TextView = itemView.findViewById(R.id.dlcPrice)
        private val dlcIcon: ImageView = itemView.findViewById(R.id.dlcIcon)

        fun bind(dlc: DlcItem) {
            dlcTitle.text = dlc.name
            dlcPrice.text = dlc.price ?: "Free"
            dlc.background_image?.let {
                Glide.with(itemView.context).load(it).into(dlcIcon)
            } ?: dlcIcon.setImageResource(R.drawable.dlcicon)
        }
    }
}


data class DlcItem(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String?,
    val background_image: String?,
    val rating: Float,
    val metacritic: Int?,
    val price: String?
)

data class DlcResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<DlcItem>  // La lista de DLCs
)

