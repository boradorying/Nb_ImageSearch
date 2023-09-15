package com.example.imageseach.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageseach.R
import com.example.imageseach.ViewModel.SharedViewModel
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.SearchItemBinding


class SearchAdapter(private val itemList: MutableList<KaKaoImage>, private  var viewModel: SharedViewModel):RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

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
    fun updateData(newData: List<KaKaoImage>) {
        if (newData.isNotEmpty()) {
            itemList.addAll(newData)
            notifyDataSetChanged()
        }
    }
    fun SearchUpdateData(newData: List<KaKaoImage>) { //검색할때 다시 새로운 리스트
        if (newData.isNotEmpty()) {
            itemList.clear()
            itemList.addAll(newData)
            notifyDataSetChanged()
        }
    }



    inner class ViewHolder(private val binding: SearchItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: KaKaoImage) {
            binding.apply {
                nameArea.text = item.displaySitename
                dateArea.text = item.dateTime
                //이미지url은 문자열인데 string으로 유지하고 이미지로딩
                    //글라이드 로그캣에 검색해서 이미지 안불러와지는거 예외처리를 고민해보자
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
    }
}