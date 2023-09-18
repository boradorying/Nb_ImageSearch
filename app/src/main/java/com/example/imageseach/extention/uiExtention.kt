package com.example.imageseach.extention

import android.widget.ImageView
import com.example.imageseach.R

fun ImageView.loadHeartImage(isHeart: Boolean) {
    setImageResource(if(isHeart) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
}//viewExtention