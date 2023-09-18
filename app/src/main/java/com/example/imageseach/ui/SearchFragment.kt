package com.example.imageseach.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageseach.ViewModel.SharedViewModel
import com.example.imageseach.adapter.SearchAdapter
import com.example.imageseach.data.Constants
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.FragmentSearchBinding
import com.example.imageseach.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter: SearchAdapter get() = binding.SearchRV.adapter as SearchAdapter
    private var items: MutableList<KaKaoImage> = mutableListOf()
    private lateinit var sharedPreferences: SharedPreferences
    private var job: Job? = null

//    private lateinit var selet : MainActivity 클론 (넘버 1임) 껍데기라서 기능없음
//    private val parentActivity  get() = (activity as?  MainActivity)(넘버2고 이런식으로 타입캐스팅 해와서 객체호ㅓ해서 메서드에 접근하면 찐 메인)


    override fun onCreate(savedInstanceState: Bundle?) {
        //프래그먼트가 생성될 때 호출 savedInstanceState 통해 프래그먼트가 이전에 저장한 정보를 복원 가능
        super.onCreate(savedInstanceState)
        //초기화  검색어를 저장하고 불러올 때 사용
        sharedPreferences = requireContext().getSharedPreferences("pref", 0)
    }

    override fun onCreateView(
        //프래그먼트의 레이아웃을 인플레이트하고 해당  view를 반환 이 메서드는 프래그먼트 ui를 초기화하고 화면에 그리는 역할
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

//        val mainView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_main,null) 새로운 레이아웃을 가져와서 검색기능이 안먹음...기능없는 껍데기같은느낌
//        ( activity as? MainActivity)?.findViewById<Button>(R.id.searchEdit) 이렇게 타입캐스팅 해서 기능가져옴
//        activity?.findViewById<Button>(R.id.searchBtn)  이런식으로 가져올 수 있음

//        parentActivity?.selecTab(1) 넘버2

//        val searchEdit = mainView.findViewById<EditText>(R.id.searchEdit)
//        val searchButton =mainView.findViewById<Button>(R.id.searchBtn)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //이 메서드는 View가 생성된 후 호출 댐 이 시점에서는 view와 관련된 작업을 수행 가능 버튼,이벤트리스너 등등 ui에 관한 이벤트 추가작업들
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchButton()
        loadData()
        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            // LiveData를 사용하여 북마크 아이템을 관찰하고 업데이트
            adapter.updateData(bookmarks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        //뷰가 파괴될때  필요한 정리 작업을 수행 뷰와 관련된 자원을 정리하거나 해제함 프래그먼트가 파괴되더라도 코루틴 계속 실행되서 메모리 누수 방지하기위해서 잡을 통해 코루틴 취소
    }

    private fun setupRecyclerView() {
        binding.apply {
            SearchRV.adapter = SearchAdapter(items, viewModel)
            SearchRV.layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setupSearchButton() {
        binding.searchBtn.setOnClickListener {
            val query = binding.searchEdit.text.toString()
            if (query.isNotEmpty()) {
                searchImages(query)
                savaData(query)
            }
        }

    }

    fun searchImages(query: String) {
        //글로벌에서 라이프스코프로 바꿈 이유는 글로벌은 앱의 수명주기에 종속됨, 라이프는 프래그먼트가 활성화 일때만 유지되며 프래그먼트가 파괴되면 자동으로 취소
        //그래서 액티비티나 프래그먼트 생명주기와 함께 동작하므로 함께 취소되니 메모리 누수 위험을 크게 줄임
        lifecycleScope.launch(Dispatchers.IO) {

            val response = RetrofitInstance.getApiService()//레트로핏을 이용해 웹서비스에 검색요청
                .searchImage(Constants.AUTH_KEY, query, "accuracy", 1, 80)

            if (response.isSuccessful) {
                Log.d("jun", "성공.")
                val imageSearchResponse = response.body() //성공 한 경우 저장하고 documents를 추출 body는 레트로핏에서 제공하는 메서드로 HTTP응답의 본문을 추출하는 역할
                val documents = imageSearchResponse?.documents
                if (documents != null) {
                    withContext(Dispatchers.Main) {
                        //ui 업데이트를 위해서 메ㄴ인스레드에서 실행 리싸이클러뷰에 documents를 호출해서 검색결과 업뎃
                        adapter.searchData(documents)
                    }
                }
            } else {
            }
        }
    }

    private fun savaData(query: String) {
        val pref = requireContext().getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("name", query)
        edit.apply()
    }

    private fun loadData() {
        val pref = requireContext().getSharedPreferences("pref", 0)
        binding.searchEdit.setText(pref.getString("name", ""))
    }
}




