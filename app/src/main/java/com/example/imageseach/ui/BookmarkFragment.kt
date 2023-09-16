package com.example.imageseach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageseach.viewModel.SharedViewModel
import com.example.imageseach.adapter.BookmarkAdapter
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {
    private lateinit var binding : FragmentBookmarkBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter get() = binding.bookRv.adapter as BookmarkAdapter
    private var items: MutableList<KaKaoImage> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false)

        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            // 북마크 아이템이 업데이트될 때 어댑터에게 알려서 화면을 업데이트
            adapter.updateData(bookmarks)
        }

        binding.apply {  bookRv.adapter = BookmarkAdapter(items,viewModel)
        bookRv.layoutManager = LinearLayoutManager(context)}

        return binding.root
    }


}