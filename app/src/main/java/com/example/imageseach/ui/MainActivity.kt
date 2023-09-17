package com.example.imageseach.ui

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imageseach.R
import com.example.imageseach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var selectedTextView: TextView
    private lateinit var nonSelectedTextView1: TextView
    private lateinit var selectedFragment: Fragment //매번 새롭게 만들어서 (클론) //너무중요해

    private val searchFrag by lazy { SearchFragment()} //얘 하나만 생성한다 중요해 찐 서치프래그(서치프래그먼트 매번 새롭게 생성하는게 아니라 이것만 쓴다! 라는말)
    private val bookFrag by lazy{BookmarkFragment()}
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        selecTab(1)


        binding.apply {
            tab1items.setOnClickListener {
                selecTab(1)
            }
            tab2items.setOnClickListener {
                selecTab(2)
            }
        }
    }

    private fun selecTab(tabNumber: Int) {
        // 선택된 번호에 따라 변수를 초기화
        when (tabNumber) {
            1 -> {
                selectedTextView = binding.tab1items
                nonSelectedTextView1 = binding.tab2items
                selectedFragment = searchFrag
            }

            2 -> {
                selectedTextView = binding.tab2items
                nonSelectedTextView1 = binding.tab1items
                selectedFragment = bookFrag
            }
        }
        // 프래그먼트를 변경
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, selectedFragment, null)
        transaction.commit()

        // 선택된 탭의 스타일을 변경
        selectedTextView.apply {
            setBackgroundResource(R.drawable.rounde_back_white_1)
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
        }

        // 비선택 탭의 스타일을 변경
        nonSelectedTextView1.apply {
            setBackgroundColor(ContextCompat.getColor(applicationContext, android.R.color.transparent))
            setTypeface(null, Typeface.NORMAL)
            setTextColor(Color.parseColor("#A4503C3C"))
        }
    }
}