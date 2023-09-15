package com.example.imageseach.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageseach.data.KaKaoImage

class SharedViewModel : ViewModel() {
    private val _bookmarkedItems: MutableLiveData<MutableList<KaKaoImage>> = MutableLiveData(mutableListOf())


    val bookmarkedItems: LiveData<MutableList<KaKaoImage>> = _bookmarkedItems


    fun addToBookmark(item: KaKaoImage) {
        val items = _bookmarkedItems.value ?: mutableListOf()
        if (!items.contains(item)) {
            items.add(item)
            _bookmarkedItems.postValue(items)
        }
    }

    fun removeFromBookmark(item: KaKaoImage) {
        val items = _bookmarkedItems.value ?: mutableListOf()
        if (items.contains(item)) {
            items.remove(item)
            _bookmarkedItems.postValue(items)
        }
    }


}
