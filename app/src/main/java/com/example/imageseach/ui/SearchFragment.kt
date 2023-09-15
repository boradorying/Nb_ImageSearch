package com.example.imageseach.ui

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageseach.ViewModel.SharedViewModel
import com.example.imageseach.adapter.SearchAdapter
import com.example.imageseach.data.Constants
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.FragmentSearchBinding
import com.example.imageseach.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException



class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SharedViewModel by activityViewModels()


    private val adapter: SearchAdapter get() = binding.SearchRV.adapter as SearchAdapter
    private var items: MutableList<KaKaoImage> = mutableListOf()

//    private lateinit var selet : MainActivity 클론 (넘버 1임) 껍데기라서 기능없음
//    private val parentActivity  get() = (activity as?  MainActivity)(넘버2고 이런식으로 타입캐스팅 해와서 객체호ㅓ해서 메서드에 접근하면 찐 메인)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
//        val mainView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_main,null) 새로운 레이아웃을 가져와서 검색기능이 안먹음...기능없는 껍데기같은느낌
//        ( activity as? MainActivity)?.findViewById<Button>(R.id.searchEdit) 이렇게 타입캐스팅 해서 기능가져옴
//        activity?.findViewById<Button>(R.id.searchBtn)  이런식으로 가져올 수 있음

//        parentActivity?.selecTab(1) 넘버2

        binding.SearchRV.adapter = SearchAdapter(items,viewModel)
        binding.SearchRV.layoutManager = GridLayoutManager(context, 2)

//        val searchEdit = mainView.findViewById<EditText>(R.id.searchEdit)
//        val searchButton =mainView.findViewById<Button>(R.id.searchBtn)


//        searchButton.setOnClickListener {
//            val query = searchEdit.text.toString()
//            if (query.isNotEmpty()) {
//                Log.e("jun", "검색어: $query")
//                searchImages(query)
//            }
//        }



        binding.searchBtn.setOnClickListener {
            val query = binding.searchEdit.text.toString()
            if (query.isNotEmpty()){
                searchImages(query)
            }
        }
        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            // 북마크 아이템이 업데이트될 때 어댑터에게 알려서 화면을 업데이트
            adapter.updateData(bookmarks)
        }

        return binding.root
    }


    fun searchImages(query:String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.getApiService()
                    .searchImage(Constants.AUTH_KEY, query, "accuracy", 1, 80)

                if (response.isSuccessful) {
                    Log.d("jun", "성공.")
                    val imageSearchResponse = response.body()
                    val documents = imageSearchResponse?.documents
                    if (documents != null) {
                        withContext(Dispatchers.Main) {

                            adapter.SearchUpdateData(documents)
                        }
                    }
                } else {
                    Log.d("jun", "API 실패: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                Log.d("jun", "HTTP 예외: ${e.message()}")
            } catch (e: Throwable) {
                Log.d("jun", "오류: ${e.message}")
            }
        }

    }




}