package com.tbadhit.myrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tbadhit.myrecyclerview.databinding.ItemRowHeroBinding

// (1)
class ListHeroAdapter(private val listHero: ArrayList<Hero>) :
    RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    // Agar bisa setOnClickListener di activity
    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

    // Agar bisa setOnClickListener di activity
    private var onItemClickCallback: OnItemClickCallback? = null

    // (1)
    inner class ListViewHolder(private val binding: ItemRowHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(hero.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhoto)

                tvItemName.text = hero.name
                tvItemDescription.text = hero.description
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(hero) }
            }
        }
    }

    // (1)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    // (1)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHero[position])
    }

    // (1)
    override fun getItemCount(): Int = listHero.size

    // Agar bisa setOnClickListener di activity
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}