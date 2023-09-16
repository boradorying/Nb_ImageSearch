package com.example.imageseach.data

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse (
    @SerializedName("meta")
    val metaData: MetaData?,
    @SerializedName("documents")
    val documents : MutableList<KaKaoImage>?
        )
