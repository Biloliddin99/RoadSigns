package com.example.yolbelgilari.db

import com.example.yolbelgilari.models.MySign

object Constants {

    val TYPE_ARRAY = arrayOf(
        "Ogohlantiruvchi",
        "Imtiyozli",
        "Ta'qiqlovchi",
        "Buyuruvchi"
    )
    var SIGN_ADDED = false
    var SIGN_EDITED = false
    var TEMP_SIGN = MySign("", "", 3, 0, "")
    var VIEW_PAGER_ITEM_POSITION = 0
}