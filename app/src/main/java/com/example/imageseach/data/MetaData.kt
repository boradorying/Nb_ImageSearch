package com.example.imageseach.data

import com.google.gson.annotations.SerializedName

data class MetaData (
    @SerializedName("total_count")
    val totalCount : Int?,
    @SerializedName("pageable_count")
    val pageale : Int?,
    @SerializedName("is_end")
    val isEnd:Boolean?
        )
