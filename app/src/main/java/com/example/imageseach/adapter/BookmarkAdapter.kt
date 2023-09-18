package com.example.imageseach.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageseach.R
import com.example.imageseach.ViewModel.SharedViewModel
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.BookmarkItemBinding
import com.example.imageseach.extention.loadHeartImage


class BookmarkAdapter(private val viewModel: SharedViewModel) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
     var itemList = mutableListOf<KaKaoImage>()//빈껍데기 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {
        val binding =
            BookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bindItems(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }



    inner class ViewHolder(private val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: KaKaoImage) {
            binding.apply {
                nameArea.text = item.displaySitename
                dateArea.text = item.datetime

                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(imageArea)

               binding.bookmarkBtn.loadHeartImage(item.isHeart)
                binding.bookmarkBtn.setOnClickListener {
                    item.isHeart = !item.isHeart
                    if (item.isHeart) {
                        bookmarkBtn.setImageResource(R.drawable.baseline_favorite_24)
                        viewModel.addToBookmark(item)
                    } else {
                        bookmarkBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                        viewModel.removeFromBookmark(item)
                    }
                }
            }
        }
    }

}