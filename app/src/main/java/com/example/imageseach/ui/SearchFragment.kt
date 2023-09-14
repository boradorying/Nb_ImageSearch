package com.example.imageseach.ui

import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageseach.R
import com.example.imageseach.adapter.SearchAdapter
import com.example.imageseach.data.Constants
import com.example.imageseach.data.Constants.Companion.AUTH_KEY
import com.example.imageseach.data.ImageSearchResponse
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.data.MetaData
import com.example.imageseach.databinding.FragmentSearchBinding
import com.example.imageseach.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.security.auth.callback.Callback


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val adapter: SearchAdapter get() = binding.SearchRV.adapter as SearchAdapter
    private var items: MutableList<KaKaoImage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val rootView = inflater.inflate(R.layout.activity_main,container,false)



        binding.SearchRV.adapter = SearchAdapter(items)
        binding.SearchRV.layoutManager = GridLayoutManager(context, 2)

//        val searchEdit = rootView.findViewById<EditText>(R.id.searchEdit)
//        val searchButton =rootView.findViewById<ImageView>(R.id.searchBtn)
//
//
//        searchButton.setOnClickListener {
//            val query = searchEdit.text.toString()
//            if (query.isNotEmpty()) {
//                searchImages(query)
//            }
//        }
//
//        adapter.notifyDataSetChanged()
        searchImages()
        return binding.root
    }

    private fun searchImages() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.getApiService()
                    .searchImage(AUTH_KEY, "아이브", "accuracy", 1, 80)

                if (response.isSuccessful) {
                    val imageSearchResponse = response.body()
                    val documents = imageSearchResponse?.documents
                    if (documents != null) {
                        withContext(Dispatchers.Main) {
                            // 검색 결과를 초기화하고 새로운 결과로 대체

                            items.addAll(documents)
                            adapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    Log.d("SearchFragment", "API 실패: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                Log.d("SearchFragment", "HTTP 예외: ${e.message()}")
            } catch (e: Throwable) {
                Log.d("SearchFragment", "오류: ${e.message}")
            }
        }
    }
}

