package com.example.imageseach.ui

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imageseach.R
import com.example.imageseach.adapter.SearchAdapter
import com.example.imageseach.data.KaKaoImage
import com.example.imageseach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var selectedTextView: TextView
    private lateinit var nonSelectedTextView1: TextView
    private lateinit var selectedFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                selectedFragment = SearchFragment()
            }

            2 -> {
                selectedTextView = binding.tab2items
                nonSelectedTextView1 = binding.tab1items
                selectedFragment = BookmarkFragment()
            }
        }
        // 프래그먼트를 변경
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment, selectedFragment, null)
            .commit()

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