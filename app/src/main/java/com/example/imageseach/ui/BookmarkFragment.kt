package com.example.imageseach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageseach.ViewModel.SharedViewModel
import com.example.imageseach.adapter.BookmarkAdapter
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {
    private lateinit var binding : FragmentBookmarkBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter get() = binding.bookRv.adapter as BookmarkAdapter
    private var items: MutableList<KaKaoImage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentBookmarkBinding.inflate(inflater,container,false)
        // RecyclerView와 어댑터를 초기화하고 설정
        binding.apply {
            bookRv.adapter = BookmarkAdapter(items,viewModel)
            bookRv.layoutManager = LinearLayoutManager(context)}

        return binding.root    // 인플레이트한 뷰를 반환
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // LiveData를 사용하여 북마크 아이템을 관찰하고 업데이트를
        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            // 북마크 아이템이 업데이트될 때 어댑터에게 알려서 화면을 업데이트
            adapter.updateData(bookmarks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}