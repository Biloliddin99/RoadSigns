package com.example.yolbelgilari.db

import com.example.yolbelgilari.models.MySign

interface MyDbInterface {
    fun addSign(mySign: MySign)
    fun getAllSign():ArrayList<MySign>
    fun deleteSign(mySign: MySign)
    fun editSign(mySign: MySign):Int
    fun getLikedSigns():ArrayList<MySign>

}