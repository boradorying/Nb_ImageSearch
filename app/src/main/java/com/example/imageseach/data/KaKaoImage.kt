package com.example.imageseach.data

import android.icu.number.IntegerWidth
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class KaKaoImage (  //JSON으로 직렬화(객체를 JSON 문자열로 변환)

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("datetime")
    val datetime: String,//타임toconvert 해서 원하는 타임포맷으로 뿌려지는거
    var isHeart :Boolean =false
)

