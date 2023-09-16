package com.example.imageseach.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageseach.R
import com.example.imageseach.viewModel.SharedViewModel
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.BookmarkItemBinding


class BookmarkAdapter(private var itemList :MutableList<KaKaoImage>,private val viewModel: SharedViewModel) :RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {
      val binding = BookmarkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bindItems(item)
    }

    override fun getItemCount(): Int {
      return itemList.size
    }
    fun updateData(newData: List<KaKaoImage>) {
        itemList = newData.filter { it.isHeart }.toMutableList()
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding: BookmarkItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bindItems(item: KaKaoImage) {
            binding.apply {
                nameArea.text = item.displaySitename
                dateArea.text = item.dateTime

                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(imageArea)

                if (item.isHeart){
                    binding.bookmarkBtn.setImageResource(R.drawable.baseline_favorite_24)
                }else{
                    binding.bookmarkBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
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
    }}