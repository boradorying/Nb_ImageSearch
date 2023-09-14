package com.example.imageseach.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.SearchItemBinding


class SearchAdapter(private val itemList: MutableList<KaKaoImage>):RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemList[position])

    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    inner class ViewHolder(private val binding: SearchItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: KaKaoImage) {
            binding.apply {
                nameArea.text = item.displaySitename
                dateArea.text = item.dateTime
                //이미지url은 문자열인데 string으로 유지하고 이미지로딩
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(imageArea)
            }
        }
    }
}